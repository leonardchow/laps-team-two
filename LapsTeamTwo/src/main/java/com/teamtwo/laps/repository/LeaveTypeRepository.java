package com.teamtwo.laps.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.teamtwo.laps.model.LeaveType;


public interface LeaveTypeRepository extends  JpaRepository<LeaveType, String> {

	@Query("SELECT s FROM LeaveType s WHERE s.leaveType = :id")
	LeaveType findLeaveTypeById(@Param("id") Integer leaveType);
	
	
}
