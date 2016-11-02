/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javamodules;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
/**
 *
 * @author RAVI
 */
public class FilterValues {
    private static String dir="Filter Setting";
    private static String filePort=dir+"\\Port Filter.txt";
    private static String fileIP=dir+"\\IP Filter.txt";
    private static String fileWord=dir+"\\Word Filter.txt";
    public static ArrayList<String> words = new ArrayList<String>();
    public static ArrayList<InetAddress> ip = new ArrayList<InetAddress>();
    public static ArrayList<Integer> ports = new ArrayList<Integer>();
   public static void readPortData()
    {
        try{
            FileInputStream fstream = new FileInputStream(filePort);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                ports.add(Integer.parseInt(strLine));
                System.out.println (strLine);
            }
            //System.out.println (ports);
            in.close();
        }
        catch(FileNotFoundException f)
        {
            //ports.add(0);
            //SET DEFAULT VALUE AS YOU WISH
            ports = null;
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
       // System.out.println (ports);
    }
   public static void readIPData()
    {
        try{
            InetAddress address;
            FileInputStream fstream = new FileInputStream(fileIP);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                address= InetAddress.getByName(strLine);
                
                ip.add(address);
                //System.out.println (strLine);
            }
            in.close();
        }
        catch(FileNotFoundException f)
        {
            //SET DEFAULT VALUE AS YOU WISH
            
            ip = null;
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
      //  System.out.println (ip);
    }


   public static void readWordData()
    {
        try{
            FileInputStream fstream = new FileInputStream(fileWord);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                words.add(strLine);
                //System.out.println (strLine);
            }
            in.close();
        }
        catch(FileNotFoundException f)
        {
            //SET DEFAULT VALUE AS YOU WISH
            words = null;
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
      //  System.out.println (words);
    }

   public static int getFilterStatus()
    {
        return FilterSetting.status;
    }
    public static void main(String[] args)
    {
        FilterValues.readIPData();
        
    }
}
