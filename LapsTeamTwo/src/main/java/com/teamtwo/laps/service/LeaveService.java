package com.teamtwo.laps.service;

import java.util.ArrayList;



import com.teamtwo.laps.model.Leave;



public interface LeaveService {

	ArrayList<Leave> findAllLeaveOfStaff(Integer staffId);

	ArrayList<Leave> findLeaveByType(Integer staffId, Integer leaveType);

	Leave findLeaveById(Integer leaveId);

	Leave createLeave(Leave leave);

}