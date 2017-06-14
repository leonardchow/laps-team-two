package com.teamtwo.laps.controller;


import java.util.ArrayList;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.teamtwo.laps.service.HolidayService;
import com.teamtwo.laps.service.LeaveTypeService;
import com.teamtwo.laps.model.Holiday;
import com.teamtwo.laps.model.LeaveType;

/**
 * Handles requests for the application staff pages.
 */
@Controller
@RequestMapping(value = "/admin/holiday")
public class AdminHolidayController {


	@Autowired
	private HolidayService hService;

	@Autowired
	private LeaveTypeService ltService;
	/**
	 * Renders the staff dashboard.
	 */

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView newLeavePage() {
		ModelAndView mav = new ModelAndView("holiday-new", "holiday", new Holiday());
		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createNewLeavePage(@ModelAttribute @Valid Holiday holiday, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors())
			return new ModelAndView("holiday-new");

		ModelAndView mav = new ModelAndView();
		String message = "New holiday " + holiday.getName() + " was successfully created.";

		hService.createHoliday(holiday);
		mav.setViewName("redirect:/admin/holiday/list");

		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView holidayListPage() {
		ModelAndView mav = new ModelAndView("holiday-list");
		ArrayList<Holiday> holidayList = hService.findAllHoliday();
		mav.addObject("hlist", holidayList);
		return mav;
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editHolidayPage(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("holiday-edit");
		Holiday holiday = hService.findHolidayByName(id);
		mav.addObject("hlist", holiday);
		return mav;
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public ModelAndView editHolidayPage(@ModelAttribute @Valid Holiday holiday, BindingResult result, @PathVariable Integer id,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors())
			return new ModelAndView("holiday-edit");

		ModelAndView mav = new ModelAndView("redirect:/admin/holiday/list");
		String message = "Holiday was successfully updated.";

		hService.changeHoliday(holiday);

		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteRole(@PathVariable Integer id, final RedirectAttributes redirectAttributes)
			 {

		ModelAndView mav = new ModelAndView("redirect:/admin/holiday/list");
		Holiday holiday = hService.findHolidayByName(id);
		hService.removeHoliday(holiday);
		String message = "The holiday " + holiday.getName() + " was successfully deleted.";

		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}

}
