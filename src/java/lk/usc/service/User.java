/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.usc.service;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.bind.annotation.XmlSeeAlso;
import lk.usc.dto.CommonResponse;
import lk.usc.model.HistoryModel;
import lk.usc.model.UserModel;
import lk.usc.persistence.HistoryPresistense;
import lk.usc.persistence.UserPersistence;

/**
 *
 * @author sachin
 */
@XmlSeeAlso({CommonResponse.class,UserModel.class,HistoryModel[].class})
@WebService(serviceName = "User")
public class User {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "registration")
    public CommonResponse registration(@WebParam(name = "fname") String fname, @WebParam(name = "lname") String lname, @WebParam(name = "email") String email, @WebParam(name = "password") String password, @WebParam(name = "passConfirmation") String passConfirmation, @WebParam(name = "contact") String contact) {
        CommonResponse resp = new CommonResponse();
        if (!password.equals(passConfirmation)) {
            resp.setStatus(400);
            resp.setMessage("Password Not Matched.");
            return resp;
        }else if(fname == null || fname.isEmpty()){
            resp.setStatus(400);
            resp.setMessage("First Name can not be empty");
            return resp;
        }else if(lname == null || lname.isEmpty()){
            resp.setStatus(400);
            resp.setMessage("Last Name can not be empty");
            return resp;
        }else if(email == null || email.isEmpty()){
            resp.setStatus(400);
            resp.setMessage("Email can not be empty");
            return resp;
        }else if(password == null || password.isEmpty()){
            resp.setStatus(400);
            resp.setMessage("Password can not be empty");
            return resp;
        }
        try {
            UserModel model = new UserModel();
            model.setfName(fname);
            model.setlName(lname);
            model.setEmail(email);
            model.setPassword(password);
            model.setContact(contact);
            boolean save = UserPersistence.save(model);
            if(save){
                resp.setStatus(200);
                resp.setMessage("Success");
            }else{
                resp.setStatus(400);
                resp.setMessage("Fail");
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resp;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "login")
    public CommonResponse login(@WebParam(name = "email") String email, @WebParam(name = "password") String password) {
        CommonResponse resp = new CommonResponse();
        if(email == null || email.isEmpty()){
            resp.setStatus(400);
            resp.setMessage("Invalid email address.");
            return resp;
        }else if(password == null || password.isEmpty()){
            resp.setStatus(400);
            resp.setMessage("Invalid password");
            return resp;
        }
        try{
            UserModel user = UserPersistence.verifyUser(email.trim(), password);
            if(user != null){
                resp.setStatus(200);
                resp.setMessage("Login Success");
                resp.setData(user);
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, "User Data: {0}",user);
            }else{
                resp.setStatus(400);
                resp.setMessage("Login Fail.");
            }
        }catch(SQLException e){
             Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
        }
        return resp;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "userHistory")
    public CommonResponse userHistory(@WebParam(name = "api") String api) {
        CommonResponse resp = new CommonResponse();
        if(api != null || !api.isEmpty()){
            try {
                String userId = UserPersistence.getUserByApiKey(api);
                HistoryModel[] historyData = HistoryPresistense.getHistoryByUserId(userId);
                resp.setStatus(200);
                resp.setMessage("Success");
                resp.setData(historyData);
            } catch (SQLException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            resp.setStatus(400);
            resp.setMessage("API_KEY Not Found");
        }
        return resp;
    }

}
