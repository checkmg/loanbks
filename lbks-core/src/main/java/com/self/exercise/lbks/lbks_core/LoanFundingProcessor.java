package com.self.exercise.lbks.lbks_core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


public class LoanFundingProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoanFundingProcessor.class);
	List<LoanFacility> facilities = null;
	LoanFacilityManager loanFacilityMgr = null;
	Map<Integer, LoanFacility> results = new HashMap<Integer, LoanFacility>();
	public Map<Integer, LoanFacility> process(Loan loan, String fileURI) throws IOException {		
		LoanFacility loanFacility = findCheapestFacility(loan, fileURI);
		loanFacility.addFundedLoan(loan);				
		results.put(new Integer(loanFacility.facilityId), loanFacility);
		LOGGER.debug("LoanId:{} --- LoanFacilityId:{}", loan.id, loanFacility.facilityId);
		LOGGER.debug("LoanFacilityId:{} --- "+"Yeild:{} ----"+"LoanAmnt:{} ----"+"Balance:{}", loanFacility.facilityId, loanFacility.findExpectedGrossYeild(), loan.amount, loanFacility.balance);

		return results;
		//System.out.println("LoanId:"+loan.id+" --- "+"LoanFacilityId:"+loanFacility.facilityId);
		//System.out.println("LoanFacilityId:"+loanFacility.facilityId+" --- "+"Yeild:"+loanFacility.findExpectedGrossYeild()+"----"+"LoanAmnt:"+loan.amount+"----"+"Balance:"+loanFacility.balance);
	}


	private LoanFacility findCheapestFacility(Loan loan, String fileURI) throws IOException {
		
		if(facilities == null) {
			loanFacilityMgr = new LoanFacilityManager();
			loanFacilityMgr.loadLoanFacilities(fileURI+"/facilities.csv");
			facilities = loanFacilityMgr.loanFacilities;
			Collections.sort(facilities);
		}
		Iterator<LoanFacility> ite = facilities.iterator();
		LoanFacility cheapest = null;
		while(ite.hasNext()) {
			cheapest = ite.next();
			if(cheapest.balance >= loan.amount) {
				if(checkCovenants(fileURI, loan, cheapest)) {
					break;
				} else {
					cheapest = null;
				}
			} else {
				
			}
		}
		return cheapest;
	}
	
	
	private boolean checkCovenants(String fileURI, Loan loan, LoanFacility cheapest) throws NumberFormatException, IOException {
		boolean isMatch = false;
			if(cheapest.getCovenant().isEmpty()) {
				loanFacilityMgr.publishCovenants(fileURI, cheapest);
			}
			for(Covenant covenant : cheapest.covenant) {
				isMatch = covenant.validate(loan);
				if(!isMatch) {
					break;
				}
			}
		return isMatch;
	}

}
