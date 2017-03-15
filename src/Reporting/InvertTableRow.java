/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reporting;

import java.util.ArrayList;

/**
 *
 * @author KOCMOC
 */
public class InvertTableRow {
    private final String columnName;
    private final String unit;
    private ArrayList<String> values = new ArrayList<String>();

    public InvertTableRow(String columnName, String unit) {
        this.columnName = columnName;
        this.unit = unit;
    }
    
    public void addValue(String value){
        values.add(value);
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getUnit() {
        return unit;
    }
    
    public String getValue(int i){
        return values.get(i);
    }
    
}
