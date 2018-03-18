# Loan Books
## Pre Req
- Install Eclipse IDE
http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/oxygen2

- Install Maven
https://maven.apache.org/install.html

- Ensure M2_HOME is appropriately confiigured

- git clone <this repo>
  
- cd loanbks

- mvn clean install

## To Verify App

- Import loanbks app to Eclipse IDE as Existing Maven Project in new workspace

- Browse to com.self.exercise.lbks.lbks_core.LoanFundingApp

- Right click --> Run as --> Java Application

- Look for fileURI variable and use "small" or "large" as options to get results.

## LoanFundingProcessor
- processes loans sent to its process method.
- LoanFundingApp reads excel to pull loans, it can be replaced by Resful API controller to receive same events over endpoint.
- It uses LoanFacilityManager to pull all available facilities that are available in memory after first time load from CSV's
- After identifying Facilities that are sorted in the order of Maximum yield and less expensive, covenants are loaded for facilities. If they dont already exist.

## LoanFacilityManager
- Manages facilities and its order based on yields desc.
- It can be extended to integrate with external datastore and poll for any new added facilities that needs remodelling of sorted facilities.

## Covenant
- Covenant is implemented in 2 varities and can be extended to add new rules. Rules can be template based with pluggable properties managed by facility admins.
- Each Covenant implements the logic of verification of loans in its rule booundaries.
