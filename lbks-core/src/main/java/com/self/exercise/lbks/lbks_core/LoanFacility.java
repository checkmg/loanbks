package com.self.exercise.lbks.lbks_core;

import java.util.ArrayList;
import java.util.List;

public class LoanFacility extends Bank implements Comparable<LoanFacility>{

	public int bankId;
	public int facilityId;
	public double fInterest;
	public double fAmount;
	public double balance;
	public List<Loan> fundedLoans = new ArrayList<Loan>();
	public List<Covenant> covenant = new ArrayList<Covenant>();
	
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
	public double getfInterest() {
		return fInterest;
	}
	public void setfInterest(double fInterest) {
		this.fInterest = fInterest;
	}
	public double getfAmount() {
		return fAmount;
	}
	public void setfAmount(double fAmount) {
		this.fAmount = fAmount;
		this.balance = fAmount;
	}
	
	
	public List<Covenant> getCovenant() {
			return covenant;
	}
	public void setCovenant(List<Covenant> covenant) {
		this.covenant = covenant;
	}
	public int compareTo(LoanFacility o) {
		int result = (int) (this.getfInterest()*1000 - o.getfInterest()*1000);
		if(result == 0) {
			result = (int) (o.balance - this.balance);
		}
		return result;
	}

	public boolean addFundedLoan(Loan loan) {
		this.fundedLoans.add(loan);
		//System.out.println("Before Funding - ID:"+facilityId+"    balance:"+balance);
		balance -= loan.amount;
		//System.out.println("After Funding - ID:"+facilityId+"     loanAmnt:"+loan.amount+"    balance:"+balance);
		return true;
	}
	
	public double findExpectedGrossYeild() {
		double grossYeild = 0;
		for(Loan loan : fundedLoans) {
			grossYeild += loan.getExpectedYeild();
		}
		grossYeild = grossYeild - ((fAmount-balance)*fInterest);
		return grossYeild;
	}
	
	@Override
	public String toString() {		
		return "BankId:"+this.getBankId()+"--"+"FacilityId:"+this.getFacilityId()+"--"+"Amount:"+this.getfAmount()+"--"+"Interest:"+this.getfInterest();
	}
	
}
