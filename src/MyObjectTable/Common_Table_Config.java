/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTable;

import java.util.HashMap;

/**
 *
 * @author KOCMOC
 */
public class Common_Table_Config {

    public static HashMap<String, Object> getDefaultRowComponents() {
        HashMap<String, Object> componentMap = new HashMap<String, Object>();
        //
        componentMap.put("*Select", new SelectRowButton("Select"));
        //
        return componentMap;
    }
    
    public static String[] getHeaders_test() {
        return new String[]{"id", "username", "pass", "dateCreated", "dateChanged"};
    }

    public static String[] getHeadersProductionPlan() {
        return new String[]{"*Select","PLANDATE", "PLANID", "ORDERNO", "RCODE", "MTYPE", "STATUS",
            "BATCHQTY","PRODQTY","FIRSTBATCH","PRIORITY","CANCELQTY",
            "BOOKEDQTY","PRODDATE","ORIGIN","MODIFIED","REMARK","LASTUPDATE","UPDATEDBY"};
    }
    
    public static String[] getHeaders_RecipeCsv() {
        return new String[]{"Id", "Field1", "Field2", "Field3", "Field4", "Field5"};
    }
}
