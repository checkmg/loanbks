package com.self.exercise.lbks.lbks_core;

public class StateCovenant extends Covenant {

	public String bannedState;
	
	@Override
	public boolean validate(Loan loan) {
		return !bannedState.equals(loan.state); 
	}

}
