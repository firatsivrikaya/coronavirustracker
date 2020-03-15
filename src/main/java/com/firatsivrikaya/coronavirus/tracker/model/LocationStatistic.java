package com.firatsivrikaya.coronavirus.tracker.model;

public class LocationStatistic {
	private String state;
	private String country;
	private int latestTotalCases;
	private int differenceFromPreviousDay;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getLatestTotalCases() {
		return latestTotalCases;
	}

	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}

	public int getDifferenceFromPreviousDay() {
		return differenceFromPreviousDay;
	}

	public void setDifferenceFromPreviousDay(int differenceFromPreviousDay) {
		this.differenceFromPreviousDay = differenceFromPreviousDay;
	}

	@Override
	public String toString() {
		return "LocationStatistic [state=" + state + ", country=" + country + ", latestTotalCases=" + latestTotalCases
				+ "]";
	}

}
