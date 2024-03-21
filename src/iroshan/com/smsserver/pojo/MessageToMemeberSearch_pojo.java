/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iroshan.com.smsserver.pojo;

import iroshan.com.smsserver.enums.MessageStatusEnum;
import java.util.Date;

/**
 *
 * @author Iroshan
 */
public class MessageToMemeberSearch_pojo
{
//    "SELECT", "#", "CODE", "NAME", "SUB A/C OFFICE NO", "TELE.", "BIRTHDAY"
    private Integer mapKey;
    private Boolean isSelect;
    private Integer sequenceId;
    private String code;
    private String name;
    private String subACOfficeNo;
    private String tele;
    private Date birthday;
    private String status = MessageStatusEnum.PENDING.toString ().trim ();

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


    
    public Integer getSequenceId ()
    {
        return sequenceId;
    }

    public void setSequenceId (Integer sequenceId)
    {
        this.sequenceId = sequenceId;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getSubACOfficeNo ()
    {
        return subACOfficeNo;
    }

    public void setSubACOfficeNo (String subACOfficeNo)
    {
        this.subACOfficeNo = subACOfficeNo;
    }

    public String getTele ()
    {
        return tele;
    }

    public void setTele (String tele)
    {
        this.tele = tele;
    }

    public Date getBirthday ()
    {
        return birthday;
    }

    public void setBirthday (Date birthday)
    {
        this.birthday = birthday;
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
