/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Examples;

import MyObjectTableInvert.JButtonSave;
import MyObjectTableInvert.TableInvertUser;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.SaveIndicator;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import supplementary.HelpA;
import supplementary.SQL_Q;
import supplementary.SqlBasicLocal;
import supplementary.Sql_B;

/**
 *
 * @author KOCMOC
 */
public class ExampleB implements TableInvertUser {

    private TableBuilderInvert TABLE_BUILDER_INVERT;
    private TableInvert TABLE_INVERT;
    private JComponent TABLE_INVERT_CONTAINER;
    private SqlBasicLocal sql;
    private JButtonSave SAVE_BTN;

    public ExampleB() {
        build();
    }

    private void build() {
        HelpA.TRACKING_TOOL_TIP = true;
        //
        final ExampleForm ef = new ExampleForm();
        ef.setVisible(true);
        //
        connect();
        //
        TABLE_INVERT_CONTAINER = ef.jPanel1;
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                showTableInvert();
                //
                SAVE_BTN = new JButtonSave(ef.jButton1, TABLE_INVERT);
                //
                initializeSaveIndicators();
            }
        });
        //

    }

    private void connect() {
        //
        sql = new Sql_B(false, false);
        Sql_B sql_b = (Sql_B) sql;
        //
        try {
            sql_b.connect_mdb("", "", "example.mdb");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ExampleC.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
//        try {
//            sql_b.connect_tds("10.87.0.2", "1433", "MILLS", "opc", "kocmoc", false, null, null);
//        } catch (SQLException | ClassNotFoundException ex) {
//            Logger.getLogger(ExampleC.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator = new SaveIndicator(SAVE_BTN.getButton(), this, 0);
    }

    @Override
    public boolean getUnsaved(int nr) {
        if (TABLE_INVERT == null) {
            return false;
        }
        return TABLE_INVERT.unsavedEntriesExist();
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        //Show 1 parameter in a "JTextFiel"
        RowDataInvert order = new RowDataInvert("main_table", "batch_id", false, "order_id", "ORDER", "", true, true, false);
        //
        String q_4 = SQL_Q.basic_combobox_query("recipe_id", "main_table");
        RowDataInvert recipe = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_4, sql, "", "main_table", "batch_id", false, "recipe_id", "RECIPE", "", true, true, false);
        //
        RowDataInvert duration = new RowDataInvert("main_table", "batch_id", false, "duration", "DURATION", "", true, true, false);
        duration.enableToolTipTextJTextField();
        duration.enableFakeValue();
        //
        String q_6 = SQL_Q.basic_combobox_query("recipe_id", "main_table");
        RowDataInvert recipe2 = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_6, sql, "", "main_table", "batch_id", false, "recipe_id", "RECIPE", "", true, true, false);
        recipe2.enableFakeValue();
        //
        String q_5 = SQL_Q.basic_combobox_query_double_param("recipe_id", "batch_id", "main_table");
        RowDataInvert line = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_5, sql, "", "main_table", "batch_id", false, "line_nr", "LINE", "", true, true, false);
        line.enableComboBoxMultipleValue();
        line.setUneditable();
        //
        RowDataInvert updated_on = new RowDataInvert("main_table", "batch_id", false, "UpdatedOn", "UPDATED ON", "", true, true, false);
        RowDataInvert updated_by = new RowDataInvert("main_table", "batch_id", false, "UpdatedBy", "UPDATED BY", "", true, true, false);
        //
        RowDataInvert[] rows = {order,recipe, duration,recipe2, line,updated_on,updated_by};
        //
        return rows;
    }

    @Override
    public void showTableInvert() {
        //
        RowDataInvert[] config = getConfigTableInvert();
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert(sql, config, false, "table_name");
        //
        TABLE_INVERT = null;
        //
        try {
            String q = "select * from main_table where batch_id = 154";
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q);
        } catch (SQLException ex) {
            Logger.getLogger(ExampleB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        TABLE_INVERT.setMargin(10, 0, 0, 0);
        //
        TABLE_INVERT.showTableInvert(TABLE_INVERT_CONTAINER);
    }

    public static void main(String[] args) {
        HelpA.nimbusLookAndFeel();
        ExampleB ea = new ExampleB();
    }
}
