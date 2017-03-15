/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTable;

import java.util.ArrayList;

/**
 *
 * @author KOCMOC
 */
public class RowData extends ArrayList<Object> {

    private String DATABASE_ID;

    public void addRowColumnData(Object obj) {
        this.add(obj);
    }    

    public void setDatabaseId(String id) {
        this.DATABASE_ID = id;
    }

    public String getDatabaseId() {
        return this.DATABASE_ID;
    }

    public int getNrColumns() {
        return this.size();
    }
}
