/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import elon_bkp_script.Constants;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.xpath.XPathExpressionException;

/**
 *
 * @author princ
 */
public class Utils {
    
    public static String getCurrentTimeStamp(){
        String timeStamp;
        Long timestamp = System.currentTimeMillis() / 1000;
        timeStamp = Long.toString(timestamp);
        return timeStamp;
    }
    
    public static String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    public static boolean createDir(String pathToDir) throws IOException{
        File file = new File(pathToDir);
        if (!file.exists()) {
            try {
                Long bkpInterval = Long.parseLong(Constants.getBkpFrequqncy())*86400;
                //System.out.println(Long.parseLong(getCurrentTimeStamp()));
                //604800
                String currentBkpDir = readSubDir(Constants.getBkpDirectory());
                if ((Long.parseLong(getCurrentTimeStamp()) - Long.parseLong(currentBkpDir)) > bkpInterval){
                    deleteDir(Constants.getBkpDirectory()+currentBkpDir);
                   if (file.mkdir()) {
                        System.out.println("DIR '"+pathToDir+"' has been created.");
                        Log.log(Log.NEW_LINE+"DIR '"+pathToDir+"' has been created."+Log.NEW_LINE);
                        return true;
                    } else {
                        System.out.println("Failed to create DIR'"+pathToDir);
                        Log.log(Log.NEW_LINE+"Failed to create DIR'"+pathToDir+"'"+Log.NEW_LINE);
                    } 
                }else{
                    System.out.println("Current Backup is up to date.\nLifetime remaining = "+(bkpInterval-(Long.parseLong(getCurrentTimeStamp()) - Long.parseLong(currentBkpDir)))+" seconds");
                    Log.log(Log.NEW_LINE+"Current Backup is up to date.\nLifetime remaining = "+(bkpInterval-(Long.parseLong(getCurrentTimeStamp()) - Long.parseLong(currentBkpDir)))+" seconds"+Log.NEW_LINE);
                }
                    
            } catch (XPathExpressionException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                Log.log("Error: "+ex.toString());
            }
        }
        
        return false;
    }
    
    public static String readSubDir(String parentDir) throws IOException{
        File file = new File(parentDir);
        String[] dirs = file.list();
        String subDir = "0";
        try{
            if(dirs.length == 1){
                subDir = dirs[0];
            }else if(dirs.length > 1){
                System.out.println("System Error! Multiple backup folders detected!");
                Log.log(Log.NEW_LINE+"System Error! Multiple backup folders detected!"+Log.NEW_LINE);
                System.exit(0);
            }else{
                System.out.println("Preparing Parent Backup Directory... \nKindly do not alter any folders here.\nAccess is STRICTLY Read and Copy only!");
                Log.log(Log.SEPARATOR+"Preparing Parent Backup Directory... \nKindly do not alter any folders here.\nAccess is STRICTLY Read and Copy only!"+Log.SEPARATOR);
            }
        }catch (Exception e){
            System.out.println("System Error! Multiple or no backup folders detected!");
            Log.log("Error: "+e.toString());
            System.exit(0);
        }
        return subDir;
    }
    
    static void deleteDir(String dir) throws IOException {
        try{
            
            if (!dir.equals(Constants.getBkpDirectory()+"0")){
                File Dir = new File(dir);
                if (Dir.isDirectory()) {
                    for (File sub : Dir.listFiles()) {
                        deletesubDirs(sub);
                    }
                }
                Dir.delete();
                System.out.println("DIR '"+dir+"' has been deleted");
                Log.log("DIR '"+dir+"' has been deleted");
            }
            
        }catch(XPathExpressionException e){
            Log.log("Error: "+e.toString());
        }finally{
            
        }
    }
    
    static void deletesubDirs(File subDir){
        if (subDir.isDirectory()) {
            for (File sub : subDir.listFiles()) {
                deletesubDirs(sub);
            }   
        }
        subDir.delete();
    }
    
    public static void writeToFile(String fileName, String dataToWrite) throws IOException{
        
        byte data[] = dataToWrite.getBytes();
        Path p = Paths.get(Constants.workingDirectory()+fileName);

        try (OutputStream out = new BufferedOutputStream(
            Files.newOutputStream(p, CREATE, APPEND))) {out.write(data, 0, data.length);
        } catch (IOException x) {
            System.err.println(x);
        }
    }
}
