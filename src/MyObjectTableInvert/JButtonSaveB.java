/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import java.awt.event.ActionEvent;
import javax.swing.JButton;

/**
 *
 * @author KOCMOC
 */
public class JButtonSaveB extends JButtonSave {

    public JButtonSaveB(JButton btn, TableInvert ti) {
        super(btn, ti);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
         tableInvert.automaticFieldUpdate();
         tableInvert.applyChanges();
         
    }
}
