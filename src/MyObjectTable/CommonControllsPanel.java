/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTable;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author mcab
 */
public class CommonControllsPanel extends JPanel implements ActionListener {

    private final ArrayList<Component> cotrolls_list = new ArrayList<Component>();
    private static final URL SAVE_ICON = CommonControllsPanel.class.getResource("save.png");
    private static final URL PRINT_ICON = CommonControllsPanel.class.getResource("print.png");
    private static final URL COPY_ICON = CommonControllsPanel.class.getResource("copy.png");
    private JButton SAVE_BTN;
    private JButton COPY_BUTTON;
    private JButton PRINT_BUTTON;
    private ControlsActionsIF controlsActions;

    public CommonControllsPanel(ControlsActionsIF controlsActions) {
        this.controlsActions = controlsActions;
        init();
    }

    private void init() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
//        this.setPreferredSize(new Dimension(200, 50));
        //
        SAVE_BTN = new JButton(new ImageIcon(SAVE_ICON));
        COPY_BUTTON = new JButton(new ImageIcon(COPY_ICON));
        PRINT_BUTTON = new JButton(new ImageIcon(PRINT_ICON));
        //
        this.add(SAVE_BTN);
        this.add(COPY_BUTTON);
        this.add(PRINT_BUTTON);
        //
        SAVE_BTN.addActionListener(this);
    }

//    public static void main(String[] args) {
//        JFrame jFrame = new JFrame("Test Frame");
//        jFrame.setLayout(new BorderLayout());
//        jFrame.setSize(400, 200);
//
//        CommonControllsPanel ccp = new CommonControllsPanel();
//
//        jFrame.add(ccp, BorderLayout.LINE_START);
//
//        jFrame.setVisible(true);
//    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == SAVE_BTN) {
            controlsActions.applyChanges();
        }
    }
}
