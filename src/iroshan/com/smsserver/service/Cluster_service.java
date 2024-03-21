/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.com.smsserver.dao.Cluster_dao;
import iroshan.com.smsserver.entity.CustCategoryMain;
import java.util.List;

/**
 *
 * @author Iroshan
 */
public class Cluster_service {

    public List<CustCategoryMain> getClusterList() {
        return new Cluster_dao().getClusterListAll();
    }
    
      public List<String> getClusterAsString() {
        return new Cluster_dao().getClusterListAsStringList ();
    }
}
