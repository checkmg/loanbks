package com.self.exercise.lbks.lbks_core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class LoanFundingApp {

	public static void main(String[] args) throws IOException {
		LoanFundingProcessor loanFundingProcess = new LoanFundingProcessor();
		ClassLoader classLoader = loanFundingProcess.getClass().getClassLoader();
		String fileURI = "large";
		File file = new File(classLoader.getResource(fileURI+"/loans.csv").getFile());
		CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(file)));
		String[] csvRecord = null;
		boolean isFirstLine = true;
		Map<Integer, LoanFacility> results = null;
		while((csvRecord = csvReader.readNext()) != null) {
			if(isFirstLine) {
				isFirstLine = false;
			} else {
				Loan loan = new Loan();
				loan.setInterest(new Double(csvRecord[0]).doubleValue());
				loan.setAmount(new Double(csvRecord[1]).doubleValue());
				loan.setId(new Integer(csvRecord[2]).intValue());
				loan.setDefaultProbability(new Double(csvRecord[3]).doubleValue());
				loan.setState(csvRecord[4]);
				results = loanFundingProcess.process(loan, fileURI);
			}
		}
		publishResults(results);
	}	
	
	public static void publishResults(Map<Integer, LoanFacility> results) throws IOException {
		StringWriter writer = new StringWriter();
		CSVWriter csvWriter = new CSVWriter(new FileWriter("yeilds.csv"), ',');
		csvWriter.writeAll(yeildsStringArray(results));
		csvWriter.close();		
		
		StringWriter writer1 = new StringWriter();
		CSVWriter csvWriter1 = new CSVWriter(new FileWriter("assignments.csv"), ',');
		csvWriter1.writeAll(assignmentStringArray(results));
		csvWriter1.close();		
		
		
	}
	
	private static List<String[]> yeildsStringArray(Map<Integer, LoanFacility> loanFacility) {
		List<String[]> records = new ArrayList<String[]>();

		// adding header record
		records.add(new String[] { "facility_id", "expected_yield" });
		
		for (Map.Entry<Integer, LoanFacility> entry : loanFacility.entrySet()) {
			LoanFacility lf = entry.getValue();
			records.add(new String[] { Integer.toString(lf.facilityId), Double.toString(lf.findExpectedGrossYeild()) });
		}
		return records;
	}

	private static List<String[]> assignmentStringArray(Map<Integer, LoanFacility> loanFacility) {
		List<String[]> records = new ArrayList<String[]>();

		// adding header record
		records.add(new String[] { "loan_id", "facility_id" });
		
		for (Map.Entry<Integer, LoanFacility> entry : loanFacility.entrySet()) {
			LoanFacility lf = entry.getValue();
			Iterator loandIte = lf.fundedLoans.iterator();
			while(loandIte.hasNext()) {
				Loan loan = (Loan)loandIte.next();
				records.add(new String[] { Integer.toString(loan.id), Integer.toString(lf.facilityId) });
			}
		
		}
		return records;
	}
	
}
