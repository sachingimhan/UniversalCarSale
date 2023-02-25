/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.usc.model;

/**
 *
 * @author sachin
 */
public class UserModel {
    private int custId;
    private String fName;
    private String lName;
    private String email;
    private String password;
    private String contact;
    private String apiKey;

    public UserModel() {
    }

    public UserModel(int custId, String fName, String lName, String email, String password, String contact) {
        this.custId = custId;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String toString() {
        return "UserModel{" + "custId=" + custId + ", fName=" + fName + ", lName=" + lName + ", email=" + email + ", password=" + password + ", contact=" + contact + ", apiKey=" + apiKey + '}';
    }
    
}
