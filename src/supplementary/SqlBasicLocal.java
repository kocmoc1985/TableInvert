/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package supplementary;

import MyObjectTable.ShowMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author KOCMOC
 */
public interface SqlBasicLocal {

    public ResultSet execute(String string) throws SQLException;

    public ResultSet execute(String sql, ShowMessage sm) throws SQLException;

    public ResultSet execute_2(String sql) throws SQLException;

    public int update(String string) throws SQLException;

    public void prepareStatement(String q) throws SQLException;

    public int executeUpdatePreparedStatement() throws SQLException;

    public PreparedStatement getPreparedStatement();

    public PreparedStatement prepareStatementB(String q) throws SQLException;

    public Connection getConnection();
    
}
