package com.teamtwo.laps.javabeans;

public enum StaffPath {
	SPENDING, SHISTORY, SDASHBOARD, SDETAIL;
	
	public String toString() {
		return name().charAt(0) + name().substring(1).toLowerCase();
	}
}
