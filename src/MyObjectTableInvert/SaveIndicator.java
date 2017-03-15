/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import images.IconUrls;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author KOCMOC
 */
public class SaveIndicator implements Runnable {

    private JButton saveBtn;
    private ImageIcon initialIcon;
    private SaveIndicatorIF unsavedList;
    private int NR;
    private boolean flag = true;

    public SaveIndicator(JButton saveBtn, SaveIndicatorIF ul, int nr) {
        this.saveBtn = saveBtn;
        this.unsavedList = ul;
        this.NR = nr;
        startThisThread();
    }

    private void startThisThread() {
        Thread x = new Thread(this);
        x.start();
    }

    @Override
    public void run() {
        //
        while (true) {
            //
            if (unsavedList.getUnsaved(NR)) {
                if (flag) {
                    initialIcon = (ImageIcon) saveBtn.getIcon();
                    //
                    ImageIcon icon = new ImageIcon(IconUrls.UNSAVED_ICON_URL);
                    //
                    saveBtn.setIcon(icon);
                }
                //
                flag = false;
                wait_(1000);
            } else {
                if (initialIcon != null) {
                    saveBtn.setIcon(initialIcon);
                }
                initialIcon = null;
                flag = true;
                wait_(1000);
            }
            //
        }
        //
    }

    private void wait_(int millis) {
        synchronized (this) {
            try {
                wait(millis);
            } catch (InterruptedException ex) {
                Logger.getLogger(SaveIndicator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public interface SaveIndicatorIF {

        public boolean getUnsaved(int nr);

        public void initializeSaveIndicators();
    }
}
