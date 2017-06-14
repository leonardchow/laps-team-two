package com.teamtwo.laps.javabeans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.teamtwo.laps.model.Holiday;
import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.service.HolidayService;

@Controller
public class LeavePeriodCalculator {

	public static Double calculateLeaveDays(Leave leave, HolidayService hService) {
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.setTime(leave.getStartDate());
		end.setTime(leave.getEndDate());

		// find length of leave
		int startDateInt = start.get(Calendar.MONTH) * start.getActualMaximum(Calendar.DAY_OF_MONTH)
				+ start.get(Calendar.DATE);
		int endDateInt = end.get(Calendar.MONTH) * end.getActualMaximum(Calendar.DAY_OF_MONTH) + end.get(Calendar.DATE);

		int startHours = start.get(Calendar.HOUR_OF_DAY);
		int endHours = end.get(Calendar.HOUR_OF_DAY);

		double claimingDays = endDateInt - startDateInt;
		if (startHours > 0) {
			claimingDays -= 0.5;
		}
		if (endHours > 0) {
			claimingDays += 0.5;
		}

		if (claimingDays <= 14.0) {
			Date startDate = leave.getStartDate();
			Date endDate = leave.getEndDate();
			ArrayList<Holiday> holidays = hService.findAllHoliday();

			for (Holiday holiday : holidays) {
				Date date = holiday.getDate();
				if (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
					// holiday is within leave range
					claimingDays -= 1.0;
				}
			}

			int weekends = 0;
			do {
				// excluding start date
				start.add(Calendar.DAY_OF_MONTH, 1);
				if (start.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
						|| start.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					++weekends;
				}
			} while (start.getTimeInMillis() < end.getTimeInMillis());
			
			claimingDays -= weekends;
		}
		return claimingDays;
	}
	
	public static Boolean areDatesOnWeekdays(Leave leave) {
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.setTime(leave.getStartDate());
		end.setTime(leave.getEndDate());
		
		if (start.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
						|| start.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
						|| end.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
						|| end.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Boolean isDateOnWeekend(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
						|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return true;
		} else {
			return false;
		}
	}
}
