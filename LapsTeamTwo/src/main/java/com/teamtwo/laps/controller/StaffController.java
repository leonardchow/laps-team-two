package com.teamtwo.laps.controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dbunit.dataset.stream.StreamingDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.teamtwo.laps.javabeans.LeaveStatus;
import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.model.LeaveType;
import com.teamtwo.laps.model.Overtime;
import com.teamtwo.laps.javabeans.DashboardBean;
import com.teamtwo.laps.javabeans.EmailSender;
import com.teamtwo.laps.javabeans.MovementBean;
import com.teamtwo.laps.javabeans.OvertimeList;
import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.service.LeaveService;
import com.teamtwo.laps.service.LeaveTypeService;
import com.teamtwo.laps.service.OvertimeService;
import com.teamtwo.laps.service.StaffMemberService;
import com.teamtwo.laps.validator.LeaveValidator;

/**
 * Handles requests for the application staff pages.
 */
@Controller
@RequestMapping(value = "/staff")
public class StaffController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private final int DASHBOARD_NUM_TO_SHOW = 3;

	@Autowired
	private StaffMemberService smService;

	@Autowired
	private LeaveService lService;

	@Autowired
	private LeaveTypeService lTypeService;

	@Autowired
	private OvertimeService otService;

	@Autowired
	private LeaveValidator leaveValidator;
	
	@InitBinder("leave")
	private void initCourseBinder(WebDataBinder binder) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		dateFormat.setLenient(false);
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.addValidators(leaveValidator);

	}
	
	/**
	 * Renders the staff dashboard.
	 */
	@RequestMapping(value = "/dashboard")
	public ModelAndView home(HttpSession session) {

		UserSession userSession = (UserSession) session.getAttribute("USERSESSION");

		if (userSession == null || userSession.getSessionId() == null) {
			return new ModelAndView("redirect:/home/login");
		}

		int staffId = userSession.getEmployee().getStaffId();
		String userid = userSession.getUser().getUserId();

		StaffMember staffMember = smService.findStaffById(staffId);
		ArrayList<Leave> leaves = lService.findAllLeaveOfStaff(staffId);
		ModelAndView modelAndView = new ModelAndView("staffDashboard");

		modelAndView = DashboardBean.getDashboard(modelAndView, DASHBOARD_NUM_TO_SHOW, staffMember, leaves);

		// leaves.get(1).getStartDate().getDate()
		logger.info("Rendering dashboard for user {}.", userid);

		return modelAndView;
	}

	@RequestMapping(value = "/email")
	public String sendEmail() {
		final String USER_NAME = "sa44lapsteamtwo"; // GMail user name (just the
													// part before "@gmail.com")
		final String PASSWORD = "lapsteamtwo"; // GMail password
		final String RECIPIENT = "sa44lapsteamtwo@gmail.com";

		String from = USER_NAME;
		String pass = PASSWORD;
		String[] to = { RECIPIENT }; // list of recipient email addresses
		String subject = "Java send mail example";
		String body = "Welcome to JavaMail!";

		EmailSender.sendFromGMail(from, pass, to, subject, body);

		return "email";
	}

	@RequestMapping(value = "/movement", method = RequestMethod.GET)
	public ModelAndView movement(HttpSession session) {

		UserSession userSession = (UserSession) session.getAttribute("USERSESSION");

		if (userSession == null || userSession.getSessionId() == null) {
			// return new ModelAndView("redirect:/home/login");
		}

		// int staffId = userSession.getEmployee().getStaffId();
		// String userid = userSession.getUser().getUserId();

		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		ModelAndView modelAndView = new ModelAndView("staff-movement-register", "monthSelect", Integer.class);

		modelAndView.addObject("picked", currentMonth);

		return MovementBean.getMovementMAV(lService, modelAndView, currentMonth, currentYear);
	}

	@RequestMapping(value = "/movement", method = RequestMethod.POST)
	public ModelAndView movementPost(HttpServletRequest request, HttpServletResponse response) {
		String picker = request.getParameter("monthPicker");

		int currentMonth = Integer.parseInt(picker);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		ModelAndView modelAndView = new ModelAndView("staff-movement-register", "monthSelect", Integer.class);

		modelAndView = MovementBean.getMovementMAV(lService, modelAndView, currentMonth, currentYear);

		modelAndView.addObject("picked", picker);

		return modelAndView;
	}

	@RequestMapping(value = "/view/{staffId}")
	public ModelAndView viewStaffMemberPage(@PathVariable Integer staffId) {
		ModelAndView modelAndView = new ModelAndView("staffMember-view");
		StaffMember staffMember = smService.findStaffById(staffId);
		modelAndView.addObject("staffMember", staffMember);
		return modelAndView;
	}

	// @RequestMapping(value = "/leave/{staffId}")
	// public ModelAndView viewStaffLeave(@PathVariable Integer staffId) {
	// ModelAndView modelAndView = new ModelAndView("staffMember-leave-view");
	//// ArrayList<Leave> leaves = lService.findAllLeaveOfStaff(staffId);
	// modelAndView.addObject("leaves", lService.findAllLeaveOfStaff(staffId));
	// return modelAndView;
	// }

	@RequestMapping(value = "/leave/create", method = RequestMethod.GET)
	public ModelAndView NewLeavePage(HttpSession session) {
		
		UserSession us = (UserSession) session.getAttribute("USERSESSION");
		
		if (us == null) {
			return new ModelAndView("redirect:/home/login");
		}
		int loggedInStaffId = us.getUser().getStaffId();
		
		ModelAndView mav = new ModelAndView("staff-leave-new");
		ArrayList<LeaveType> leaveTypes = lTypeService.findAllLeaveType();
		ArrayList<StaffMember> staffMembers = (ArrayList<StaffMember>) smService.findAllStaff().stream()
				.filter(staff -> staff.getStaffId() != loggedInStaffId).collect(Collectors.toList());
		Leave leave = new Leave();
		mav.addObject("leave", leave);
		mav.addObject("leaveTypes", leaveTypes);
		mav.addObject("staffMembers", staffMembers);
		
		int unclaimedHours = otService.findUnclaimedOvertimeOfStaff(loggedInStaffId);
		mav.addObject("compHours", unclaimedHours);
		mav.addObject("compDays", unclaimedHours * 1.0 / 8);
		
		return mav;
	}

	
	@RequestMapping(value = "/history")
	public ModelAndView employeeCourseHistory(HttpSession session) {
		UserSession us = (UserSession) session.getAttribute("USERSESSION");
		ModelAndView mav = new ModelAndView("login");
		if (us.getSessionId() != null) {
			mav = new ModelAndView("/staff-leave-history");
			mav.addObject("lhistory", lService.findLeaveById(us.getUser().getStaffId()));
			return mav;
		}
		return mav;

	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/home/login";

	}
	
@RequestMapping(value = "/leave/created", method = RequestMethod.POST)
public ModelAndView createNewLeave(@ModelAttribute @Valid Leave leave, BindingResult result,
		final RedirectAttributes redirectAttributes, HttpSession session,
		HttpServletRequest request) {
	UserSession us = (UserSession) session.getAttribute("USERSESSION");
	
	if (us == null) {
		return new ModelAndView("redirect:/home/login");
	}
	int staffId = us.getUser().getStaffId();

	Boolean compNotOkay = false;
	String compErrorMessage = "";
	if (leave.getLeaveType() == 3) {
		// If it's compensation do validation
		int unclaimedHours = otService.findUnclaimedOvertimeOfStaff(staffId);
		double eligibleDays = unclaimedHours * 1.0 / 8;
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.setTime(leave.getStartDate());
		end.setTime(leave.getEndDate());
		
		int startDate = start.get(Calendar.MONTH) * start.getActualMaximum(Calendar.DAY_OF_MONTH) + start.get(Calendar.DATE);
		int endDate = end.get(Calendar.MONTH) * end.getActualMaximum(Calendar.DAY_OF_MONTH) + end.get(Calendar.DATE);
		
		String startHalfDay = request.getParameter("startDateHalfDay");
		String endHalfDay = request.getParameter("endDateHalfDay");

		double claimingDays = endDate - startDate;
		
		if (startHalfDay != null) {
			System.out.println("startHalfDay: " + startHalfDay);
			Calendar startDateNew = Calendar.getInstance();
			startDateNew.setTime(leave.getStartDate());
			startDateNew.set(Calendar.HOUR_OF_DAY, 12);
			leave.setStartDate(startDateNew.getTime());
			claimingDays -= 0.5;
		}
		
		if (endHalfDay != null) {
			System.out.println("endHalfDay: " + endHalfDay);
			Calendar endDateNew = Calendar.getInstance();
			endDateNew.setTime(leave.getEndDate());
			endDateNew.set(Calendar.HOUR_OF_DAY, 12);
			leave.setEndDate(endDateNew.getTime());
			claimingDays += 0.5;
		}
		
		System.out.println(claimingDays);
		
		if (claimingDays > eligibleDays) {
			compNotOkay = true;
			compErrorMessage = "You cannot claim more days than you are eligible for.";
		}
	}
	
	if (result.hasErrors() || compNotOkay) {
		ModelAndView mavError = new ModelAndView("staff-leave-new");
		ArrayList<LeaveType> leaveTypes = lTypeService.findAllLeaveType();
		ArrayList<StaffMember> staffMembers = (ArrayList<StaffMember>) smService.findAllStaff().stream()
				.filter(staff -> staff.getStaffId() != staffId).collect(Collectors.toList());
		mavError.addObject("leave", leave);
		mavError.addObject("leaveTypes", leaveTypes);
		mavError.addObject("staffMembers", staffMembers);
		
		int unclaimedHours = otService.findUnclaimedOvertimeOfStaff(staffId);
		mavError.addObject("compHours", unclaimedHours);
		mavError.addObject("compDays", unclaimedHours * 1.0 / 8);

		mavError.addObject("compError", compErrorMessage);
		
		return mavError;
	}
		
	
		ModelAndView mav = new ModelAndView("staff-leave-created");
		// leave.setEmployeeId(us.getEmployee().getEmployeeId());

		leave.setStaffId(us.getUser().getStaffId());

		leave.setStatus(LeaveStatus.PENDING);

		// String message = "New leave " + leave.getLeaveId() + " was
		// successfully created.";
		lService.createLeave(leave);

		return mav;
	}
}
