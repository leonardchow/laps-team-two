package com.teamtwo.laps.javabeans;

public enum LeaveStatus {
	APPROVED, REJECTED, DELETED, UPDATED, CANCELLED;
	
	public String toString() {
		return name().charAt(0) + name().substring(1).toLowerCase();
	}
}
