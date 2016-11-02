/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javamodules;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RAVI
 */
public class LogData {

    public static FileWriter fw;
    public static PrintWriter out;
    //static String source_IP,desti_IP,source_MAC,desti_MAC;
    //static int source_Port,desti_Port;
    //static Date date;
    
    public static void createLog()
    {
        String logfolder = "Log Files\\";
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter= new SimpleDateFormat("dd_MM_yyyy");
        String dateNow = formatter.format(currentDate.getTime());
        System.out.println("Now the date is :=>  " + dateNow);
        //System.out.println(dateNow + "_log_file");
        String filename = logfolder+dateNow + "_log_file";
        String HEADER = "Source IP ,Destination IP ,Source Port ,Destination Port ,Source MAC ,Destination MAC , Date\n";
        File fname = new File(filename);
        boolean check= false;
        
            if(!(fname.exists()))
            {
            try {
                fw = new FileWriter(filename+".csv",true);
            } catch (IOException ex) {
                Logger.getLogger(LogData.class.getName()).log(Level.SEVERE, null, ex);
            }
                check = true;
            }
            else
                check = false;
        
        out = new PrintWriter(fw);
        
        String COLUMN1 = HEADER.substring(0,HEADER.indexOf(",")+1);
        //System.out.println(COLUMN1);
        String line1column1 = "Source IP";
        //if(!(line1column1.equals(COLUMN1)))
        //{
          //  out.write(HEADER); 
        //}
        System.out.println(check);
        
            
           
    }
    
    public static void writeLog(String sourceIP,String destIP,int sourcePort, int destPort, String sourceMAC, String destMAC, Date d)
    {
        
        String sp = String.valueOf(sourcePort);
        String dp = String.valueOf(destPort);
        String str = sourceIP+","+destIP+","+sp+","+dp+","+sourceMAC+","+destMAC+","+d.toString()+"\n";
        out.print(str);
        
    }
    
    public static void closeLog()
    {
        try {
            //Flush the output to the file
            out.flush();
           
            //Close the Print Writer
            out.close();
           
            //Close the File Writer
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(LogData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /*public static void main(String args[])
    {
      
    }*/
}
