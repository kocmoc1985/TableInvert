/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTable;

import MyObjectTableInvert.UnsavedEntryInvert;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import supplementary.HelpA;
import supplementary.SqlBasicLocal;

/**
 *
 * @author KOCMOC
 */
public class Table extends JPanel implements ComponentListener, SelectRowButtonPressedListener {

    private SqlBasicLocal sql;
    //
    public JScrollPane scrollPane;
    private int CURRENT_ROW;
    private String CURRENT_DATABASE_ID;
    private int SELECTED_ROW;
    private String SELECTED_DATABASE_ID;
    private final int NR_ROWS;
    public int ROW_COUNTER;
    private SelectRowButton SELECT_ROW_BTN;
    public final TableData TABLE_DATA;
    private final HashMap<String, String> row_nr__database_id_map = new HashMap<String, String>();
    private String[] TABLE_HEADERS;
    public HashMap<Component, Integer> row_col_object__column_count__map = new HashMap<Component, Integer>();
    public ArrayList<TableRow> rows_list = new ArrayList<TableRow>();
    //
    public HashMap<String, Integer> col_name__row_nr__map = new HashMap<String, Integer>();// used for "Invert"
    public HashMap<Component, TableRow> row___col_object__map = new HashMap<Component, TableRow>();// used for "Invert"
    public HashMap<Component, String> row_col_object__db_id__map = new HashMap<Component, String>();// used for "Invert"
    public HashMap<Component, String> row_col_object__column_name__map = new HashMap<Component, String>();// used for "Invert"
    public HashMap<Component, String> row_col_object__column_nick_name__map = new HashMap<Component, String>();// used for "Invert"
    public HashMap<Object, UnsavedEntryInvert> unsaved_entries_map = new HashMap<Object, UnsavedEntryInvert>();
    //
    //
    public int[] COLUMN_WIDTHS_PERCENT = {25, 25, 25, 12, 10};
    public int ROW_HEIGHT = 45;
    public int ROW_WIDTH_INITIAL;
    public int ROW_WIDTH_MINUS = 20;
    public int TABLE_ROW_LAYOUT = TableRow.FLOW_LAYOUT;

    public Table(TableData data, String[] headers, int row_layout, int row_height, int[] column_width_percent) {
        super(new FlowLayout(FlowLayout.LEFT));
        this.TABLE_ROW_LAYOUT = row_layout;
        this.ROW_HEIGHT = row_height;
        this.COLUMN_WIDTHS_PERCENT = column_width_percent;
        //====
        this.TABLE_DATA = data;
        this.TABLE_HEADERS = headers;
        this.ROW_WIDTH_INITIAL = tableInitialSize() - ROW_WIDTH_MINUS;
        this.NR_ROWS = TABLE_DATA.size();
        addHeaders();
        init();
    }

    /**
     * Used by "TableInvert.class"
     *
     * @param data
     * @param container
     * @param row_layout
     * @param row_height
     * @param column_width_percent
     */
    public Table(TableData data, int row_layout, int row_height, int[] column_width_percent) {
        super(new FlowLayout(FlowLayout.LEFT));
        this.TABLE_ROW_LAYOUT = row_layout;
        this.ROW_HEIGHT = row_height;
        this.COLUMN_WIDTHS_PERCENT = column_width_percent;
        //====
        this.TABLE_DATA = data;
        this.NR_ROWS = TABLE_DATA.size();
        init();
    }

    public void setSql(SqlBasicLocal sql) {
        this.sql = sql;
    }

    public SqlBasicLocal getSql() {
        return sql;
    }

    private void init() {
        addDataToTable();
        initTable();
    }

    public void initTable() {
//        setBackground(Color.yellow);
        double height = (rows_list.size() * ROW_HEIGHT) * 1.15;
        setPreferredSize(new Dimension(tableInitialSize(), (int) Math.round(height)));
        scrollPane = new JScrollPane(this);
        this.addComponentListener(this);
    }

    private int tableInitialSize() {
        return TABLE_HEADERS.length * 120;
    }

    private void addHeaders() {
        RowData rcd = new RowData();
        //
        for (int i = 0; i < TABLE_HEADERS.length; i++) {
            rcd.addRowColumnData(TABLE_HEADERS[i]);
        }
        //
        TableRow row = new TableRowHeaders(rcd, "-1", -1, TABLE_ROW_LAYOUT, this);
        rows_list.add(row);
        this.addRow(row);
    }

    public void addDataToTable() {
        for (Object row_column_data_entry : TABLE_DATA) {
            RowData rcd = (RowData) row_column_data_entry;
            TableRow row = new TableRow(rcd, rcd.getDatabaseId(), ROW_COUNTER, TABLE_ROW_LAYOUT, this);
            rows_list.add(row);
            this.addRow(row);
            this.ROW_COUNTER++;
        }
    }

    public void deleteRows() {
        for (TableRow row : rows_list) {
            remove(row);
        }
        rows_list = new ArrayList<TableRow>();
    }

    public void deleteRow(int row) {
        if (row == 0) {
            return;
        }
        this.remove(getRow(row));
        this.updateUI();
    }

    public int getNrRows() {
        return this.NR_ROWS;
    }
    
   

    public void addRow(TableRow row) {
        if (row instanceof TableRowHeaders) {
            this.add(row);
        } else {
            row_nr__database_id_map.put("" + row.getDatabaseId(), "" + ROW_COUNTER);
            this.add(row);
        }
    }

    public String toCSV() {
        String csv_string = "";
        //
        for (TableRow row : rows_list) {
            //
            if (row.getRowNr() == 0) {
                continue;
            }
            //
            for (int i = 0; i < row.getColumnCount(); i++) {
                String value = getValueAt(row.getRowNr(), i);
                //
                if (value == null) {
                    continue;
                }
                value = HelpA.extractValueFromHtmlString(value);
                //
                csv_string += value + ";";
                if ((i + 1) == row.getColumnCount()) {
                    csv_string += "\n";
                }
            }
        }
        return csv_string;
    }

    public String getValueAt(int row, int column) {
        TableRow tableRow = getRow(row);
        String value = tableRow.getValueAt(column);
        return value;
    }
    
    

    public TableRow getRow(int i) {
        return (TableRow) this.getComponent(i);
    }

    public TableRow getRowByDatabaseId(int db_id) {
        int row_nr = Integer.parseInt(row_nr__database_id_map.get("" + db_id));
        return getRow(row_nr);
    }

    public void setSelectRowBtn(SelectRowButton srb) {
        this.SELECT_ROW_BTN = srb;
    }

    public SelectRowButton getSelectRowBtn() {
        return SELECT_ROW_BTN;
    }

    public void setCurrentRow(int row) {
        CURRENT_ROW = row;
    }

    public void setCurrentDatabaseId(String id) {
        CURRENT_DATABASE_ID = id;
    }

    public void setSelectedRow(int row) {
        SELECTED_ROW = row;
    }

    public void setSelectedDatabaseId(String id) {
        SELECTED_DATABASE_ID = id;
    }

    public int getCurrentRow() {
        return CURRENT_ROW;
    }
    
    public int getCurrentColumn(Object source) {
        return row_col_object__column_count__map.get((Component) source);
    }

    public String getSelectedDatabaseId() {
        return SELECTED_DATABASE_ID;
    }

    public int getSelectedRow() {
        return SELECTED_ROW;
    }

    public String getCurrentDatabaseId() {
        return CURRENT_DATABASE_ID;
    }

    public Component getTable() {
        return scrollPane;
    }

    @Override
    public void componentResized(ComponentEvent ce) {
        System.out.println("w: " + getWidth());
        resizeRows();
    }

    public void resizeRows() {
        for (TableRow row : rows_list) {
            row.setPreferredSize(new Dimension(this.getWidth() - ROW_WIDTH_MINUS, ROW_HEIGHT));
        }
    }

    @Override
    public void componentMoved(ComponentEvent ce) {
    }

    @Override
    public void componentShown(ComponentEvent ce) {
    }

    @Override
    public void componentHidden(ComponentEvent ce) {
    }

    @Override
    public void selectRowBtnPressed(MouseEvent me, int selected_row, String database_id) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("SELECT BTN CLICKED!");
        System.out.println("TABLE->SelectRowBtnPressedListener:" + getValueAt(selected_row, 2));
        System.out.println("SELECTED_ROW: " + selected_row + "   ID: " + database_id);
    }
    //    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        FontMetrics fm = g.getFontMetrics();
//
//        Dimension size = getPreferredSize();
//        String text = "Pref: " + size.width + "x" + size.height;
//        g.drawString(text, 0, fm.getAscent());
//
//        size = getSize();
//        text = "Size: " + size.width + "x" + size.height;
//        g.drawString(text, 0, fm.getHeight() + fm.getAscent());
//    }
}
