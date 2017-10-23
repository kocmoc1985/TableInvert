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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import sql.SqlBasicLocal;
import supplementary.HelpA;
import supplementary.Sql_B;

/**
 *
 * @author KOCMOC
 */
public class ExampleA implements TableInvertUser {

    private TableBuilderInvert TABLE_BUILDER_INVERT;
    private TableInvert TABLE_INVERT;
    private JComponent TABLE_INVERT_CONTAINER;
    private SqlBasicLocal sql;
    private JButtonSave SAVE_BTN;

    public ExampleA() {
        build();
    }

    private void build() {
        //
        ExampleForm ef = new ExampleForm();
        ef.setVisible(true);
        //
        connect();
        //
        TABLE_INVERT_CONTAINER = ef.jPanel1;
        //
        showTableInvert();
        //
        SAVE_BTN = new JButtonSave(ef.jButton1, TABLE_INVERT);
        //
        initializeSaveIndicators();
    }

    private void connect() {
        //
        sql = new Sql_B(false, false);
        Sql_B sql_b = (Sql_B) sql;
        //
        try {
            sql_b.connect_tds("10.87.0.2", "1433", "MILLS", "opc", "kocmoc", false, null, null);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ExampleA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator = new SaveIndicator(SAVE_BTN.getButton(), this, 0);
    }

    @Override
    public boolean getUnsaved(int nr) {
        return TABLE_INVERT.unsavedEntriesExist();
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        RowDataInvert code = new RowDataInvert(
                "main_table", // tableName
                "batch_id", // primaryOrForeignKey
                false, // keyIsString
                "order_id", // field_original_name
                "ORDER", // field_nick_name
                "", // unitOrObject
                true, // IsString
                true, // visible
                false // important
                );
        //
        RowDataInvert[] rows = {code};
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
            Logger.getLogger(ExampleA.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        TABLE_INVERT.setMargin(10, 0, 0, 0);
        //
        TABLE_INVERT.showTableInvert(TABLE_INVERT_CONTAINER);
    }

    public static void main(String[] args) {
        HelpA.nimbusLookAndFeel();
        ExampleA ea = new ExampleA();
    }
}
