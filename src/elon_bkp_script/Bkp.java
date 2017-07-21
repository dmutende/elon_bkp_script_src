/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elon_bkp_script;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JOptionPane;
import javax.xml.xpath.XPathExpressionException;
import lib.Log;
import lib.Utils;
import lib.XMLUtils;
import org.w3c.dom.Document;
import lib.Zip;
import lib.ZipUtils;
import org.w3c.dom.DOMException;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
/**
 *
 * @author princ
 */
public class Bkp {
    
    void checkBkpFileStatus() throws XPathExpressionException, IOException{
        if(Utils.createDir(Constants.getBkpDirectory()+Utils.getCurrentTimeStamp())){
            getDirectoriestoBkp();
        }
        Log.log(Log.SEPARATOR+"Backup Completed At: "+Utils.getDate()+Log.EOF);
//        JOptionPane.showMessageDialog(null, "Backup Completed Sucessfully At: "+Utils.getDate(), "Elonbkp: Backup Successful", JOptionPane.INFORMATION_MESSAGE);
    }
    
    void getDirectoriestoBkp() throws IOException{
        Document XMLDoc = XMLUtils.parse();
        
        try{
            NodeList nList = XMLDoc.getElementsByTagName("DIRTOBKP");
            Node n=null;
            Element eElement=null;
            for (int i = 0; i < nList.getLength(); i++) {   
                n = nList.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    eElement = (Element) n.getChildNodes();
                    //System.console().writer().println("\nDIR '"+XMLDoc.getElementsByTagName("DIRTOBKP").item(i).getTextContent()+"' is currently being backed up");
                    System.out.println("\nDIR '"+XMLDoc.getElementsByTagName("DIRTOBKP").item(i).getTextContent()+"' is currently being backed up");
                    System.out.println("ZIP Filename - " + eElement.getAttribute("BKPZIPNAME"));
                    Log.log("\nDIR '"+XMLDoc.getElementsByTagName("DIRTOBKP").item(i).getTextContent()+"' is currently being backed up"+Log.NEW_LINE);
                    Log.log("ZIP Filename - " + eElement.getAttribute("BKPZIPNAME")+Log.NEW_LINE+Log.NEW_LINE);
                    Path folder = Paths.get(XMLDoc.getElementsByTagName("DIRTOBKP").item(i).getTextContent());
                    Path zipFilePath = Paths.get(Constants.getBkpDirectory()+Utils.readSubDir(Constants.getBkpDirectory())+"\\"+eElement.getAttribute("BKPZIPNAME")+".zip");
                    backUpFiles(folder, zipFilePath);
                }
                n.getNextSibling();
            }
        }catch (IOException | XPathExpressionException | DOMException e){
            Log.log(Log.NEW_LINE+"Error: "+e.toString()+Log.NEW_LINE);
        }
    }
    
    void backUpFiles(final Path folder, final Path zipFilePath) throws IOException, XPathExpressionException {
        if(Constants.getZipMode().equals(Constants.ZIP)){
            Zip.backUpFiles(folder, zipFilePath);
        }else if(Constants.getZipMode().equals(Constants.ZIPIT)){
            ZipUtils zipit = new ZipUtils();
            zipit.zipIt(folder.toFile(), zipFilePath.toFile());
        }
        
    }
    
}
