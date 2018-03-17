package com.self.exercise.lbks.lbks_core;

public abstract class Covenant {

	public int bankId;
	public int facilityId;

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public int getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}

	public abstract boolean validate(Loan loan);

}
