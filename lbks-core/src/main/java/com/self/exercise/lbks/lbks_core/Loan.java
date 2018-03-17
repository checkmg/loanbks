package com.self.exercise.lbks.lbks_core;

public class Loan {

	public double interest;
	public double amount;
	public int id;
	public String state;
	public double defaultProbability;
	
	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getDefaultProbability() {
		return defaultProbability;
	}

	public void setDefaultProbability(double defaultProbability) {
		this.defaultProbability = defaultProbability;
	}

	
	public double getPartLoanYeild() {
		
		return ((1-defaultProbability)*interest*amount)-(defaultProbability*amount);
	}
		
	public double getExpectedYeild() {
		return ((1-defaultProbability)*interest*amount)-(defaultProbability*amount);
	}
}
