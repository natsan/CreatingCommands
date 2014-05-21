/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commandtofile;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

/**
 *
 * @author stend
 */
public class MainPanel extends JFrame {
    
    int height = 80; //высота
    int width = 550; //ширина приложения
    
    JTextField jtfNewFile; //название создаваемого файла
    JButton jbNewFile; //создать новый файл
    JButton jbAddMK; //добавить к текущему файлу команду МК
    JButton jbAddPAR; //добавить к текущему файлу команду PAR
    JButton jbAddSPI; //добавить к текущему файлу команду SPI
    
    //поля ввода для MK-команд
    JTextField jtfMKAddr; //адрес
    JTextField jtfMKRow; //ряд
    JTextField jtfMKColumn; //столбец
    JTextField jtfMKDuration;//длительность
    JCheckBox jcbMK15V30V; //15 В/30 В
    JButton jbMKAdd;
    
    //поля ввода для PAR-команд
    JTextField jtfPARAddr; //адрес
    JTextField jtfPARMask; //маска вых.
    JTextField jtfPARData; //данные
    JButton jbPARAdd;
    
    //поля ввода для SPI-команд
    JTextField jtfSPIAddr; //адрес
    JTextField jtfSPICLK; //CLK
    JTextField jtfSPISTR; //STR
    JTextField jtfSPIDAT;//DAT
    JTextField jtfSPIMask; //Инв. маска
    JCheckBox jcbSPIStrob; //Строб зав.
    JTextField jtfSPIData; // Данные
    JButton jbSPIAdd;
    
    //поля ввода для TMI-команд
    JTextField jtfTMIAddr; //адрес
    JTextField jtfTMIChannel; //номер канала
    JButton jbTMIAdd;
    
    private JFileChooser fc = new JFileChooser(); //для создания файла
    
    MainPanel() {
        super("Запись команд в файл");
        
        JPanel jpMain = new JPanel();
        jpMain.setPreferredSize(new Dimension(width, height*5));
        jpMain.add(jpNewFile());
        jpMain.add(jpMK());
        jpMain.add(jpPAR());
        jpMain.add(jpSPI());
        jpMain.add(jpTMI());
        
        this.add(jpMain);

        this.setSize(width, height*4);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
        this.setVisible(true);
    }
    
    private JPanel jpNewFile() {
        JPanel jpNewFile = new JPanel();
        jpNewFile.setPreferredSize(new Dimension(width, height/2));
        
        JLabel jlbFileName = new JLabel("Введите имя файла.расширение:");
        
        jtfNewFile = new JTextField(15);
        jtfNewFile.setPreferredSize(new Dimension(100, 25));
        jtfNewFile.setText("Commands.dat");
        jbNewFile = new JButton("Создать новый файл");
        jbNewFile.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                String name = jtfNewFile.getText();
                File file;
                if(testName(name))
                    file = new File(name);
                else return;
                fc.setDialogTitle("Создание нового файла");
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.setSelectedFile(file);
                int res = fc.showSaveDialog(MainPanel.this);
                if(res==JFileChooser.APPROVE_OPTION) {
                    try {
                        if(!CommandFile.setFile(fc.getSelectedFile().toString()))
                            Error.showError(1, null);
                    } catch (IOException ex) {
                        Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Logger.getLogger(MainPanel.class.getName()).log(Level.INFO, "File {0} is created", fc.getSelectedFile().toString());
                }
                jtfNewFile.setText(file.toString());
            }
            
        });
        jpNewFile.add(jlbFileName);
        jpNewFile.add(jtfNewFile);
        jpNewFile.add(jbNewFile);
        return jpNewFile;
    }
    
    private JPanel jpMK() {
        JPanel jpMK = new JPanel(new GridLayout(2,5));
        jpMK.setPreferredSize(new Dimension(width, height));
        addBorderTitle("MK", jpMK);
        
        JLabel jlMKAddr = new JLabel("Номер платы"); //адрес
        JLabel jlMKRow = new JLabel("Ряд"); //ряд
        JLabel jlMKColumn = new JLabel("Колонка"); //столбец
        JLabel jlMKDuration = new JLabel("Дл-ть (в 0.1 мс)");//длительность
        
        jtfMKAddr = new JTextField(4); //адрес
        jtfMKAddr.setText("01");
        
        jtfMKRow = new JTextField(4); //ряд
        jtfMKRow.setText("01");
        
        jtfMKColumn = new JTextField(4); //столбец
        jtfMKColumn.setText("01");
        
        jtfMKDuration = new JTextField(4);//длительность
        jtfMKDuration.setText("01");
        
        jcbMK15V30V = new JCheckBox("15 В/30 В"); //15 В/30 В
        
        jbMKAdd = new JButton("Добавить");
        jbMKAdd.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String str = "";
                if(!testStr(jtfMKAddr.getText()))
                    return;
                str+=jtfMKAddr.getText();
                
                if(!testStr(jtfMKRow.getText()))
                    return;
                str+=jtfMKRow.getText();
                
                if(!testStr(jtfMKColumn.getText()))
                    return;
                str+=jtfMKColumn.getText();    
                
                if(!testStr(jtfMKDuration.getText()))
                    return;
                str+=jtfMKDuration.getText();
                
                if(jcbMK15V30V.isSelected())
                    str+="01";
                else
                    str+="00";
                CommandFile.addToFile(str);
            }

        });
        
        jpMK.add(jlMKAddr);
        jpMK.add(jlMKRow);
        jpMK.add(jlMKColumn);
        jpMK.add(jlMKDuration);
        jpMK.add(jbMKAdd);
        
        jpMK.add(jtfMKAddr);
        jpMK.add(jtfMKRow);
        jpMK.add(jtfMKColumn);
        jpMK.add(jtfMKDuration);
        jpMK.add(jcbMK15V30V);
        
        return jpMK;
    }
    
    private JPanel jpPAR() {
        JPanel jpPAR = new JPanel();
        jpPAR.setPreferredSize(new Dimension(width, height));
        jpPAR.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        addBorderTitle("PAR", jpPAR);
 
        JLabel jlPARAddr = new JLabel("Номер платы"); //адрес
        c.insets = new Insets(1,1,1,1);  // padding
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        jpPAR.add(jlPARAddr, c);
        
        JLabel jlPARMask = new JLabel("Маска"); //маска вых.
        c.weightx = 0.5;
        c.insets = new Insets(1,1,1,1);  // padding
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        jpPAR.add(jlPARMask, c);
        
        JLabel jlPARData = new JLabel("Данные"); //данные
        c.weightx = 0.5;
        c.insets = new Insets(1,1,1,1);  // padding
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        jpPAR.add(jlPARData, c);

        jtfPARAddr = new JTextField(4); //адрес
        jtfPARAddr.setText("01");
        c.weightx = 0.5;
        c.weighty = 0.0;
        c.ipady = 5;
        c.insets = new Insets(1,1,10,1);  //bottom padding
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        jpPAR.add(jtfPARAddr, c);
        
        jtfPARMask = new JTextField(4); //маска вых.
        jtfPARMask.setText("01");
        c.weightx = 0.5;
        c.weighty = 0.0;
        c.ipady = 5;
        c.insets = new Insets(1,1,10,1);  // padding
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        jpPAR.add(jtfPARMask, c);
        
        jtfPARData = new JTextField(10); //данные
        jtfPARData.setText("01");
        c.weightx = 0.5;
        c.weighty = 0.0;
        c.ipady = 5;
        c.insets = new Insets(1,1,10,1);  //bottom padding
      //  JScrollPane jspPARData = new JScrollPane(jtfPARData);
      //  jspPARData.setPreferredSize(new Dimension(100,40));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        jpPAR.add(/*jspPARData*/jtfPARData, c);
        
        jbPARAdd = new JButton("Добавить");
        c.weightx = 0.5;
        c.insets = new Insets(5,210,1,1);  // top padding
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 0;
        jpPAR.add(jbPARAdd, c);
        
        jbPARAdd.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String str = "";
                if(!testStr(jtfPARAddr.getText()))
                    return;
                str+=jtfPARAddr.getText();
                
                if(!testStr(jtfPARMask.getText()))
                    return;
                str+=jtfPARMask.getText();
                
                if(!testStr(jtfPARData.getText()))
                    return;
                str+=jtfPARData.getText();    
               
                CommandFile.addToFile(str);
            }

        });
        
        return jpPAR;
    }
    
    private JPanel jpSPI() {
        JPanel jpSPI = new JPanel(new GridLayout(2,7));
        jpSPI.setPreferredSize(new Dimension(width, height));
        addBorderTitle("SPI", jpSPI);
        
        JLabel jlSPIAddr = new JLabel("<html>Номер<br>платы", SwingConstants.CENTER); //адрес
    //    jlSPIAddr.setFont(new Font("Serif", Font.PLAIN, 10));
        JLabel jlSPICLK = new JLabel("CLK"); //CLK
        JLabel jlSPISTR = new JLabel("STR"); //STR
        JLabel jlSPIDAT = new JLabel("DAT");//DAT
        JLabel jlSPIMask = new JLabel("<html>Инв.<br>маска", SwingConstants.CENTER); //Инв. маска
        JLabel jlSPIData = new JLabel("Данные"); // Данные
        
        jtfSPIAddr = new JTextField(4); //адрес
        jtfSPIAddr.setText("01");
        
        jtfSPICLK = new JTextField(4); //CLK
        jtfSPICLK.setText("01");
        
        jtfSPISTR = new JTextField(4); //STR
        jtfSPISTR.setText("01");
        
        jtfSPIDAT = new JTextField(4);//DAT
        jtfSPIDAT.setText("01");
        
        jtfSPIMask = new JTextField(4); //Инв. маска
        jtfSPIMask.setText("01");
        
        jtfSPIData = new JTextField(8); // Данные
        jtfSPIData.setText("01");
        
        jcbSPIStrob = new JCheckBox("Строб"); //Строб зав.
        
        jbSPIAdd = new JButton("Добавить");
        jbSPIAdd.setFont(new Font("Serif", Font.BOLD, 10));
        jbSPIAdd.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String str = "";
                if(!testStr(jtfSPIAddr.getText()))
                    return;
                str+=jtfSPIAddr.getText();
                
                if(!testStr(jtfSPICLK.getText()))
                    return;
                str+=jtfSPICLK.getText();
                
                if(!testStr(jtfSPISTR.getText()))
                    return;
                str+=jtfSPISTR.getText();    
                
                if(!testStr(jtfSPIDAT.getText()))
                    return;
                str+=jtfSPIDAT.getText();
                
                if(!testStr(jtfSPIMask.getText()))
                    return;
                str+=jtfSPIMask.getText();
                
                if(jcbSPIStrob.isSelected())
                    str+="01";
                else
                    str+="00";
                
                if(!testStr(jtfSPIData.getText()))
                    return;
                str+=jtfSPIData.getText();
                CommandFile.addToFile(str);
            }

        });
        
        jpSPI.add(jlSPIAddr);
        jpSPI.add(jlSPICLK);
        jpSPI.add(jlSPISTR);
        jpSPI.add(jlSPIDAT);
        jpSPI.add(jlSPIMask);
        jpSPI.add(jlSPIData);
        jpSPI.add(jbSPIAdd);
        
        jpSPI.add(jtfSPIAddr);
        jpSPI.add(jtfSPICLK);
        jpSPI.add(jtfSPISTR);
        jpSPI.add(jtfSPIDAT);
        jpSPI.add(jtfSPIMask);
        jpSPI.add(jtfSPIData);
        jpSPI.add(jcbSPIStrob);
        
        return jpSPI;
    }
    
    private JPanel jpTMI() {
        JPanel jpTMI = new JPanel();
        jpTMI.setPreferredSize(new Dimension(width, height));
        jpTMI.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        addBorderTitle("TMI", jpTMI);
        
        JLabel jlTMIAddr = new JLabel("Номер платы");
        c.insets = new Insets(1,1,1,1);  // padding
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        jpTMI.add(jlTMIAddr, c);
        
        JLabel jlTMIChannel = new JLabel("Номер канала");
        c.insets = new Insets(1,1,1,1);  // padding
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        jpTMI.add(jlTMIChannel, c);
        
        jtfTMIAddr = new JTextField(); //адрес
        jtfTMIAddr.setText("01");
        c.insets = new Insets(1,1,10,1);  // padding
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.0;
        c.ipady = 5;
        c.gridx = 0;
        c.gridy = 1;
        jpTMI.add(jtfTMIAddr, c);
        
        jtfTMIChannel = new JTextField(); //номер канала
        jtfTMIChannel.setText("01");
        c.insets = new Insets(1,1,10,1);  // padding
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.0;
        c.ipady = 5;
        c.gridx = 1;
        c.gridy = 1;
        jpTMI.add(jtfTMIChannel, c);
        
        jbTMIAdd = new JButton("Добавить");
        c.weightx = 0.5;
        c.insets = new Insets(5,240,1,1);  // top padding
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        jpTMI.add(jbTMIAdd, c);
        
        jbTMIAdd.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String str = "";
                if(!testStr(jtfTMIAddr.getText()))
                    return;
                str+=jtfTMIAddr.getText();
                
                if(!testStr(jtfTMIChannel.getText()))
                    return;
                str+=jtfTMIChannel.getText();

                CommandFile.addToFile(str);
            }

        });
        
        return jpTMI;
    }

    static void addBorderTitle(String str, JPanel jp) { // for drawing borders
        TitledBorder titled;
        titled = BorderFactory.createTitledBorder(str);
        titled.setTitleJustification(TitledBorder.CENTER);
        titled.setTitlePosition(TitledBorder.DEFAULT_POSITION);
        jp.setBorder(titled);
    }

    //тестирование имени файла
    private boolean testName(String name) {
        if (name.length() == 0) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.INFO, "Name isn't entered");
            Error.showError(2, null);
            return false;//имя не введено
        }
        String[] afterSplit = name.split("\\.");
        if (afterSplit.length <= 1) {
            System.out.println("lenght after split: " + afterSplit.length);
            Error.showError(3, null);
            Logger.getLogger(MainPanel.class.getName()).log(Level.INFO, "It isn't specified file extension");
            return false;//не указано расширение файла (нет точки)
        }
        if ((afterSplit[afterSplit.length - 1].length() < 3) || (afterSplit[afterSplit.length - 1].length() > 4)) {
            Error.showError(4, null);
            Logger.getLogger(MainPanel.class.getName()).log(Level.INFO, "File extension isn't correct");
            return false;//не указано правильное расширение (3-4 символа)
        }
        return true;
    }
    // проверка корректности текста, набранного в JTextField-ах
    private boolean testStr(String text) {
        if ((text.length() % 2) != 0) {
            Error.showError(5, null);
            return false;
        }
        try {
        Integer.parseInt(text, 16);
        }catch (NumberFormatException e) {
            Error.showError(6, null);
        }
        return true;
    }
}
