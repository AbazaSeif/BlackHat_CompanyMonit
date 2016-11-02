/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comman;

import static Comman.KeysVer.Args;
import Comman.KeysVer.Keys;
import compy_client.ComSpy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author abaza
 */
public final class ConnectionServer {
    
    private Properties properties;
    private String KeyPath = "conf/inf", Key, UUID, Lang, ServerLink, ConfFile, EncConfFile, Ports1, Ports2, Vuser, VPass;
    static JSONParser JSON = new JSONParser();
    private static final SERVER_KEYS Server_Var = new SERVER_KEYS();
    private static final LOCAL_KEYS Local_Var = new LOCAL_KEYS();
    
    public ConnectionServer() {
        
    }

    public void Init(String Conffile) {
        ConfFile = Conffile;
        File F1 = new File(ConfFile);
        EncConfFile = Conffile + "e";
        File F2 = new File(EncConfFile);
        Key = getKey();
        try {
            FileInputStream fis2 = null;
            FileOutputStream fos2 = null;
            if (F2.exists()) {
                if (F1.exists()) {
                    fos2 = new FileOutputStream(ConfFile);
                }
                fis2 = new FileInputStream(EncConfFile);
                CryptoUtils.decrypt(Key, fis2, fos2);
                File F = new File(EncConfFile);
                F.delete();
            }

            this.setConf(ConfFile);
        } catch (Throwable e) {
        }
    }
//    public void Init(String PathConfigration) {
//        FileOutputStream fileOutStrem = null;
//        try {
//            Key = getKey();
//            File FileEncription = new File(PathConfigration);
//            File FileDecryption = new File(FileEncription.getAbsoluteFile() + "e");
//            fileOutStrem = new FileOutputStream(FileDecryption);
//            FileInputStream fileInputStrem = new FileInputStream(PathConfigration);
//            if (FileDecryption.exists()) {
//                try {
//                    CryptoUtils.decrypt(Key, fileInputStrem, fileOutStrem);
//                    File F = new File(PathConfigration);
//                    F.delete();
//                    this.setConf(fileOutStrem.toString());
//                } catch (Throwable ex) {
//                }
//            } else {
//                try {
//                    System.err.println("Error Not Find " + PathConfigration + "e");
//                    CryptoUtils.encrypt(Key, fileInputStrem, fileOutStrem);
//                    Init(PathConfigration);
//                } catch (Throwable ex) {
//                }
//            }
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(ConnectionServer.class.getName()).log(Level.SEVERE, null, ex);
//            System.exit(1);
//        } finally {
//            try {
//                fileOutStrem.close();
//            } catch (IOException ex) {
//                Logger.getLogger(ConnectionServer.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
    
    public void checkHosts(String subnet) {
        int timeout = 500;
        for (int i = 99; i < 255; i++) {
            try {
                String host = subnet + "." + i;
                
                if (InetAddress.getByName(host).isReachable(timeout)) {
                    System.out.print(host + "  [ONLINE .. ");
                    try (Socket Test = new Socket(host, 3333)) {
                        if (Test.isConnected()) {
                            System.out.println("ComSpy]");
                        }
                    }
                }
            } catch (Exception E) {
                System.out.println("BUT NOT ComSpy]");
            }
            
        }
    }
    
    public boolean Connect(String Data, boolean ConnectactMethodOverLAN) {
        if (ConnectactMethodOverLAN) {
            checkHosts("192.168.0");
            System.out.println("End Scan");
        } else {
            List<NameValuePair> conteners = new ArrayList<>();
            
            conteners.add(new BasicNameValuePair(Server_Var.TYPE_COMMENT, Data));
            
            String resServer = Post(conteners, "GET");
            
            if (resServer == null || resServer.isEmpty()) {
                return false;
            } else {
                
                HashMap<String, String> ServerData = parseJSON(resServer);
                
                if (ServerData != null) {
                    
                    if (ServerData.containsKey(Server_Var.RETERN)) {
                        if (ServerData.get(Server_Var.RETERN).equals(Server_Var.SUCCESS)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public HashMap<String, String> Contaner(String[][] Data) {
        List<NameValuePair> conteners = new ArrayList<>();
        
        if (Data.length < 0) {
            return null;
        }
        
        for (int i = 0; i <= Data.length - 1; i++) {
            conteners.add(new BasicNameValuePair(Data[i][0], Data[i][1]));
        }
        
        String resServer = Post(conteners, "GET");
        
        if (resServer == null) {
            return null;
        } else {
            HashMap<String, String> ServerData = parseJSON(resServer);
            return ServerData;
        }
    }
    
    public HashMap<String, String> ContanerPOST(String[][] Data) {
        List<NameValuePair> conteners = new ArrayList<>();
        
        if (Data.length < 0) {
            return null;
        }
        
        for (int i = 0; i <= Data.length - 1; i++) {
            conteners.add(new BasicNameValuePair(Data[i][0], Data[i][1]));
        }
        
        String resServer = Post(conteners, "POST");
        
        if (resServer == null) {
            return null;
        } else {
            HashMap<String, String> ServerData = parseJSON(resServer);
            return ServerData;
        }
    }
    
    public boolean DoneOrNot(HashMap<String, String> ServerData) {
        if (ServerData != null) {
            
            if (ServerData.containsKey(Server_Var.RETERN)) {
                return ServerData.get(Server_Var.RETERN).equals(Server_Var.SUCCESS);
            }
        }
        return false;
    }
    
    public static void upload(String Filename) throws Exception {
        
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost httppost = new HttpPost(Args.get(Keys.SL) + "?" + "type=upload");
        File file = new File(Filename);
        MultipartEntity mpEntity = new MultipartEntity();
        ContentBody contentFile = new FileBody(file);
        mpEntity.addPart("userfile", contentFile);
        httppost.setEntity(mpEntity);
        ComSpy.MessageLog("executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        
        if (!(response.getStatusLine().toString()).equals("HTTP/1.1 200 OK")) {
            // Successfully Uploaded
        } else {
            // Did not upload. Add your logic here. Maybe you want to retry.
        }
        ComSpy.MessageLog(String.valueOf(response.getStatusLine()));
        if (resEntity != null) {
            ComSpy.MessageLog(EntityUtils.toString(resEntity));
        }
        if (resEntity != null) {
            resEntity.consumeContent();
        }
        httpclient.getConnectionManager().shutdown();
    }
    
    public static HashMap<String, String> parseJSON(String response) {
        try {
            HashMap<String, String> out = null;
            JSONObject json = null;
            json = new JSONObject(response);
            out = new HashMap<>();
            Iterator jk = json.keys();
            while (jk.hasNext()) {
                String key = (String) jk.next();
                out.put(key, json.getString(key));
            }
            
            return out;
        } catch (JSONException e) {
            return null;
        }
    }
    
    public void SendSegnalforClient() {
        try {
            String command = "connect";
            String up = "type=" + command;
            URL url = new URL(Args.get(Keys.SL) + "?" + up);
            URLConnection urlc = url.openConnection();
            urlc.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            
            br.close();
            String Buffer = sb.toString();
            
        } catch (Exception e) {
        }
    }
    
    public static JSONObject getJSONfromURL(String url) {
        InputStream is = null;
        String result = "";
        JSONObject jArray = null;

        // Download JSON data from URL
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            
        } catch (IOException | IllegalStateException e) {
        }

        // Convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
        }
        
        try {
            
            jArray = new JSONObject(result);
        } catch (JSONException e) {
        }
        
        return jArray;
    }
    
    public static String Post(List<NameValuePair> nameValuePairs, String Type) {
        JSONObject Ret = new JSONObject();
        Ret = JSON.makeHttpRequest(Args.get(Keys.SL), Type, nameValuePairs);
        if (Ret != null) {
            return Ret.toString();
        } else {
            return null;
        }
    }
    
    public void CloseClient() {
        FileInputStream fis = null;
        File f = new File(ConfFile);
        if (!f.exists()) {
            return;
        }
        try {
            fis = new FileInputStream(ConfFile);
            if (fis != null) {
                FileOutputStream fos = new FileOutputStream(EncConfFile);
                CryptoUtils.encrypt(Key, fis, fos);
                File F = new File(ConfFile);
                F.delete();
            }
        } catch (FileNotFoundException ex) {
        } catch (Throwable ex) {
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                System.exit(0);
            }
        }
    }
    
    private void setConf(String filename) throws FileNotFoundException, IOException, InvalidPropertiesFormatException {
        this.properties = new Properties();
        FileInputStream FIS = new FileInputStream(filename);
        this.properties.loadFromXML(FIS);
        
        this.ServerLink = this.properties.getProperty("SL");
        this.UUID = this.properties.getProperty("UUID");
        this.Lang = this.properties.getProperty("lang");
        this.Ports1 = this.properties.getProperty("port1", "3333");
        this.Ports2 = this.properties.getProperty("port2", "2222");
        this.Vuser = this.properties.getProperty("username");
        this.VPass = this.properties.getProperty("password");
        
        Args.put(Keys.Lang, this.Lang);
        Args.put(Keys.CUUID, this.UUID);
        Args.put(Keys.MY_PORT, this.Ports1);
        Args.put(Keys.AUDIO_PORT, this.Ports2);
        Args.put(Keys.VUSERNAME, this.Vuser);
        Args.put(Keys.VPASSWORD, this.VPass);
        Args.put(Keys.SL, this.ServerLink);
    }
    
    public String getKey() {
        BufferedReader br = null;
        
        File MKey = new File(KeyPath);
        if (MKey.exists()) {
            try {
                br = new BufferedReader(new FileReader(KeyPath));
                try {
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();
                    
                    while (line != null) {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                        line = br.readLine();
                    }
                    return sb.toString();
                } catch (IOException ex) {
                } finally {
                    try {
                        br.close();
                    } catch (IOException ex) {
                    }
                }
            } catch (FileNotFoundException ex) {
            } finally {
                try {
                    br.close();
                } catch (IOException ex) {
                }
            }
        } else {
            System.exit(0);
        }
        return null;
    }
    
}
