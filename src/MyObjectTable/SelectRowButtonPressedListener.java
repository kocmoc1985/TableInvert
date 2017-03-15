/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MyObjectTable;

import java.awt.event.MouseEvent;

/**
 *
 * @author mcab
 */
public interface SelectRowButtonPressedListener {
    
    public void selectRowBtnPressed(MouseEvent me,int selected_row,String database_id);
    
}
