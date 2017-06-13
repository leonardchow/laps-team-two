package com.teamtwo.laps.javabeans;

public enum ManagerPath {
	PENDING, HISTORY, DASHBOARD;
	
	public String toString() {
		return name().charAt(0) + name().substring(1).toLowerCase();
	}
}
