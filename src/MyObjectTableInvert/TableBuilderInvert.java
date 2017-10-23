/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import MyObjectTable.TableData;
import MyObjectTable.TableRow;
import supplementary.HelpA;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import sql.SqlBasicLocal;

/**
 *
 * @author mcab
 */
public class TableBuilderInvert {
    
    private final SqlBasicLocal sql;
    private final RowDataInvert[] CONFIG;
    private final boolean SHOW_UNITS;
    private final String TABLE_NAME;
    
    public TableBuilderInvert(SqlBasicLocal sql, RowDataInvert[] config, boolean showUnits, String tableName) {
        this.sql = sql;
        this.CONFIG = config;
        this.SHOW_UNITS = showUnits;
        this.TABLE_NAME = tableName;
    }
    
    public TableInvert buildTable(String query) throws SQLException {
        //
        ResultSet rs = sql.execute_2(query);
        //
        TableData tableData = new TableData();
        //
        RowDataInvert[] ROWS = CONFIG;
        //
        boolean not_empty = rs.next();
        //
        for (int i = 0; i < ROWS.length; i++) {
            RowDataInvert CURR_ROW = (RowDataInvert) ROWS[i];
            //
            CURR_ROW.addRowColumnData(new HeaderInvert(CURR_ROW.getFieldNickName(),CURR_ROW.getFieldOriginalName(), CURR_ROW.getTableName()));
            //
            if (SHOW_UNITS) {
                CURR_ROW.addRowColumnData(new HeaderInvert(CURR_ROW.getUnit(), true));
            }
            //
            //Inserting empty fields in case if no entries
            if (not_empty == false) {//is empty!
                CURR_ROW.addRowColumnData(new ColumnDataEntryInvert("", "-1", CURR_ROW.getFieldOriginalName(), CURR_ROW.getFieldNickName()));
                tableData.addRowData(CURR_ROW);
                continue;
            }
            //
            //
            rs.beforeFirst();
            //
            String orig_field_name = CURR_ROW.getFieldOriginalName();
            String key_name = CURR_ROW.getPrimaryOrForeignKeyName();
            //
            int type = CURR_ROW.getType();
            //
            while (rs.next()) {
                //
                String value = rs.getString(orig_field_name);
                //
                //
                ColumnDataEntryInvert cde;
                //
                if (type == RowDataInvert.TYPE_COMMON) {
                    cde = new ColumnDataEntryInvert(value, rs.getString(key_name), orig_field_name, CURR_ROW.getFieldNickName());
                } else {
                    cde = new ColumnDataEntryInvert(CURR_ROW.getSpecialComponent(value), rs.getString(key_name), orig_field_name, CURR_ROW.getFieldNickName());
                }
                //
                //
                if (cde.getObject() == null) {
                    cde.setObject("NULL");
                }
                //
                CURR_ROW.addRowColumnData(cde);
                //
            }
            //
            //
            addAdditionalComponent(CURR_ROW, getDefaultRowComponents());
            //
            //
            tableData.addRowData(CURR_ROW);
        }
        //
        //
        int[] arr = {25, 25, 25, 25};
        //
//        TableInvert table = new TableInvert(tableData, TableRow.FLOW_LAYOUT, 45, arr, TABLE_NAME);
        TableInvert table = new TableInvert(tableData, TableRow.GRID_LAYOUT, 45, null, TABLE_NAME);
        table.setShowUnits(SHOW_UNITS);
        table.setSql(sql);
        table.setTableEmpty(not_empty);
        HelpA.setTrackingToolTip(table, query);
        //
        return table;
    }
    
    public HashMap<String, Object> getDefaultRowComponents() {
        HashMap<String, Object> componentMap = new HashMap<String, Object>();
        //
//        componentMap.put("Test", new JButton("Test"));
        //
        return componentMap;
    }
    
    private void addAdditionalComponent(RowDataInvert CURR_ROW, HashMap<String, Object> additionalComponentsMap) {
        Set set = additionalComponentsMap.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object component = additionalComponentsMap.get(key);
            CURR_ROW.addRowColumnData(new ColumnDataEntryInvert(component, "-1", "-", CURR_ROW.getFieldNickName()));
        }
    }
    
}
