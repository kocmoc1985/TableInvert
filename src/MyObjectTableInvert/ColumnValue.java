/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import supplementary.HelpA;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author mcab
 */
public class ColumnValue {

    private String columnNickName;
    private String columnOriginalName;
    final Object value;

    public ColumnValue(Object value) {
        this.value = value;
    }  
    
    public ColumnValue(String columnNickName, String columnOriginalName, Object value) {
        this.columnNickName = columnNickName;
        this.columnOriginalName = columnOriginalName;
        this.value = value;
    }

    public String getColumnNickName() {
        return columnNickName;
    }

    public String getValue() {
        if (value instanceof JLabel) {
            JLabel label = (JLabel) value;
            return label.getText();
        } else if (value instanceof JComboBox) {
            JComboBox comboBox = (JComboBox) value;
            return HelpA.getComboBoxSelectedValue(comboBox);
        } else if (value instanceof JTextField) {
            JTextField jtf = (JTextField) value;
            return (String) jtf.getText();
        } else if (value instanceof JButton) {
            JButton jb = (JButton) value;
            return (String) jb.getText();
        } else {
            return null;
        }
    }

}
