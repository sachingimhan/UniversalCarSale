/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.usc.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import lk.usc.db.DbConnection;
import lk.usc.model.UserModel;

/**
 *
 * @author sachin
 */
public class UserPersistence {

    public static boolean save(UserModel user) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO USER (F_NAME, L_NAME, EMAIL, PASS, CONTACT, API_KEY) VALUES (?, ?, ?, SHA1(?), ?, ?)");
        pstm.setString(1, user.getfName());
        pstm.setString(2, user.getlName());
        pstm.setString(3, user.getEmail());
        pstm.setString(4, user.getPassword());
        pstm.setString(5, user.getContact());
        pstm.setString(6, UUID.randomUUID().toString().replace("-", ""));
        return pstm.executeLargeUpdate() > 0;
    }

    public static UserModel verifyUser(String email, String password) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        UserModel user = new UserModel();
        PreparedStatement pstm = con.prepareStatement("SELECT CUST_ID,F_NAME,L_NAME,CONTACT,API_KEY FROM USER WHERE EMAIL= ? AND PASS = SHA1(?)");
        pstm.setString(1, email);
        pstm.setString(2, password);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            user.setCustId(rs.getInt("CUST_ID"));
            user.setfName(rs.getString("F_NAME"));
            user.setlName(rs.getString("L_NAME"));
            user.setContact(rs.getString("CONTACT"));
            user.setApiKey(rs.getString("API_KEY"));
        } else {
            user = null;
        }

        return user;
    }

    public static String getUserByApiKey(String api) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT CUST_ID FROM `USER` WHERE API_KEY = ?");
        pstm.setString(1, api);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return rs.getString("CUST_ID");
        }
        return null;
    }

}
