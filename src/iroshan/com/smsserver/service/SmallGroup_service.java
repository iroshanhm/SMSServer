/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.service;

import iroshan.com.smsserver.dao.Cluster_dao;
import iroshan.com.smsserver.dao.SmallGroup_dao;
import iroshan.com.smsserver.entity.CustCategoryMain;
import iroshan.com.smsserver.entity.CustCategory_ent;
import java.util.List;

/**
 *
 * @author Iroshan
 */
public class SmallGroup_service {


    public List<CustCategory_ent> getSmallGroupsList_byClusterCode(String clusterCode) {
         return new SmallGroup_dao().getSmallGroupsList_byClusterCode(clusterCode);
    }
    
}
