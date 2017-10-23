/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import MyObjectTable.RowData;
import MyObjectTable.Table;
import MyObjectTable.TableRow;
import supplementary.HelpA;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import supplementary.JPanelImg;

/**
 *
 * @author mcab
 */
public class TableRowInvert extends TableRow implements KeyListener, ItemListener {

    private ArrayList<TableRowInvertListener> tableRowListenerList = new ArrayList<TableRowInvertListener>();

    public TableRowInvert(RowData rowColumnObjects, String database_id, int row_nr, int layout, Table table) {
        super(rowColumnObjects, database_id, row_nr, layout, table);
        gridLayoutFix();
    }

    public void addTableRowInvertListener(TableRowInvertListener tril) {
        tableRowListenerList.add(tril);
    }

    /**
     * OBS! This one fixes the empty space on the both sides of a row
     */
    private void gridLayoutFix() {
        if (this.getLayout() instanceof GridLayout) {
            GridLayout gridLayout = (GridLayout) this.getLayout();
            gridLayout.setHgap(5);
        }
    }

    @Override
    public RowDataInvert getRowConfig() {
        return (RowDataInvert) ROW_COLUMN_DATA;
    }

    @Override
    protected void addColumn(Object obj) {
        Component add_component = null;
        //
        TableInvert table_invert = (TableInvert) getTable();
        //
        if (obj instanceof HeaderInvert) {
            HeaderInvert hi = (HeaderInvert) obj;
            //
            if (hi.isUnitHeader() == false) {
                JLabel label = new JLabel("<html><p style='margin-left:5px;font-weight:bold'>" + hi.getHeader() + "</p></html>");
                //
                HelpA.setTrackingToolTip(label, hi.getRealColName() + " / " + hi.getTableName());
                //
                add_component = label;
                addComponent(add_component);
            } else {
                if (hi.getHeader() instanceof String) {
                    JLabel label = new JLabel("<html><p style='margin-left:5px;font-weight:bold;color:gray'>" + hi.getHeader() + "</p></html>");
                    add_component = label;
                    addComponent(add_component);
                } else if (hi.getHeader() instanceof JLabel) {
                    JLabel label = (JLabel) hi.getHeader();
                    add_component = label;
                    addComponent(add_component);
                } else if (hi.getHeader() instanceof JPanel) {
                    JPanel panel = (JPanel) hi.getHeader();
                    add_component = panel;
                    addComponent(add_component);
                }
            }
            //

        }

        if (obj instanceof ColumnDataEntryInvert) {
            //
            ColumnDataEntryInvert cde = (ColumnDataEntryInvert) obj;
            //
            if (cde.getObject() instanceof String) {
                //
                JTextField jtf;
                //
                if (getRowConfig().fakeValueEnabled()) {
                    String realVal = (String) cde.getObject();
                    String fakeVal = FakeValuesMaps.TEXT_FIELD_FAKE_VALUES_MAP.get(realVal.trim());
                    jtf = new JTextFieldF(fakeVal, realVal);
                    jtf.setMargin(new Insets(5, 5, 5, 5));
                } else {
                    jtf = new JTextField((String) cde.getObject());
                    jtf.setMargin(new Insets(5, 5, 5, 5));
                }
                //
                if (getRowConfig().getImportant()) {
                    jtf.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
                }
                //
                if (getRowConfig().isEditable() == false) {
                    jtf.setEditable(false);
                }
                //
                if (getRowConfig().toolTipTextEnabled()) {
                    jtf.setToolTipText((String) cde.getObject());
                }
                //
                add_component = jtf;
                //
                addComponent(add_component);
                add_component.addKeyListener(this);
                //
            } else if (cde.getObject() instanceof JButton) {
                add_component = (Component) cde.getObject();
                addComponent(add_component);
            } else if (cde.getObject() instanceof JComboBox) {
                //
                add_component = (Component) cde.getObject();
                addComponent(add_component);
                JComboBox box = (JComboBox) add_component;
                box.addItemListener(this);
                //
                if (getRowConfig().isEditable() == false) {
                    box.setEditable(false);
                }
                //
            }
            //
            table_invert.row_col_object__column_count__map.put(add_component, COLUMN_COUNT);
            table_invert.row_col_object__db_id__map.put(add_component, cde.getDatabase_id());
            table_invert.row_col_object__column_name__map.put(add_component, cde.getOriginalColumn_name());
            table_invert.row_col_object__column_nick_name__map.put(add_component, cde.getColumn_nick_name());
            table_invert.row___col_object__map.put(add_component, this);
            table_invert.col_name__row_nr__map.put(cde.getOriginalColumn_name(), ROW_NR);
        }

        if (add_component != null) {
            //
            JComponent component = (JComponent) add_component;
            add_component.addMouseListener(this);
            component.addAncestorListener(this);
            //
        }
        //        
        COLUMN_COUNT++;
//        System.out.println("COLUMN_COUNT:" + COLUMN_COUNT);
    }

    public String getTableName() {
//        return getRowConfig().getTableName();
        TableInvert ti = (TableInvert) getTable();
        return ti.getTABLE_NAME();
    }

    private void set_current_row__and__database_id(Object source) {
        Table t = getTable();
        t.setCurrentRow(ROW_NR);
        t.setCurrentDatabaseId("" + t.row_col_object__db_id__map.get((Component) source));
//        System.out.println("selected_row: " + t.getCurrentRow() + " / database_id: " + t.getCurrentDatabaseId());
    }

    private boolean isHeaderComponent(Object source) {
        if (getTable().row_col_object__db_id__map.containsKey((Component) source)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object getComponentAt(int column_index) {
        return this.getComponent(column_index);
    }

    public String getValueCurrentColumn(Object source) {
        TableInvert ti = (TableInvert) getTable();
        int col = ti.getCurrentColumn(source);
        return getValueAt(col);
    }

    @Override
    public String getValueAt(int column_index) {
        //
        Component c = this.getComponent(column_index);
        //
        if (c instanceof JLabel) {
            JLabel label = (JLabel) c;
            return label.getText();
        } else if (c instanceof JComboBoxInvert) {
            JComboBoxInvert comboBox = (JComboBoxInvert) c;
//            return HelpA.getComboBoxSelectedValue(comboBox);
            return comboBox.getComboBoxSelectedValue();
        } else if (c instanceof JTextFieldF) {
            JTextFieldF jtf = (JTextFieldF) c;
            return jtf.getRealValue();
        } else if (c instanceof JTextField) {
            JTextField jtf = (JTextField) c;
            return (String) jtf.getText();
        } else if (c instanceof JButton) {
            JButton jb = (JButton) c;
            return (String) jb.getText();
        } else {
            return null;
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //
        Object source = me.getSource();
        //
        if (isHeaderComponent(source)) {
            return;
        }
        //
        current_row_highlight(me.getSource());
        //
        TableInvert ti = (TableInvert) getTable();
        set_current_row__and__database_id(source);
        System.out.println("\n==============================");
//        System.out.println("Value: " + getValueAt(ti.getCurrentColumn(source)));
        System.out.println("Value: " + ti.getValueCurrentColumn(source));
        System.out.println("Row_nr: " + ti.getCurrentRow());
        System.out.println("Column_nr: " + ti.getCurrentColumn(source));
        System.out.println("DB_ID: " + ti.getCurrentDatabaseId());
        System.out.println("Column name: " + ti.getCurrentColumnName(source));
        System.out.println("Column NickName: " + ti.getCurrentColumnNickName(source));
        System.out.println("TABLE_NAME: " + ti.getTABLE_NAME());// OBS!
        System.out.println("==============================");
        //
        //
        //
        //
        Object col_nr = ti.getCurrentColumn(source);
        //
        if (col_nr == null) {
            return;
        }
        //
        ArrayList table_fields_list = ti.getComponentsWithColNr((Integer) col_nr);
        //
        for (Object field : table_fields_list) {
            //
            if (field instanceof JTextField == false) {
                break;
            }
            //
            JTextField jtf = (JTextField) field;
            //
//            System.out.println("value:" + jtf.getText());
            //
            //
//            TableRowInvert tri = (TableRowInvert) ti.row___col_object__map.get(field);
            TableRowInvert tri = ti.getTableRowInvertByComponent(field);
            RowDataInvert rcdi = tri.getRowConfig();
//            RowDataInvert rcdi_2 = ti.getRowConfig(1);
            //

        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        //
        TableInvert t = (TableInvert) getTable();
        //
        for (TableRowInvertListener tril : tableRowListenerList) {
            //
            if(me.getSource() instanceof JLabel || me.getSource() instanceof JPanelImg){
                continue;
            }
            //
            tril.mouseClicked(me.getSource(), t);
        }
        //
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        Object source = ke.getSource();
        //
        add_to_unsaved(source);
        //

    }

    public void add_to_unsaved(Object source) {
        TableInvert table = (TableInvert) getTable();
//        String db_id = table.getCurrentDatabaseId();
        String db_id = table.row_col_object__db_id__map.get((Component) source);
        set_current_row__and__database_id(source);
        //
        // OBS! OBS!
        HashMap unsaved_entries_map = getTable().unsaved_entries_map;
        //
        String col_name = table.getCurrentColumnName(source);
        //
        String tableName = getRowConfig().getTableName();
        String primaryOrForeignKeyName = getRowConfig().getPrimaryOrForeignKeyName();
        boolean isString = getRowConfig().isString();
        boolean keyIsString = getRowConfig().getKeyIsString();
        String updateOtherTablesBefore = getRowConfig().getUpdateOtherTablesBefore();
        int colNr = table.getCurrentColumn(source);
        //
        if (unsaved_entries_map.containsKey(source)) {
            unsaved_entries_map.remove(source);
            //
            UnsavedEntryInvert entryInvert_update = new UnsavedEntryInvert(source, tableName, primaryOrForeignKeyName, db_id, col_name, colNr, isString, keyIsString, updateOtherTablesBefore);
            unsaved_entries_map.put(source, entryInvert_update);
        } else {
            unsaved_entries_map.put(source, new UnsavedEntryInvert(source, tableName, primaryOrForeignKeyName, db_id, col_name, colNr, isString, keyIsString, updateOtherTablesBefore));
        }
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        Object source = ie.getSource();
        //
        add_to_unsaved(source);
    }
}
