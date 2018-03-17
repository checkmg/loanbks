package com.self.exercise.lbks.lbks_core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.opencsv.CSVReader;

public class LoanFacilityManager {

	public List<LoanFacility> loanFacilities = new ArrayList();
	
	public void loadLoanFacilities(String facilitiesFile) throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(facilitiesFile).getFile());
		CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(file)));
		String[] csvRecord = null;
		boolean isFirstLine = true;
		while((csvRecord = csvReader.readNext()) != null) {
			if(isFirstLine) {
				isFirstLine = false;
			} else {
				LoanFacility facility = new LoanFacility();
				facility.setfAmount(new Double(csvRecord[0]).doubleValue());
				facility.balance=new Double(csvRecord[0]).doubleValue();
				facility.setfInterest(new Double(csvRecord[1]).doubleValue());
				facility.setFacilityId(new Integer(csvRecord[2]).intValue());
				facility.setBankId(new Integer(csvRecord[3]).intValue());
				loanFacilities.add(facility);
			}
		}
	}

	public List<LoanFacility> getLoanFacilities() {
		return loanFacilities;
	}

	public void setLoanFacilities(ArrayList<LoanFacility> loanFacilities) {
		this.loanFacilities = loanFacilities;
	}

	public void publishCovenants(String covenantFile, LoanFacility cheapest) throws NumberFormatException, IOException {		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(covenantFile+"/covenants.csv").getFile());
		CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(file)));
		String[] csvRecord = null;
		boolean isFirstLine = true;
		while((csvRecord = csvReader.readNext()) != null) {
			if(isFirstLine) {
				isFirstLine = false;
			} else {
				int bankId = new Integer(csvRecord[2]).intValue();
				boolean isBankOnlyCovenant = false;
				if(csvRecord[0] == null) {
					isBankOnlyCovenant = true;
				}
				if(bankId == cheapest.bankId) {
					if(!StringUtils.isEmpty(csvRecord[1])) {
						DefaultsCovenant covenant = new DefaultsCovenant();
						covenant.bankId = bankId;
						covenant.facilityId = csvRecord[0] == null ? cheapest.facilityId : new Integer(csvRecord[2]).intValue();						
						covenant.defaultMargin = new Double(csvRecord[1]).doubleValue();
						cheapest.covenant.add(covenant);
					}
					if(!StringUtils.isEmpty(csvRecord[3])) {
						StateCovenant covenant = new StateCovenant();
						covenant.bankId = bankId;
						covenant.facilityId = csvRecord[0] == null ? cheapest.facilityId : new Integer(csvRecord[0]).intValue();
						covenant.bannedState = csvRecord[3];
						cheapest.covenant.add(covenant);
					}
				}
			}
		}

	}
	
	
	
	/**
	public static void main(String[] args) throws IOException {
		LoanFacilityManager loanFacilityManager = new LoanFacilityManager();
		loanFacilityManager.loadLoanFacilities("large/facilities.csv");
		Iterator<LoanFacility> loanFacilitiesIter = loanFacilityManager.getLoanFacilities().iterator();
		while(loanFacilitiesIter.hasNext()) {
			System.out.println(loanFacilitiesIter.next());
		}
		System.out.println("Sorting Collection >>>>>>>");
		Collections.sort(loanFacilityManager.getLoanFacilities());
		loanFacilitiesIter = loanFacilityManager.getLoanFacilities().iterator();
		while(loanFacilitiesIter.hasNext()) {
			System.out.println(loanFacilitiesIter.next());
		}
	}**/	
}
