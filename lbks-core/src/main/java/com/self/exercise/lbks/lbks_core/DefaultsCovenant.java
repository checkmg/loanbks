package com.self.exercise.lbks.lbks_core;

public class DefaultsCovenant extends Covenant {

	public double defaultMargin;
	
	@Override
	public boolean validate(Loan loan) {
		
		return (loan.getDefaultProbability() <= defaultMargin);
	}

}
