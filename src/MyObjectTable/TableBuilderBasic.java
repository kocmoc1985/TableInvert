/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTable;

import supplementary.GP;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import supplementary.HelpA;
import supplementary.Sql_B;

/**
 *
 * @author mcab
 */
public class TableBuilderBasic {

    public Sql_B sql;
    private Properties PROPS;
    private String PROPERTIES_PATH;
    //==========================
    private final String MS_SQL = "mssql";
    private final String MY_SQL = "mysql";
    private final String ODBC = "odbc";
    //==========================
    private final ShowMessage SM;
    //==========================
    private Table table;
    //==========================
    private String[] HEADERS;
    private int ID_INDEX;

    /**
     *
     * @param sm
     * @param container - Container element of the table
     * @param headers_config - an array of headers. By this headers it's defined
     * which columns are taken
     * @param id_index - nr of column which is "key/id" for the table
     */
    public TableBuilderBasic(ShowMessage sm, String[] headers_config, int id_index, Sql_B sql) {
        this.SM = sm;
        this.HEADERS = headers_config;
        this.ID_INDEX = id_index;
        if (sql != null) {
            this.sql = sql;
        }
        init();
    }

    /**
     * Used by "Invert"
     *
     * @param sm
     */
    public TableBuilderBasic(ShowMessage sm, Sql_B sql) {
        this.SM = sm;
        if (sql != null) {
            this.sql = sql;
        }
        init();
    }

    private void init() {
        setLookAndFeelNimbus();
        defineHeadVariables();
        sqlConnect();
    }

    private void defineHeadVariables() {
        this.PROPERTIES_PATH = "mccompound.properties";
        this.PROPS = HelpA.properties_load_properties(PROPERTIES_PATH, false);
        GP.MSSQL_CREATE_STATEMENT_SIMPLE = "false";
        GP.SQL_LIBRARY_JTDS = true;
    }

    public void sqlConnect() {
        //
//        if (sql.isConnected()) {
//            return;
//        }
        //==========
        GP.MSSQL_CREATE_STATEMENT_SIMPLE = PROPS.getProperty("mssql_create_statement_simple", "false");
        GP.MSSQL_LOGIN_TIME_OUT = Integer.parseInt(PROPS.getProperty("login_time_out", "60"));
        GP.SQL_LIBRARY_JTDS = Boolean.parseBoolean(PROPS.getProperty("use_jtds_library", "true"));
        GP.JTDS_USE_NAMED_PIPES = Boolean.parseBoolean(PROPS.getProperty("use_named_pipes", "false"));
        GP.JTDS_DOMAIN_WORKGROUP = PROPS.getProperty("domain_or_workgroup", "workgroup");
        GP.JTDS_INSTANCE_PARAMETER = PROPS.getProperty("jtds_instance", "");
        //==========
        showMessage("mssql_create_statement_simple: " + GP.MSSQL_CREATE_STATEMENT_SIMPLE);
        showMessage("login_time_out: " + GP.MSSQL_LOGIN_TIME_OUT);
        showMessage("use_jtds_library: " + GP.SQL_LIBRARY_JTDS);
        //==========
        String odbc = PROPS.getProperty("odbc", "");
        String odbc_user = PROPS.getProperty("odbc_user", "");
        String odbc_name = PROPS.getProperty("odbc_name", "");
        //==========================================
        String dbtype = PROPS.getProperty("db_type", "");
        String host = PROPS.getProperty("host", "");
        String port = PROPS.getProperty("port", "");
        String db_name = PROPS.getProperty("db_name", "");
        String user = PROPS.getProperty("user", "");
        String pass = PROPS.getProperty("pass", "");

        showMessage("sql_type: " + dbtype);

        showMessage("Connecting to: " + host);
        //
        //
        sql = new Sql_B(Boolean.parseBoolean(GP.MSSQL_CREATE_STATEMENT_SIMPLE), GP.MSSQL_LOGIN_TIME_OUT, true);
        //
        //
        try {
            if (dbtype.equals(MS_SQL)) {
                //
                if (GP.SQL_LIBRARY_JTDS) {
                    sql.connect_tds(host, port, db_name, user, pass,
                            GP.JTDS_USE_NAMED_PIPES, GP.JTDS_DOMAIN_WORKGROUP,
                            GP.JTDS_INSTANCE_PARAMETER);
                } else {
                    sql.connect_jdbc(host, port, db_name, user, pass);
                }
                //
            } else if (dbtype.equals(MY_SQL)) {
                sql.connectMySql(host, port, db_name, user, pass);
            } else if (dbtype.equals(ODBC)) {
                sql.connect_odbc(odbc_user, odbc_name, odbc);
            }
            //
            showMessage("Connection to " + host + " / " + db_name + " established");
            //
        } catch (Exception ex) {
            showMessage("Connection to " + host + " / " + db_name + " failed: " + ex);
            Logger.getLogger(TableBuilderBasic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showMessage(String msg) {
        SM.showMessage(msg);
    }

    public Table buildTable(String query) throws SQLException {
        ResultSet rs = sql.execute(query);

        TableData tableData = new TableData();

        String[] headers = HEADERS;

        while (rs.next()) {
            RowData ro = new RowData();
            //
            for (int i = 0; i < headers.length; i++) { // i = 2 -> to not take the id
                //
                Object row_data;
                //
                HashMap<String, Object> additionalComponentsMap = getDefaultRowComponents();
                //
                if (additionalComponentsMap.containsKey(headers[i])) {
                    row_data = additionalComponentsMap.get(headers[i]);
                } else {
                    row_data = rs.getString(headers[i]);
                }
                //
                //
                if (row_data == null) {
                    row_data = "NULL";
                }
                //
                //
                //
                String id = getDbId(rs, ID_INDEX);
                //
                if (id == null || id.isEmpty()) {
                    break;
                }
                //
                ro.setDatabaseId(id);// parameter i should be changed to real db id if any  
                //
                ro.addRowColumnData(row_data);
            }

            //
            tableData.addRowData(ro);
            //
        }
        //
        table = new Table(tableData, headers, TableRow.GRID_LAYOUT, 45, null);
        table.setSql(sql);
        //
        return table;
    }

    public HashMap<String, Object> getDefaultRowComponents() {
        HashMap<String, Object> componentMap = new HashMap<String, Object>();
        //
        componentMap.put("*Select", new SelectRowButton("Select"));
        //
        return componentMap;
    }

    public Table getTable() {
        return table;
    }

    public String getDbId(Object obj, int index) {
        ResultSet rs = (ResultSet) obj;
        try {
            String id = rs.getString(index);
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(TableBuilderBasic.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public String[] getHeaders(ResultSet rs) throws SQLException {
        ResultSetMetaData meta;
        String[] headers;
        meta = rs.getMetaData();
        headers = new String[meta.getColumnCount()];
        for (int i = 0; i < headers.length; i++) {
            headers[i] = meta.getColumnLabel(i + 1);
        }
        return headers;
    }

    public Sql_B getSql() {
        return this.sql;
    }

    private void setLookAndFeelNimbus() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TableBuilderBasic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TableBuilderBasic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TableBuilderBasic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TableBuilderBasic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
