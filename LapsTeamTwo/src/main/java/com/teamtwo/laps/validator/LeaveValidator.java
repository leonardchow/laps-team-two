package com.teamtwo.laps.validator;

import java.util.Calendar;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.teamtwo.laps.model.Leave;

@Component
public class LeaveValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return Leave.class.isAssignableFrom(arg0);

	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		Leave leave = (Leave) arg0;	
		if ((leave.getStartDate()!=null && leave.getEndDate()!=null)&&(leave.getStartDate().compareTo(leave.getEndDate()) > 0)) {
			arg1.reject("endDate", "End date must be after start date.");
			arg1.rejectValue("endDate", "error.dates", "End date must be after start date.");
	
		}
		Calendar start = Calendar.getInstance();
		start.setTime(leave.getStartDate());
		Calendar end = Calendar.getInstance();
		end.setTime(leave.getEndDate());
		
		int startYear = start.get(Calendar.YEAR);
		int endYear = end.get(Calendar.YEAR);
		
		if (startYear != endYear) {
			arg1.reject("endDate", "Must be within the same year");
			arg1.rejectValue("endDate", "error.dates", "Must be within the same year");
			
		}
//		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "courseName", "error.courseName", "Course name is required.");
//		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "startDate", "error.fromDate", "From Date is required.");
//		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "endDate", "error.toDate", "To Date is required.");
	}

}
