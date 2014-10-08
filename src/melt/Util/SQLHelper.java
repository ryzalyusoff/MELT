/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.Util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aote Zhou
 */
public class SQLHelper {

    public static String url;
    public static String user;
    public static String password;
    public static String driver;
    public Connection conn;
    public Statement stat;
    public ResultSet rs ;


    /**
     *read properties from jdbc.properties
     */
        public SQLHelper() {
        try {

            InputStream in = this.getClass().getResourceAsStream("/melt/Util/jdbc.properties");
            Properties pp = new Properties();
            pp.load(in);
            url = pp.getProperty("jdbc.url");
            user = pp.getProperty("jdbc.username");
            password = pp.getProperty("jdbc.password");
            driver = pp.getProperty("jdbc.driver");

        } catch (IOException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Connect to the database
     */
    public void sqlConnect() {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, user, password);
            //if (!conn.isClosed()) {
                //System.out.println("connect to Database successfully!");
            //}

            stat = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * do update operations
     * @param sql sqlString
     */
    public void runUpdate(String sql) {
        try {
            stat.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * do update and return autogenerated keys
     * @param sql
     * @return
     */
    public ResultSet runUpdate2(String sql) {
        ResultSet rs=null;
        try {
            stat.executeUpdate(sql,stat.RETURN_GENERATED_KEYS);
            rs=stat.getGeneratedKeys();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    

    /**
     *  do query operations
     * @param sql sqlString
     * @return Resultset
     */
    public ResultSet runQuery(String sql) {
        try {
            rs = stat.executeQuery(sql);

        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;

    }

    /**
     * close resultset,statement and connection
     */
    public void sqlClose() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        try {
            SQLHelper sQLHelper = new SQLHelper();
            sQLHelper.sqlConnect();
            //sQLHelper.runUpdate("insert into teacher values('10060120','haha','zheng','jjhsjl')");
            ResultSet rs = sQLHelper.runQuery("select * from teacher");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            sQLHelper.sqlClose();
        } catch (SQLException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
