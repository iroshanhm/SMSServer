/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.common.pojo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Iroshan
 */
@Entity
public class CustomerCategoryMainCategory_Deleted implements Serializable {


    private String cluster_code;
    private String cluster_name;
    private String mfo;
    private String small_group_name;
    private String small_group_code;
    @Id
    private String CM_CODE;
    private String CM_DESC;
    private String CM_ID;
    private String CM_ADD1;
    private String CM_ADD2;
    private String CM_ADD3;
    private String CM_ADD4;
    private String CM_TELE;

    public String getCluster_code() {
        return cluster_code;
    }

    public void setCluster_code(String cluster_code) {
        this.cluster_code = cluster_code;
    }

    public String getCluster_name() {
        return cluster_name;
    }

    public void setCluster_name(String cluster_name) {
        this.cluster_name = cluster_name;
    }

    public String getMfo() {
        return mfo;
    }

    public void setMfo(String mfo) {
        this.mfo = mfo;
    }

    public String getSmall_group_name() {
        return small_group_name;
    }

    public void setSmall_group_name(String small_group_name) {
        this.small_group_name = small_group_name;
    }

    public String getSmall_group_code() {
        return small_group_code;
    }

    public void setSmall_group_code(String small_group_code) {
        this.small_group_code = small_group_code;
    }

    public String getCM_CODE() {
        return CM_CODE;
    }

    public void setCM_CODE(String CM_CODE) {
        this.CM_CODE = CM_CODE;
    }

    public String getCM_DESC() {
        return CM_DESC;
    }

    public void setCM_DESC(String CM_DESC) {
        this.CM_DESC = CM_DESC;
    }

    public String getCM_ID() {
        return CM_ID;
    }

    public void setCM_ID(String CM_ID) {
        this.CM_ID = CM_ID;
    }

    public String getCM_ADD1() {
        return CM_ADD1;
    }

    public void setCM_ADD1(String CM_ADD1) {
        this.CM_ADD1 = CM_ADD1;
    }

    public String getCM_ADD2() {
        return CM_ADD2;
    }

    public void setCM_ADD2(String CM_ADD2) {
        this.CM_ADD2 = CM_ADD2;
    }

    public String getCM_ADD3() {
        return CM_ADD3;
    }

    public void setCM_ADD3(String CM_ADD3) {
        this.CM_ADD3 = CM_ADD3;
    }

    public String getCM_ADD4() {
        return CM_ADD4;
    }

    public void setCM_ADD4(String CM_ADD4) {
        this.CM_ADD4 = CM_ADD4;
    }

    public String getCM_TELE() {
        return CM_TELE;
    }

    public void setCM_TELE(String CM_TELE) {
        this.CM_TELE = CM_TELE;
    }

    
    
}