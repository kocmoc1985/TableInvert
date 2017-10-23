/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import MyObjectTable.ControlsActionsIF;
import MyObjectTable.RowData;
import MyObjectTable.Table;
import MyObjectTable.TableData;
import MyObjectTable.TableRow;
import Reporting.InvertTableRow;
import Reporting.TableInvertBasicRepport;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import sql.SqlBasicLocal;
import supplementary.HelpA;

/**
 *
 * @author mcab
 */
public  class TableInvert extends Table implements ControlsActionsIF {

    private String TABLE_NAME = "";
    private boolean SHOW_UNITS = false;
    private boolean IS_EMPTY = false;

    public TableInvert(TableData data, int row_layout, int row_height, int[] column_width_percent, String tableName) {
        super(data, row_layout, row_height, column_width_percent);
        this.TABLE_NAME = tableName;
    }

    public void setShowUnits(boolean show) {
        SHOW_UNITS = show;
    }

    public boolean getShowInits() {
        return SHOW_UNITS;
    }

    public void setMargin(int top, int left, int bottom, int right) {
        setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    @Override
    public void componentResized(ComponentEvent ce) {
        resizeRows();
    }

    @Override
    public void resizeRows() {
        for (TableRow row : rows_list) {
            row.setPreferredSize(new Dimension(getWidth() - 10, ROW_HEIGHT));
        }
    }

    @Override
    public void initTable() {
        double height = (rows_list.size() * ROW_HEIGHT) * 1.13;//1.13
        RowData rcd = (RowData) TABLE_DATA.get(0);
        //
        //
        setPreferredSize(new Dimension(rcd.getNrColumns() * 150, (int) Math.round(height)));
        scrollPane = new JScrollPane(this);
//        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.addComponentListener(this);

    }

    @Override
    public void addDataToTable() {
        //
        for (Object row_column_data_entry : TABLE_DATA) {
            RowDataInvert rcd = (RowDataInvert) row_column_data_entry;
            TableRowInvert row = new TableRowInvert(rcd, "-1", ROW_COUNTER, TABLE_ROW_LAYOUT, this);
            //
            rows_list.add(row);
            //
            if (rcd.getVisible() == false) {
                row.setVisible(false);
            }
            //
            this.addRow(row);
            this.ROW_COUNTER++;
        }

    }

    public void addTableRowInvertListener(TableRowInvertListener tril) {
        for (TableRow row : rows_list) {
            TableRowInvert tri = (TableRowInvert) row;
            tri.addTableRowInvertListener(tril);
        }
    }

    public HashMap<String, ColumnValue> getColumnData(int colNr) {
        HashMap<String, ColumnValue> map = new HashMap<String, ColumnValue>();
        //
        Set set = row_col_object__column_count__map.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            //
            Component c = (Component) it.next();
            int col_nr = row_col_object__column_count__map.get(c);
            //
            if (col_nr == colNr) {
                String nickName = row_col_object__column_nick_name__map.get(c);
//                String origName =  row_col_object__column_name__map.get(c);
                map.put(nickName, new ColumnValue(c));
            }
        }
        return map;
    }

    public void setTableEmpty(boolean not_empty) {
        if (not_empty) {
            this.IS_EMPTY = false;
        } else {
            this.IS_EMPTY = true;
        }
    }

    public boolean tableEmpty() {
        return IS_EMPTY;
    }

    public ArrayList getComponentsWithColNr(int colNr) {
        ArrayList list = new ArrayList();
        //
        Set set = row_col_object__column_count__map.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Component c = (Component) it.next();
            int col_nr = row_col_object__column_count__map.get(c);
            if (col_nr == colNr) {
                list.add(c);
            }
        }
        return list;
    }

    public void addToUnsaved(String rowName, int column) {
        Object obj = getComponentAt(rowName, column);
        TableRow tableRow = getRow(0);
        TableRowInvert tri = (TableRowInvert) tableRow;
        tri.add_to_unsaved(obj);
    }

    public void clearAllRows() {
        for (Object rowInvert : rows_list) {
            TableRowInvert tri = (TableRowInvert) rowInvert;
            //
            Object obj;
            //
            try {
                obj = tri.getComponentAt(2);
            } catch (Exception ex) {
                obj = tri.getComponentAt(1);
            }
            //
            if (obj instanceof JTextField) {
                JTextField jtf = (JTextField) obj;
                jtf.setText("");
            } else if (obj instanceof JComboBox) {
                JComboBox jcb = (JComboBox) obj;
                jcb.setSelectedItem("");
            }
        }
    }

    public void clearRows(int start, int notToClearFromEnd) {
        for (int i = start; i < rows_list.size() - notToClearFromEnd; i++) {
            Object rowInvert = rows_list.get(i);
            TableRowInvert tri = (TableRowInvert) rowInvert;
            //
            Object obj;
            //
            try {
                obj = tri.getComponentAt(2);
            } catch (Exception ex) {
                obj = tri.getComponentAt(1);
            }
            //
            if (obj instanceof JTextField) {
                JTextField jtf = (JTextField) obj;
                jtf.setText("");
            } else if (obj instanceof JComboBox) {
                JComboBox jcb = (JComboBox) obj;
                jcb.setSelectedItem("");
            }
        }
    }

    public void clearRows(int start) {
        clearRows(start, 0);
    }

    public RowDataInvert getRowConfig(int row) {
        TableRowInvert rowInvert = getRow(row);
        return rowInvert.getRowConfig();
    }

    @Override
    public TableRowInvert getRow(int i) {
        return (TableRowInvert) this.getComponent(i);
    }

    public void setValueAt(String rowName, Object value, int column) {
        TableRow tableRow = getRow(getRowNrByName(rowName));
        tableRow.setValueAt(column, value);
    }

    public void setValueAt(String rowName, Object value) {
        TableRow tableRow = getRow(getRowNrByName(rowName));
        tableRow.setValueAt(1, value);
    }

    public Object getComponentAt(String rowName, int column) {
        TableRow tableRow = getRow(getRowNrByName(rowName));
        return tableRow.getComponentAt(column);
    }

    public String getValueAt(String rowName) {
        TableRow tableRow = getRow(getRowNrByName(rowName));
        String value = tableRow.getValueAt(1);
        return value;
    }
    
    public String getValueCurrentColumn(Object source){
        TableRowInvert tableRow = (TableRowInvert)getRow(getCurrentRow()-1);
        return tableRow.getValueCurrentColumn(source);
    }

    public String getValueAt(String rowName, int column) {
        TableRow tableRow = getRow(getRowNrByName(rowName));
        String value = tableRow.getValueAt(column);
        return value;
    }

    public int getColumnCount() {
        TableRowInvert tableRowInvert = (TableRowInvert) rows_list.get(0);
        return tableRowInvert.getColumnCount();
    }

    public int getRowNrByName(String name) {
        return col_name__row_nr__map.get(name) - 1;
    }

    public String getCurrentColumnName(Object source) {
        return row_col_object__column_name__map.get((Component) source);
    }

    public String getCurrentColumnNickName(Object source) {
        return row_col_object__column_nick_name__map.get((Component) source);
    }

    public TableRowInvert getTableRowInvertByComponent(Object field) {
        return (TableRowInvert) row___col_object__map.get((Component) field);
    }

    class Entry {

        private UnsavedEntryInvert unsavedEntryInvert;
        private String RowName;

        public Entry(UnsavedEntryInvert unsavedEntryInvert, String RowName) {
            this.unsavedEntryInvert = unsavedEntryInvert;
            this.RowName = RowName;
        }

        public String getRowName() {
            return RowName;
        }

        public UnsavedEntryInvert getUnsavedEntryInvert() {
            return unsavedEntryInvert;
        }
    }

    public void handleAutomaticFieldUpdate(String rowName, String value) {
        Set<Entry> list = new HashSet<Entry>();
        //
        Set set = unsaved_entries_map.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Object key = it.next();
            UnsavedEntryInvert unsavedEntryInvert = unsaved_entries_map.get(key);
            list.add(new Entry(unsavedEntryInvert, rowName));
        }
        //
        for (Entry entry : list) {
            handle(entry.getUnsavedEntryInvert(), entry.getRowName(), value);
        }
        //
    }

    private void handle(UnsavedEntryInvert unsavedEntryInvert, String rowName, String value) {
        int colNr = unsavedEntryInvert.getColumnNr();
        //
        if (columnNameExists(rowName, colNr)) {
            changeValueNoSave(rowName, colNr, value);
        }
    }

    private void changeValueNoSave(String rowName, int colNr, String value) {
        setValueAt(rowName, value, colNr);
        addToUnsaved(rowName, colNr);
    }

    @Override
    public void applyChanges() {
        //
        Set set = unsaved_entries_map.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Object key = it.next();
            UnsavedEntryInvert unsavedEntryInvert = unsaved_entries_map.get(key);

            String updateOtherTablesBeforeInstruction = unsavedEntryInvert.getUpdateOtherTablesBefore();

            if (updateOtherTablesBeforeInstruction.isEmpty() == false) {
                UpdateBefore.updateBefore(unsavedEntryInvert, getSql(), updateOtherTablesBeforeInstruction);
            }

            updateFieldString(
                    unsavedEntryInvert.getTableName(),
                    unsavedEntryInvert.getColumnName(),
                    unsavedEntryInvert.getValue(),
                    unsavedEntryInvert.getPrimareyOrForeignKeyName(),
                    unsavedEntryInvert.getDbID(),
                    unsavedEntryInvert.isString(),
                    unsavedEntryInvert.keyIsString());
            //
            try {
                //
                Component c = (Component) unsavedEntryInvert.getDataField();
                c.setForeground(Color.green);
                //
                if (c instanceof JComboBox) {
                    JComboBox box = (JComboBox) c;
                    box.setBorder(BorderFactory.createLineBorder(Color.green, 2));
                }
                //
            } catch (Exception ex) {
                Logger.getLogger(TableInvert.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
        unsaved_entries_map.clear();
        //
    }

    private void updateFieldString(String tableName,
            String columnName,
            String value,
            String keyName,
            String db_id,
            boolean isString,
            boolean keyIsString) {
        //
        try {
            //
            SqlBasicLocal sql = getSql();
            //
            sql.prepareStatement("UPDATE " + tableName
                    + " SET [" + columnName + "]=" + "?" + ""
                    + " WHERE " + keyName + "=" + "?" + "");
            //
            String dateFormat = HelpA.define_date_format(value);
            //
            if (dateFormat != null) {
                long millis = HelpA.dateToMillisConverter3(value, dateFormat);
                Timestamp timestamp = new Timestamp(millis);
                sql.getPreparedStatement().setTimestamp(1, timestamp);
            } else {
                if (isString) {
                    sql.getPreparedStatement().setString(1, value);
                } else {
                    sql.getPreparedStatement().setInt(1, Integer.parseInt(value));
                }
            }
            //
            if (keyIsString) {
                sql.getPreparedStatement().setString(2, db_id);
            } else {
                sql.getPreparedStatement().setInt(2, Integer.parseInt(db_id));
            }
            //
            sql.getPreparedStatement().executeUpdate();
            //
        } catch (SQLException ex) {
            Logger.getLogger(TableInvert.class.getName()).log(Level.SEVERE, null, ex);
        }

//        return "UPDATE " + tableName
//                + " SET [" + columnName + "]=" + value + ""
//                + " WHERE " + keyName + "=" + db_id + "";

    }

    private boolean columnNameExists(String rowName, int colNr) {
        try {
            getValueAt(rowName, colNr);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    //==========================================================================
    public void showTableInvert(final JComponent container) {
        container.setLayout(new GridLayout());
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Thread x = new Thread(new RepaintThread(container));
                x.setPriority(Thread.MAX_PRIORITY);
                x.start();
            }
        });
    }

    public class RepaintThread implements Runnable {

        private final JComponent container;

        public RepaintThread(JComponent container) {
            this.container = container;
        }

        @Override
        public void run() {
            //
            if (this == null) {
                return;
            } else {
                container.removeAll();
            }
            //OBS! OBS! Don't forget that the JPanel to which "TABLE_INVERT" is added
            //shall have Layout = "GridLayout"
            container.add(getTable());
            container.invalidate();
            container.validate();
//            container.updateUI(); // This causes "JavaEventQue Exceptions"
            container.repaint();
            //
//            container.setPreferredSize(new Dimension(getTable().getWidth()+5, getTable().getHeight()+10));
            //
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    SwingUtilities.updateComponentTreeUI(getTable());
                }
            });
        }
    }

    public void setVerticalScrollBarDisabled() {
        JScrollPane jsp = (JScrollPane) getTable();
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    }

    public void changeValueAndSave(Object newValue, String rowName, int column) {
        setValueTableInvert(rowName, newValue);
        addToUnsaved(rowName, column);
        applyChanges();
    }

    public void changeValueAndSaveWithAutomaticFieldUpdate(Object newValue, String rowName, int column) {
        setValueTableInvert(rowName, newValue);
        addToUnsaved(rowName, column);
        automaticFieldUpdate();
        applyChanges();
    }

    public void changeValueNoSave(Object newValue, String rowName, int column) {
        setValueTableInvert(rowName, newValue);
        addToUnsaved(rowName, column);
    }

    public boolean unsavedEntriesExist() {
        if (unsaved_entries_map.isEmpty() == false) {
            return true;
        } else {
            return false;
        }
    }

    public void setValueTableInvert(String rowName, Object value) {
        setValueAt(rowName, value, defineValueColumnIndex());
    }

    public boolean columnNameExists(String colName, Table tableInvert) {
        try {
            getValueAt(colName, defineValueColumnIndex());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void automaticFieldUpdate() {
        handleAutomaticFieldUpdate("UpdatedOn", HelpA.updatedOn());
        handleAutomaticFieldUpdate("UpdatedBy", HelpA.updatedBy());
    }

    public int defineValueColumnIndex() {
        if (getShowInits() == false) {
            return 1;
        } else {
            return 2;
        }

    }

    public void tableInvertRepport(int startColumn, RowDataInvert[] cfg) {
        String csv = tableInvertToCSV(startColumn, cfg, false);
        makeRepportTableInvert(csv);
    }

    private void makeRepportTableInvert(String csv) {
        String[] lines = csv.split("\n");
        //
        ArrayList<InvertTableRow> tableRowsList = new ArrayList<InvertTableRow>();
        //
        for (String line : lines) {
            String arr[] = line.split(";");

            String columnName = arr[0];
            String unit = arr[1];

            InvertTableRow row = new InvertTableRow(columnName, unit);

            for (int i = 2; i < arr.length; i++) {
                row.addValue(arr[i]);
            }
            //
            tableRowsList.add(row);
            //
        }
        //
        new TableInvertBasicRepport(tableRowsList);
        //
    }

    public String tableInvertToCSV(int startColumn, RowDataInvert[] rdi, boolean writeToFile) {
        //
        String csv = "";
        //
        for (RowDataInvert dataInvert : rdi) {
            //
            if (dataInvert.getVisible() == false) {
                continue;
            }
            //
            csv += dataInvert.getFieldNickName() + ";";
            //
            if (dataInvert.getUnit() instanceof String) {
                String unit = (String) dataInvert.getUnit();
                //
                if (unit.isEmpty() == false) {
                    csv += unit + ";";
                } else {
                    csv += "unit" + ";";
                }
                //
            }
            //
            for (int x = startColumn; x < getColumnCount(); x++) {
                //
                HashMap<String, ColumnValue> map = getColumnData(x);
                //
                ColumnValue columnValue = map.get(dataInvert.getFieldNickName());
                //
                csv += columnValue.getValue() + ";";
                // 
            }
            //
            csv += "\n";
            //
        }
        //
//        System.out.println("CSV: \n" + csv);
        //
        //
        String path = HelpA.get_desktop_path() + "\\"
                + HelpA.get_proper_date_time_same_format_on_all_computers_err_output() + ".csv";
        //
        if (writeToFile) {
            try {
                HelpA.writeToFile(path, csv);
//                JOptionPane.showMessageDialog(null, "Export file ready, the file is in: " + path);
                HelpA.run_application_with_associated_application(new File(path));
            } catch (IOException ex) {
                Logger.getLogger(TableInvert.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
        return csv;
    }
    //==========================================================================
}
