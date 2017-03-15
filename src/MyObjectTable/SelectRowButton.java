/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTable;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JToggleButton;

/**
 *
 * @author KOCMOC
 */
public class SelectRowButton extends JToggleButton implements MouseListener {

    private int SELECTED_ROW;
    private String SELECTED_DATABASE_ID;
    private final ArrayList<SelectRowButtonPressedListener> select_row_btn_listener_clients = new ArrayList<SelectRowButtonPressedListener>();

    public SelectRowButton(String string) {
        super(string);
        this.addMouseListener(this);
    }

    public void addSelectRowBtnPressedListener(SelectRowButtonPressedListener srbpl) {
        select_row_btn_listener_clients.add(srbpl);
    }

    public void setSelectedRow(int selected_row) {
        this.SELECTED_ROW = selected_row;
    }

    public void setSelectedDatabaseId(String selected_db_id) {
        this.SELECTED_DATABASE_ID = selected_db_id;
    }

    public int getSelectedRow() {
        return SELECTED_ROW;
    }

    public String getSelectedDatabaseId() {
        return SELECTED_DATABASE_ID;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        for (SelectRowButtonPressedListener selectRowButtonPressedListener : select_row_btn_listener_clients) {
            selectRowButtonPressedListener.selectRowBtnPressed(me,SELECTED_ROW, SELECTED_DATABASE_ID);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
