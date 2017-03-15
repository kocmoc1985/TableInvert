/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package supplementary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author KOCMOC
 */
public interface SqlBasicLocal {

    public ResultSet execute(String string) throws SQLException;

    public ResultSet execute2(String string) throws SQLException;
    
    public int update(String string) throws SQLException;

    public Connection getConnection();
    
}
