/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.common.derby.service;

import iroshan.com.common.derby.dao.Derby_dao;
import iroshan.com.common.derby.pojo.Setting_pojo;

/**
 *
 * @author Iroshan
 */
public class Setting_service {

    public void insertSetting () {

        new Derby_dao ().insertDBSetting ();

    }

    public void loadSetting () {

        new Derby_dao ().loadSetting ();
    }

}
