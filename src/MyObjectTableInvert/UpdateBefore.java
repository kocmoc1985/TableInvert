/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import java.util.logging.Level;
import java.util.logging.Logger;
import sql.SqlBasicLocal;

/**
 *
 * @author KOCMOC
 * @deprecated 
 */
public class UpdateBefore {

    public static final String SQL_CMD_0 = "sql_cmd_0";
    public static final String SQL_CMD_1 = "sql_cmd_0";
    
    public static boolean updateBefore(UnsavedEntryInvert unsavedEntryInvert, SqlBasicLocal sql, String cmd) {
        if (cmd.equals(SQL_CMD_0)) {
            return sql_cmd_0(unsavedEntryInvert, sql);
        }else if(cmd.equals(SQL_CMD_1)){
            
        }
        return false;
    }

    private static boolean sql_cmd_0(UnsavedEntryInvert unsavedEntryInvert, SqlBasicLocal sql) {
        String q = "update table tableName set..";
        boolean ok = true;
        try {
            sql.execute(q);
        } catch (Exception ex) {
            Logger.getLogger(UpdateBefore.class.getName()).log(Level.SEVERE, null, ex);
            ok = false;
        }

        return ok;
    }
}
