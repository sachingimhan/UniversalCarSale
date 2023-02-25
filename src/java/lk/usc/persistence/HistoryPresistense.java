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
import java.util.ArrayList;
import java.util.List;
import lk.usc.db.DbConnection;
import lk.usc.model.HistoryModel;

/**
 *
 * @author sachin
 */
public class HistoryPresistense {
    
    public static boolean save(String uid, String data) throws SQLException{
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO HISTORY (SEARCH,CUST_ID) VALUES(?,?)");
        pstm.setString(1, data);
        pstm.setString(2, uid);
        return pstm.executeUpdate() > 0;
    }
    
    public static HistoryModel[] getHistoryByUserId(String uid) throws SQLException{
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT ID,SEARCH,CUST_ID,CREATED_AT,UPDATED_AT FROM HISTORY WHERE CUST_ID = ?");
        pstm.setString(1, uid);
        ResultSet rs = pstm.executeQuery();
        List<HistoryModel> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new HistoryModel(rs.getInt("ID"), rs.getString("SEARCH"), rs.getInt("CUST_ID"), rs.getString("CREATED_AT"), rs.getString("UPDATED_AT")));
        }
        
        HistoryModel[] history = new HistoryModel[list.size()];
        return list.toArray(history);
    }
    
}
