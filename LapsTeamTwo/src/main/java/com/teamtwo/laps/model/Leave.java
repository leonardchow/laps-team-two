package com.teamtwo.laps.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.teamtwo.laps.javabeans.LeaveStatus;

@Entity
@Table(name = "leave_history")
public class Leave {
	
	@Id
	@Column(name = "leaveid")
	private Integer leaveId;
	@Column(name = "staffid")
	private Integer staffId;
	@Column(name = "leavetype")
	private Integer leaveType;
	private String reason;
	@Column(name = "startdate")
	private Date startDate;
	@Column(name = "enddate")
	private Date endDate;
	private String dissemination;
	@Column(name = "disseminationid")
	private Integer disseminationId;
	
	@Enumerated(EnumType.STRING)
	private LeaveStatus status;
	
	private String comment;
	@Column(name = "wasupdated")
	private Integer wasUpdated;
	
	@ManyToOne
	@JoinColumn(name = "staffid", insertable = false, updatable = false)
	private StaffMember staffMember;
	
	public Leave() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Integer getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(Integer leaveId) {
		this.leaveId = leaveId;
	}
	public Integer getStaffId() {
		return staffMember.getStaffId();
	}
	public void setStaffId(Integer staffId) {
		this.staffMember.setStaffId(staffId);
	}
	public Integer getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(Integer leaveType) {
		this.leaveType = leaveType;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getDissemination() {
		return dissemination;
	}
	public void setDissemination(String dissemination) {
		this.dissemination = dissemination;
	}
	public Integer getDisseminationId() {
		return disseminationId;
	}
	public void setDisseminationId(Integer disseminationId) {
		this.disseminationId = disseminationId;
	}
	public LeaveStatus getStatus() {
		return status;
	}
	public void setStatus(LeaveStatus status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getWasUpdated() {
		return wasUpdated;
	}
	public void setWasUpdated(Integer wasUpdated) {
		this.wasUpdated = wasUpdated;
	}
	
	public StaffMember getStaffMember() {
		return staffMember;
	}

	public void setStaffMember(StaffMember staffMember) {
		this.staffMember = staffMember;
	}

	@Override
	public String toString() {
		return "Leave [leaveId=" + leaveId + ", staffId=" + staffMember.getStaffId() + ", leaveType=" + leaveType + ", reason=" + reason
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", dissemination=" + dissemination
				+ ", disseminationId=" + disseminationId + ", status=" + status + ", comment=" + comment
				+ ", wasUpdated=" + wasUpdated + "]";
	}
	
	
}
