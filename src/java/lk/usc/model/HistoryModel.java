/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.usc.model;

import java.sql.Date;

/**
 *
 * @author sachin
 */
public class HistoryModel {
    private int id;
    private String search;
    private int custId;
    private String createdAt;
    private String updateAt;

    public HistoryModel() {
    }

    public HistoryModel(int id, String search, int custId, String createdAt, String updateAt) {
        this.id = id;
        this.search = search;
        this.custId = custId;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "HistoryModel{" + "id=" + id + ", search=" + search + ", custId=" + custId + ", createdAt=" + createdAt + ", updateAt=" + updateAt + '}';
    }
    
}
