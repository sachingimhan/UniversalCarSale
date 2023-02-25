/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.usc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sachin
 */
public class DbConnection {
    
    private static DbConnection dbConnection;
    private String URL = "jdbc:mysql://ikman.mysql.database.azure.com:3306/ucs?useSSL=true";
    private String USER = "sachin";
    private String PASSWORD = "Password@123";
    private Connection con;
    
    public DbConnection() {
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch(ClassNotFoundException | SQLException e){
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public static DbConnection getInstance(){
        if(dbConnection == null) dbConnection = new DbConnection();
        return dbConnection;
    }
    
    public Connection getConnection(){
        return con;
    }
}
