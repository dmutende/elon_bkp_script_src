/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import elon_bkp_script.Constants;
import org.w3c.dom.Document;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 *
 * @author princ
 */
public class XMLUtils {
    
    public static Document parse(){
        Document doc = null;
        try{
            File inputFile = new File(Constants.workingDirectory()+Constants.XMLFileName());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
        }catch(Exception e){
            
        }
         return doc;
    }
    
    public static String readXMLTag(String tag_path) throws XPathExpressionException{
        
        String content = "";
        Document XML = parse();
        
        XPathExpression xp = XPathFactory.newInstance().newXPath().compile(tag_path);
        content = xp.evaluate(XML);
        
        return content;
    }
    
}
