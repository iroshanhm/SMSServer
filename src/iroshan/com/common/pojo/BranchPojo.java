package iroshan.com.common.pojo;

import java.sql.Timestamp;
import java.util.Date;

public class BranchPojo {

	private int idAuto;
	private int sysId;
	
	private String code;
	private String name;
	private String address;
	private String phoneNo;
	private String location;
	
	private int insertUserId;
	private Date insertDate;
	
	private int isUpdated;
	private int updateUserId;
	private Date updateDate;
	
	private int isRemove;
	private int removeUserId;
	private Date removeDate;
	
	
	
	public int getIdAuto() {
		return idAuto;
	}
	public void setIdAuto(int idAuto) {
		this.idAuto = idAuto;
	}
	public int getSysId() {
		return sysId;
	}
	public void setSysId(int sysId) {
		this.sysId = sysId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getInsertUserId() {
		return insertUserId;
	}
	public void setInsertUserId(int insertUserId) {
		this.insertUserId = insertUserId;
	}


	public int getIsUpdated() {
		return isUpdated;
	}
	public void setIsUpdated(int isUpdated) {
		this.isUpdated = isUpdated;
	}
	public int getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
	}


	public int getIsRemove() {
		return isRemove;
	}
	public void setIsRemove(int isRemove) {
		this.isRemove = isRemove;
	}
	public int getRemoveUserId() {
		return removeUserId;
	}
	public void setRemoveUserId(int removeUserId) {
		this.removeUserId = removeUserId;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getRemoveDate() {
		return removeDate;
	}
	public void setRemoveDate(Date removeDate) {
		this.removeDate = removeDate;
	}


	

	
	
	
	
	
}
