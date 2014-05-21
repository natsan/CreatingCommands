/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commandtofile;

import javax.swing.SwingUtilities;

/**
 *
 * @author stend
 */
public class CommandToFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                new MainPanel();

            }
        });
    }
}
