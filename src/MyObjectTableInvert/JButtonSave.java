/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author KOCMOC
 */
public class JButtonSave implements ActionListener {

    private JButton button;
    public TableInvert tableInvert;

    public JButtonSave(JButton btn,TableInvert ti) {
        this.button = btn;
        this.tableInvert = ti;
        //
        button.addActionListener(this);
    }

    public JButton getButton() {
        return button;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == button) {
            tableInvert.applyChanges();
        }
    }
    
    
}
