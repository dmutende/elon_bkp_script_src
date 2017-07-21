/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elon_bkp_script;

import java.io.IOException;
import javax.xml.xpath.XPathExpressionException;
import lib.Log;

/**
 *
 * @author princ
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws javax.xml.xpath.XPathExpressionException
     */
    public static void main(String[] args) throws XPathExpressionException, IOException {
        Log.initializeLog();
        Bkp bkp = new Bkp();
        bkp.checkBkpFileStatus();
    }
    
}
