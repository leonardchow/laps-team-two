package com.teamtwo.laps.service;

import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;

import com.teamtwo.laps.model.Leave;

public interface LeaveService {

	ArrayList<Leave> findAllLeaveOfStaff(Integer staffId);

	ArrayList<Leave> findLeaveByType(Integer staffId, Integer leaveType);

	Leave findLeaveById(Integer leaveId);
	
	Leave changeLeave(Leave leave);

	ArrayList<Leave> findPendingLeaveByType(Integer managerId);
	
	//Yin
	ArrayList<Leave> findStaffLeaveHistory(Integer sid );
}