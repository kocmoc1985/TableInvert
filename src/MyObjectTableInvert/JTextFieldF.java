/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class JTextFieldF extends JTextField{
   private String realValue;
   private String fakeValue;

    public JTextFieldF(String fakeValue,String realValue) {
        super(fakeValue);
        this.realValue = realValue;
        this.fakeValue = fakeValue;
    }

    public String getRealValue() {
        return realValue;
    }

    public String getFakeValue() {
        return fakeValue;
    }
   
}
