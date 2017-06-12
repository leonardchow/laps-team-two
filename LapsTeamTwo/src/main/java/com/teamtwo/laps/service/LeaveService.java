package com.teamtwo.laps.service;

import java.util.ArrayList;



import com.teamtwo.laps.model.Leave;



public interface LeaveService {

	ArrayList<Leave> findAllLeaveOfStaff(Integer staffId);

	ArrayList<Leave> findLeaveByType(Integer staffId, Integer leaveType);

	Leave findLeaveById(Integer leaveId);
<<<<<<< HEAD

	Leave createLeave(Leave leave);

=======
	
	Leave changeLeave(Leave leave);
>>>>>>> branch 'master' of https://github.com/leonardchow/laps-team-two.git
}