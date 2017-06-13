package com.teamtwo.laps.model;





import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table(name = "leave_type")
public class LeaveType {
	@Id
	@Column(name = "leavetype")
	private int leaveType;
	@Column(name = "leavename")
	private String leaveName;
	
	public LeaveType()
	{
		
	}

	public LeaveType(int leaveType, String leaveName)
	{
		this.leaveType=leaveType;
		this.leaveName= leaveName;
	}
	
	public int getLeaveType()
	{
		return leaveType;
	}
	public void setLeaveType(int leaveType)
	{
		this.leaveType=leaveType;
	}
	public String getLeaveName()
	{
		return leaveName;
	}
	public void setLeaveName(String leaveName)
	{
		this.leaveName=leaveName;
	}
}
