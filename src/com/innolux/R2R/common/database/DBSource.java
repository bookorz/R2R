package com.innolux.R2R.common.database;
import java.sql.Connection;
import java.sql.SQLException;

public interface DBSource {
    public Connection getConnection() throws SQLException;
    public void closeConnection(Connection conn) throws SQLException;
}
