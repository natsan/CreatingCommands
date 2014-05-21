/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commandtofile;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author stend
 */
public class Error {

    public static void showError(int number, JPanel jp) {
        switch (number) {
            case 1:
                JOptionPane.showMessageDialog(jp,
                        new String[]{"Файл с таким именем уже существует"}, "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(jp,
                        new String[]{"Не введено имя файла"},
                        "Внимание", JOptionPane.WARNING_MESSAGE);
                break;
            case 3:
                JOptionPane.showMessageDialog(
                        jp,
                        new String[]{"Не задано расширение файла"},
                        "Внимание", JOptionPane.WARNING_MESSAGE);
                break;
            case 4:
                JOptionPane.showMessageDialog(jp,
                        new String[]{"<html><center>Расширение файла не корректно.<br>Расширение включает в себя 3-4 символа"}, "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                break;
            case 5:
                JOptionPane.showMessageDialog(jp,
                        new String[]{"<html><center>Число символов должно быть чётным.<br><Необходимо вводить 0102... и т. п."}, "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                break;
            case 6:
                JOptionPane.showMessageDialog(jp,
                        new String[]{"Все значения необходимо вводить в формате HEX"}, "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                break;
            case 7:
                JOptionPane.showMessageDialog(jp,
                        new String[]{"Не создан файл для записи команд"}, "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(jp,
                        new String[]{"Неизвестная ошибка"}, "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                break;
        }
    }
    
}
