/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package supplementary;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class DefaultTableModelM extends DefaultTableModel {

    private String[] sortAsFloatColNames;
    private JTable table;

    public DefaultTableModelM(Object[][] os, Object[] os1, String[] sortAsFloatColNames, JTable table) {
        super(os, os1);
        this.sortAsFloatColNames = sortAsFloatColNames;
        this.table = table;
    }

//    @Override
//    public Class<?> getColumnClass(int i) {
//        if (i == 0) {
//            return Integer.class;
//        }
//        return super.getColumnClass(i);
//    }
    @Override
    public Class<?> getColumnClass(int i) {
//        if (table.getName().equals("table_4_recipe")) {
//            System.out.println("");
//        }
        //
        for (String colName : sortAsFloatColNames) {
            if (HelpA.getColByName(table, colName) == i) {
                return Integer.class;
            }
        }
        return super.getColumnClass(i);
    }
//    @Override
//    public String getColumnName(int column) {
//        return getColumnClass(column).getSimpleName();
//    }
}
