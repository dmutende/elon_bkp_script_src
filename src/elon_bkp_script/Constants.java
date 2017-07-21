/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elon_bkp_script;

import java.io.File;
import java.io.IOException;
import javax.xml.xpath.XPathExpressionException;
import lib.XMLUtils;

/**
 *
 * @author princ
 */
public class Constants {
    public static final String ZIP = "ZIP";
    public static final String ZIPIT = "ZIPIT";
    public static String getBkpDirectory() throws XPathExpressionException{return XMLUtils.readXMLTag("/ELONBKP/BKPDIR");}
    public static String getBkpFrequqncy() throws XPathExpressionException{return XMLUtils.readXMLTag("/ELONBKP/BKPFREQ");}
    public static String getZipMode() throws XPathExpressionException{return XMLUtils.readXMLTag("/ELONBKP/BKPZIPTOOL");}
    public static String getDirsToBkpTagPath(){return "/ELONBKP/DIRSTOBKP/DIRTOBKP";}
    public static String logFileName(){return "bkp_logs.log";}
    public static String XMLFileName(){return "bkp_conf.xml";}
    public static String workingDirectory() throws IOException{
        File currentDirectory = new File(new File(".").getAbsolutePath());
        //System.out.println(currentDirectory.getCanonicalPath());
        //System.out.println(currentDirectory.getAbsolutePath());
        return currentDirectory.getCanonicalPath()+"\\";
    }
}
