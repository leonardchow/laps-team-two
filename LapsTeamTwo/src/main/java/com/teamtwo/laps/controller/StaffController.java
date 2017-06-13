package com.teamtwo.laps.controller;

<<<<<<< HEAD
=======
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
>>>>>>> branch 'master' of https://github.com/leonardchow/laps-team-two.git

import java.awt.Dialog.ModalExclusionType;
import java.text.DateFormat;
<<<<<<< HEAD
=======
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
>>>>>>> branch 'master' of https://github.com/leonardchow/laps-team-two.git
import java.util.Date;
import java.util.List;
import java.util.Locale;
<<<<<<< HEAD

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
=======
import java.util.stream.Collectors;
>>>>>>> branch 'master' of https://github.com/leonardchow/laps-team-two.git

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.http.HttpRequest;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

<<<<<<< HEAD
import com.teamtwo.laps.javabeans.LeaveStatus;
import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.model.LeaveType;
=======
import com.teamtwo.laps.javabeans.DashboardBean;
import com.teamtwo.laps.javabeans.LeaveStatus;
import com.teamtwo.laps.model.Leave;
>>>>>>> branch 'master' of https://github.com/leonardchow/laps-team-two.git
import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.service.LeaveService;
import com.teamtwo.laps.service.LeaveTypeService;
import com.teamtwo.laps.service.StaffMemberService;

<<<<<<< HEAD




=======
>>>>>>> branch 'master' of https://github.com/leonardchow/laps-team-two.git

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

//		leaves.get(1).getStartDate().getDate()
		logger.info("Rendering dashboard for user {}.", userid);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/movement", method = RequestMethod.GET)
	public ModelAndView movement(HttpSession session) {
		
		UserSession userSession = (UserSession) session.getAttribute("USERSESSION");
		
		if (userSession == null || userSession.getSessionId() == null) {
			//return new ModelAndView("redirect:/home/login");
		}
		
		//int staffId = userSession.getEmployee().getStaffId();
		//String userid = userSession.getUser().getUserId();
		
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		ModelAndView modelAndView = new ModelAndView("staff-movement-register", "monthSelect", Integer.class);
		
		modelAndView.addObject("picked", currentMonth);
		
		return getMovementMAV(modelAndView, currentMonth, currentYear);
	}
	
	private ModelAndView getMovementMAV(ModelAndView modelAndView, int currentMonth, int currentYear) {
		List<Leave> allLeave = lService.findAllLeave().stream().filter(leave -> {
			Calendar start = Calendar.getInstance();
			start.setTime(leave.getStartDate());
			Calendar end = Calendar.getInstance();
			end.setTime(leave.getEndDate());
			if ((start.get(Calendar.YEAR) == currentYear && start.get(Calendar.MONTH) == currentMonth)
					|| (end.get(Calendar.YEAR) == currentYear && end.get(Calendar.MONTH) == currentMonth))
				return true;
			else
				return false;
		}).collect(Collectors.toList());
		
		modelAndView.addObject("allLeave", allLeave);
		modelAndView.addObject("year", currentYear);
		modelAndView.addObject("monthName", new SimpleDateFormat("MMMM").format(Calendar.getInstance().getTime()));
		modelAndView.addObject("allMonthNames", Arrays.asList(new DateFormatSymbols().getMonths()).subList(0, 12));
		
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/movement", method = RequestMethod.POST)
	public ModelAndView movementPost(HttpServletRequest request, HttpServletResponse response) {
		String picker = request.getParameter("monthPicker");
		
		int currentMonth = Integer.parseInt(picker);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		ModelAndView modelAndView = new ModelAndView("staff-movement-register", "monthSelect", Integer.class);
		
		modelAndView = getMovementMAV(modelAndView, currentMonth, currentYear);
		
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
	
//	@RequestMapping(value = "/leave/{staffId}")
//	public ModelAndView viewStaffLeave(@PathVariable Integer staffId) {
//		ModelAndView modelAndView = new ModelAndView("staffMember-leave-view");
////		ArrayList<Leave> leaves = lService.findAllLeaveOfStaff(staffId);
//		modelAndView.addObject("leaves", lService.findAllLeaveOfStaff(staffId));
//		return modelAndView;
//	}
	
<<<<<<< HEAD
	@RequestMapping(value = "/leave/create", method = RequestMethod.GET)
	public ModelAndView NewLeavePage() {
		ModelAndView mav = new ModelAndView("staff-leave-new");
		
		List<LeaveType> leaveTypes = lTypeService.findAllLeaveType();
		
		Leave leave = new Leave();
		mav.addObject("leave", leave);
		mav.addObject("leaveTypes", leaveTypes);
		
		return mav;
=======
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
	
	
	
>>>>>>> branch 'master' of https://github.com/leonardchow/laps-team-two.git
}
	
	
	
	
@RequestMapping(value = "/leave/created", method = RequestMethod.POST)
public ModelAndView createNewLeave(@ModelAttribute @Valid Leave leave, BindingResult result,
		final RedirectAttributes redirectAttributes, HttpSession session) {



	ModelAndView mav = new ModelAndView("staff-leave-created");
	UserSession us = (UserSession) session.getAttribute("USERSESSION");
	//leave.setEmployeeId(us.getEmployee().getEmployeeId());
	
	leave.setStaffId(us.getUser().getStaffId());
	
	leave.setStatus(LeaveStatus.PENDING);
	

	
	
//	String message = "New leave " + leave.getLeaveId() + " was successfully created.";
	lService.createLeave(leave);

	
	return mav;
}
}


