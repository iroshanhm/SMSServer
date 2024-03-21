/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.pojo;

import iroshan.com.common.utility.MyMessagesUtility;
import iroshan.com.smsserver.enums.MessageStatusEnum;
import iroshan.com.smsserver.enums.MyMessageTypeEnum;

/**
 *
 * @author Iroshan
 */
public class NewJurnal_pojo
{
    
    Integer mapKey;
    Boolean isSelect;
    String JurnalNo;
    String memCode;
    String memName;
    String acOfficeNo;
    String memTeleNo;
    String message;
    String status = MessageStatusEnum.PENDING.toString ();

    public Integer getMapKey ()
    {
        return mapKey;
    }

    public void setMapKey (Integer mapKey)
    {
        this.mapKey = mapKey;
    }

    public Boolean getIsSelect ()
    {
        return isSelect;
    }

    public void setIsSelect (Boolean isSelect)
    {
        this.isSelect = isSelect;
    }

    public String getJurnalNo ()
    {
        return JurnalNo;
    }

    public void setJurnalNo (String JurnalNo)
    {
        this.JurnalNo = JurnalNo;
    }

    public String getMemCode ()
    {
        return memCode;
    }

    public void setMemCode (String memCode)
    {
        this.memCode = memCode;
    }

    public String getMemName ()
    {
        return memName;
    }

    public void setMemName (String memName)
    {
        this.memName = memName;
    }

    public String getAcOfficeNo ()
    {
        return acOfficeNo;
    }

    public void setAcOfficeNo (String acOfficeNo)
    {
        this.acOfficeNo = acOfficeNo;
    }

    public String getMemTeleNo ()
    {
        return memTeleNo;
    }

    public void setMemTeleNo (String memTeleNo)
    {
        this.memTeleNo = memTeleNo;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    
    

}
