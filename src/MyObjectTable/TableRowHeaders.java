/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTable;

import MyObjectTable.TableRow;
import MyObjectTable.Table;
import MyObjectTable.RowData;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author KOCMOC
 */
public class TableRowHeaders extends TableRow {

    private final Font TABLE_HEADERS_FONT = new Font("Arial", Font.PLAIN, 24);

    public TableRowHeaders(RowData rowColumnObjects, String database_id, int row_nr, int layout, Table table) {
        super(rowColumnObjects, database_id, row_nr, layout, table);
    }

    @Override
    protected void addColumn(Object obj) {
        Component add_component = null;
        //
        if (obj instanceof String) {
            JLabel label = new JLabel("<html><p style='margin-left:10px; font-size:13pt;font-weight:bold;'>"
                    + (String) obj + "</p></html>");//(String) obj
            label.setBorder(BorderFactory.createRaisedBevelBorder());
            add_component = label;
            addComponent(add_component);
        }
        //
        if (add_component != null) {
            JComponent component = (JComponent) add_component;
            component.addAncestorListener(this);
        }
        //
        getTable().row_col_object__column_count__map.put(add_component, COLUMN_COUNT);
        //
        COLUMN_COUNT++;
    }

}
