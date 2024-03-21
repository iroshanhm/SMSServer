/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Iroshan
 */
@Entity
public class Customer_entity implements Serializable {

    public Customer_entity ()
    {
    }

    
    
    @Id
    private String cmCode;
    @Id
    private String cmTele;
    @Id
    private String password;
    @Id
    private String cmCode2;

    public String getCmCode () {
        return cmCode;
    }

    public void setCmCode (String cmCode) {
        this.cmCode = cmCode;
    }

    public String getCmTele () {
        return cmTele;
    }

    public void setCmTele (String cmTele) {
        this.cmTele = cmTele;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getCmCode2 () {
        return cmCode2;
    }

    public void setCmCode2 (String cmCode2) {
        this.cmCode2 = cmCode2;
    }



}
