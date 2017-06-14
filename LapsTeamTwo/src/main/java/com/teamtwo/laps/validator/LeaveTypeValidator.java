package com.teamtwo.laps.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.teamtwo.laps.model.LeaveType;


@Component
public class LeaveTypeValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return LeaveType.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LeaveType l= (LeaveType) target;

		ValidationUtils.rejectIfEmpty(errors, "leaveType", "error.leavetype.leavetype.empty");
		ValidationUtils.rejectIfEmpty(errors, "leaveName", "error.leavetype.leavename.empty");
		System.out.println(l.toString());
	}

}

