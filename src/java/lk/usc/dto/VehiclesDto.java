/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.usc.dto;

/**
 *
 * @author sachin
 */
public class VehiclesDto {
    private int status;
    private String message;
    private VehicleDto[] body;

    public VehiclesDto() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public VehicleDto[] getBody() {
        return body;
    }

    public void setBody(VehicleDto[] body) {
        this.body = body;
    }
}
