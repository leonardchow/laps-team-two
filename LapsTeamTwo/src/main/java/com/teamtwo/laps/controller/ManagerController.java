package com.teamtwo.laps.controller;


import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.teamtwo.laps.service.LeaveService;
import com.teamtwo.laps.service.StaffMemberService;

import com.teamtwo.laps.javabeans.Approve;
import com.teamtwo.laps.javabeans.DashboardBean;
import com.teamtwo.laps.javabeans.LeaveStatus;
import com.teamtwo.laps.javabeans.ManagerPath;
import com.teamtwo.laps.model.Leave;
import com.teamtwo.laps.model.LeaveType;
import com.teamtwo.laps.model.StaffMember;
import com.teamtwo.laps.service.LeaveService;
import com.teamtwo.laps.service.StaffMemberService;

/**
 * Handles requests for the application staff pages.
 */
@Controller
@RequestMapping(value = "/manager")
public class ManagerController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private LeaveService lService;

	@Autowired
	private StaffMemberService smService;

	private final int DASHBOARD_NUM_TO_SHOW = 3;

	/**
	 * Renders the staff dashboard.
	 */
	@RequestMapping(value = "/dashboard")
	public ModelAndView home(HttpSession session) {

		UserSession userSession = (UserSession) session.getAttribute("USERSESSION");

		if (userSession == null || userSession.getSessionId() == null) {
			// return new ModelAndView("redirect:/home/login");
		}

		// int staffId = userSession.getEmployee().getStaffId();
		// String userid = userSession.getUser().getUserId();
		int staffId = 1;

		StaffMember staffMember = smService.findStaffById(staffId);
		ArrayList<Leave> leaves = lService.findAllLeaveOfStaff(staffId);
		ArrayList<StaffMember> subordinates = smService.findSubordinates(staffId);
		ArrayList<Leave> subordinatesLeaves = new ArrayList<>();

		for (StaffMember staff : subordinates) {
			subordinatesLeaves.addAll(staff.getAppliedLeaves().stream()
					.filter(al -> al.getStatus() == LeaveStatus.PENDING).collect(Collectors.toList()));
		}

		ModelAndView modelAndView = new ModelAndView("manager-dashboard");
		modelAndView = DashboardBean.getDashboard(modelAndView, DASHBOARD_NUM_TO_SHOW, staffMember, leaves);
		modelAndView.addObject("subLeaves", subordinatesLeaves);
		return modelAndView;
	}

	// DONE
	@RequestMapping(value = "/pending/list")
	public ModelAndView viewPendingPage(HttpSession session) throws IOException {
		ModelAndView modelAndView = new ModelAndView("manager-pending-list");
		HashMap<StaffMember, ArrayList<Leave>> hm = new HashMap<StaffMember, ArrayList<Leave>>();
		UserSession us = (UserSession) session.getAttribute("USERSESSION");
		ModelAndView mav = new ModelAndView("login");
		if (us.getSessionId() != null) {
			for (StaffMember sMember : us.getSubordinates()) {
				ArrayList<Leave> llist = lService.findPendingLeaveByType(sMember.getStaffId());
				hm.put(sMember, llist);
			}
			mav = new ModelAndView("manager-pending-list");
			//Pagination
		     //ObjectMapper mapper = new ObjectMapper();
			mav.addObject("pendinghistory", hm);		     
			return mav;
		}
		return modelAndView;
	}

	// DONE
	@RequestMapping(value = "/pending/detail/{leaveId}")
	public ModelAndView approveApplicationPage(@PathVariable Integer leaveId, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("manager-pending-approve");
		
		ManagerPath mp = (ManagerPath) session.getAttribute("MANAGERPATH");
		if (mp == null) {
			mp = ManagerPath.PENDING;
			session.setAttribute("MANAGERPATH", mp);
		}
		mp = ManagerPath.PENDING;
		session.setAttribute("MANAGERPATH", mp);		
		
		Leave leave = lService.findLeaveById(leaveId);
		modelAndView.addObject("leave", leave);
		modelAndView.addObject("approve", new Approve());
		return modelAndView;
	}

	// leave validation and business logic
	@RequestMapping(value = "/pending/edit/{leaveId}", method = RequestMethod.POST)
	public ModelAndView approveOrRejectCourse(@ModelAttribute("approve") Approve approve, BindingResult result,
			@PathVariable Integer leaveId, HttpSession session, final RedirectAttributes redirectAttributes) {
		if (result.hasErrors())
			return new ModelAndView("manager-approve");
		Leave leave = lService.findLeaveById(leaveId);
		if (approve.getDecision().equalsIgnoreCase("approved")) {
			leave.setStatus(LeaveStatus.APPROVED);
		} else {
			leave.setStatus(LeaveStatus.REJECTED);
		}
		leave.setComment(approve.getComment());
		System.out.println(leave.toString());
		lService.changeLeave(leave);
		ManagerPath mp = (ManagerPath) session.getAttribute("MANAGERPATH");
		if (mp == ManagerPath.PENDING) {
			ModelAndView mav = new ModelAndView("redirect:/manager/pending/list");
		} else if (mp == ManagerPath.HISTORY) {
			ModelAndView mav = new ModelAndView("redirect:/manager/pending/list");
		}
		ModelAndView mav = new ModelAndView("redirect:/manager/pending/list");
		String message = "Course was successfully updated.";
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}

	// Yin
	@RequestMapping(value = "/subordinate", method = RequestMethod.GET)
	public ModelAndView viewSubordinateListForLeaveApproval(HttpSession session) {
		ModelAndView mav = new ModelAndView("manager-subordinate");
		UserSession us = (UserSession) session.getAttribute("USERSESSION");
		List<StaffMember> subordinateList = smService.findSubordinates(us.getEmployee().getStaffId());
		mav.addObject("subordinateList", subordinateList);
		return mav;
	}



	@RequestMapping(value = "/subordinate/history/{staffId}", method = RequestMethod.GET)
	public ModelAndView viewSubordinateLeaveHistoryDeatils(@PathVariable int staffId) {
		ModelAndView mav = new ModelAndView("manager-subordinate-history");

		StaffMember staffMember = smService.findStaff(staffId);
		mav.addObject("staffMember", staffMember);

		List<Leave> leaveHistoryList = lService.findStaffLeaveHistory(staffMember.getStaffId());
		mav.addObject("leaveHistoryList", leaveHistoryList);
		return mav;
	}
	
	@RequestMapping(value = "/subordinate/history/detail/{leaveId}", method = RequestMethod.GET)
	public ModelAndView viewSubordinateLeaveHistory(@PathVariable int leaveId) {
		ModelAndView modelAndView = new ModelAndView("manager-pending-approve");
		Leave leave = lService.findLeaveById(leaveId);
		modelAndView.addObject("leave", leave);
		modelAndView.addObject("approve", new Approve());
		return modelAndView;
	}
}
