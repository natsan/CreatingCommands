/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commandtofile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author stend
 */
public class CommandFile {
    static String _name = "";
    
//    CommandFile(String name) {
//        _name = name;
//        
//    }
    
    public static boolean setFile(String name) throws IOException {
        _name = name;
        File file = new File(_name);
       
            if(file.createNewFile())
                return true;
            else
                return false;
    }
    
    public static void addToFile(String text) {
        if(_name.length()==0)
            Error.showError(7, null);
        
        FileOutputStream fout;
        try {
            fout = new FileOutputStream(_name, true);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CommandFile.class.getName()).log(Level.SEVERE, null, ex);
        //    Error.showError(7, null);
            return;
        }
        byte [] data = convertStringToByteArray(text);
        try {
            fout.write(data);
        } catch (IOException ex) {
            Logger.getLogger(CommandFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(CommandFile.class.getName()).log(Level.INFO, "{0} is written.", text);
        try {
            fout.close();
        } catch (IOException ex) {
            Logger.getLogger(CommandFile.class.getName()).log(Level.SEVERE, null, ex);
        }
         //        try {
    //            FileWriter sw = new FileWriter(_name, true);
    //            sw.write(text+"\n");
    //            sw.close();
    //        } catch (Exception e) {
    //        }
    //        }
    }

    private static byte[] convertStringToByteArray(String text) {
        byte[] arrayOfValues = new byte [text.length() / 2];
        int counter = 0;
        for (int i = 0; i < text.length(); i += 2) {
            String s = text.substring(i, i + 2);
            arrayOfValues[counter] =(byte)Integer.parseInt(s, 16);
            counter++;
        }
        return arrayOfValues;
    }
}
