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
import MyObjectTableInvert.TableRowInvertListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import supplementary.HelpA;
import supplementary.JPanelImg;
import supplementary.SQL_Q;
import supplementary.SqlBasicLocal;
import supplementary.Sql_B;

/**
 * This one is focused on TableRowInvertListener
 *
 * @author KOCMOC
 */
public class ExampleC implements TableInvertUser, TableRowInvertListener {

    private TableBuilderInvert TABLE_BUILDER_INVERT;
    private TableInvert TABLE_INVERT;
    private JComponent TABLE_INVERT_CONTAINER;
    private SqlBasicLocal sql;
    private JButtonSave SAVE_BTN;
    private final static String TABLE_INVERT_NAME = "TABLE_ONE";

    public ExampleC() {
        build();
    }

    @Override
    public void mouseClicked(Object source, TableInvert ti) {
        //
        String tableName = ti.getTABLE_NAME();
        int currColumn = ti.getCurrentColumn(source);
        String currColName = ti.getCurrentColumnName(source);
        String value = ti.getValueCurrentColumn(source);
        //
        if (tableName.equals(TABLE_INVERT_NAME)) {
            System.out.println("colName: " + currColName + "  " + "colNr: " + currColumn + "  value: " + value);
        }
        //
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
    public RowDataInvert[] getConfigTableInvert() {
        //
        //Show 1 parameter in a "JTextFiel"
        RowDataInvert order = new RowDataInvert("main_table", "batch_id", false, "order_id", "ORDER", "KM/H", true, true, false);
        //
        RowDataInvert batch = new RowDataInvert("main_table", "batch_id", false, "batch_nr", "BATCH", "", true, true, false);
        //
        String q_4 = SQL_Q.basic_combobox_query("recipe_id", "main_table");
        RowDataInvert recipe = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_4, sql, "", "main_table", "batch_id", false, "recipe_id", "RECIPE", "", true, true, false);
        //
        String q_5 = SQL_Q.basic_combobox_query_double_param("recipe_id", "batch_id", "main_table");
        RowDataInvert line = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_5, sql, "", "main_table", "batch_id", false, "line_nr", "LINE", "", true, true, false);
        line.enableComboBoxMultipleValue();
        //
        String q_6 = SQL_Q.basic_combobox_query("duration", "main_table");
        RowDataInvert duration = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_6, sql, "", "main_table", "batch_id", false, "duration", "DURATION", "", true, true, false);
        duration.enableJComboAutoFill();
        //
        //Using Image example
        JPanelImg image = HelpA.getImageForTableInvert(images.IconUrls.PRE_ADD);
        //
        String q_7 = SQL_Q.basic_combobox_query("duration", "main_table");
        RowDataInvert datum = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_7, sql, "", "main_table", "batch_id", false, "datum", "DATUM",image, true, true, false);
        duration.enableJComboAutoFill();
        //
        RowDataInvert[] rows = {order,batch, recipe,line,duration,datum};
        //
        return rows;
    }

    @Override
    public void showTableInvert() {
        //
        RowDataInvert[] config = getConfigTableInvert();
        //
        boolean SHOW_UNITS = true;
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert(sql, config, SHOW_UNITS, TABLE_INVERT_NAME);
        //
        TABLE_INVERT = null;
        //
        try {
            String q = "select * from main_table where batch_id = 154";
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q);
        } catch (SQLException ex) {
            Logger.getLogger(ExampleC.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        TABLE_INVERT.addTableRowInvertListener(this);
        //
        TABLE_INVERT.setMargin(10, 0, 0, 0);
        //
        TABLE_INVERT.showTableInvert(TABLE_INVERT_CONTAINER);
        //
        HelpA.addMouseListenerToAllComponentsOfComponent(TABLE_INVERT);
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

    public static void main(String[] args) {
        HelpA.nimbusLookAndFeel();
        ExampleC ea = new ExampleC();
    }
}
