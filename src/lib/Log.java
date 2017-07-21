/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import elon_bkp_script.Constants;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author princ
 */
public class Log {
    
    public static final String EOF = "\n-----------------------------------------\n\n\n";
    public static final String NEW_LINE = "\n";
    public static final String SEPARATOR = NEW_LINE+"--------------------"+NEW_LINE;
    
    public static void initializeLog() throws IOException{
        
        Document XMLDoc = XMLUtils.parse();
        
        try{
            log("Backup Started At: "+Utils.getDate()+NEW_LINE
                    +"Zipping mode - "+Constants.getZipMode()
                    +SEPARATOR
                    +"Files Set for Back Up"
                    +SEPARATOR);
            
            NodeList nList = XMLDoc.getElementsByTagName("DIRTOBKP");
            Node n=null;
            Element eElement=null;
            for (int i = 0; i < nList.getLength(); i++) {   
                n = nList.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    eElement = (Element) n.getChildNodes();
                    log(NEW_LINE+"DIR '"+XMLDoc.getElementsByTagName("DIRTOBKP").item(i).getTextContent()+"'"+NEW_LINE
                        +"ZIP Filename - " + eElement.getAttribute("BKPZIPNAME")+NEW_LINE);
                }
                n.getNextSibling();
            }
            log("\n-----------------------------------------\n");
        }catch (Exception e){
            log(NEW_LINE+"Error: "+e.toString()+NEW_LINE);
        }
    }
    
    public static void log(String logData) throws IOException{
        Utils.writeToFile(Constants.logFileName(), logData);
    }
}
