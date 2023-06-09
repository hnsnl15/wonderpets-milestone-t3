package com.wonderpets.payroll_gui_v2.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.wonderpets.payroll_gui_v2.model.Account;
import com.wonderpets.payroll_gui_v2.model.Attendance;
import com.wonderpets.payroll_gui_v2.model.Benefit;
import com.wonderpets.payroll_gui_v2.model.Employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SheetsAPI {
    private static final String spreadsheetId = "1JIevo1BJSuU0Iv8c_YKxSXKtdJJwk3LilAT822P3IW8";
    private static final List<Employee> employees = new ArrayList<>();
    private static final List<Attendance> attendances = new ArrayList<>();

    public static void main(String... args) throws IOException {

        SheetsAPI.queryEmployeeAttendance();
        SheetsAPI.queryListOfEmployees();
    }

    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        String APPLICATION_NAME = "MotorPH Google Sheet API";
        return new Sheets.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static Credential authorize() throws IOException {
        String SERVICE_ACCOUNT_FILE = "/.credentials.json";
        return GoogleCredential
                .fromStream(Objects.requireNonNull(SheetsAPI.class.getResourceAsStream(SERVICE_ACCOUNT_FILE)))
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
    }

    public static List<List<Object>> getDataFromGoogleSheet(String range) throws IOException {
        return getSheetsService().spreadsheets().values()
                .get(spreadsheetId, range).execute().getValues();
    }

    public static void queryEmployeeAttendance() throws IOException {
        List<List<Object>> attendanceRecord = getDataFromGoogleSheet("Attendance Record!A2:F2176");

        for (List<?> row : attendanceRecord) {
            try {
                String employeeId = (String) row.get(0);
                String lastName = (String) row.get(1);
                String firstName = (String) row.get(2);
                String dateOfAttendance = (String) row.get(3);
                String timeIn = (String) row.get(4);
                String timeOut = (String) row.get(5);

                attendances.add(
                        new Attendance(employeeId,
                                firstName + " " + lastName,
                                dateOfAttendance,
                                timeIn,
                                timeOut)
                );

            } catch (IndexOutOfBoundsException | NullPointerException err) {
                err.printStackTrace();
            }
        }
    }

    public static void queryListOfEmployees() throws IOException {
        List<List<Object>> values = getDataFromGoogleSheet("Employee Details!A2:S26");

        for (List<?> row : values) {
            try {
                String empId = (String) row.get(0);
                String lastName = (String) row.get(1);
                String firstName = (String) row.get(2);
                String birthday = (String) row.get(3);
                String address = (String) row.get(4);
                String phoneNumber = (String) row.get(5);
                String sss = (String) row.get(6);
                String philHealth = (String) row.get(7);
                String tin = (String) row.get(8);
                String pagibig = (String) row.get(9);
                String status = (String) row.get(10);
                String position = (String) row.get(11);
                String immediateSupervisor = (String) row.get(12);
                String basicSalary = (String) row.get(13);
                String riceSubsidy = (String) row.get(14);
                String phoneAllowance = (String) row.get(15);
                String clothingAllowance = (String) row.get(16);
                String grossMonthlySemiRate = (String) row.get(17);
                String hourlyRate = (String) row.get(18);

                String cleanedBasicSalary = basicSalary.replace(",", "");
                String cleanedRiceSubsidy = riceSubsidy.replace(",", "");
                String cleanedPhoneAllowance = phoneAllowance.replace(",", "");
                String cleanedClothingAllowance = clothingAllowance.replace(",", "");
                String cleanedGrossMonthlySemiRate = grossMonthlySemiRate.replace(",", "");


                Benefit benefit = new Benefit(
                        Double.parseDouble(cleanedBasicSalary),
                        Double.parseDouble(cleanedRiceSubsidy),
                        Double.parseDouble(cleanedPhoneAllowance),
                        Double.parseDouble(cleanedClothingAllowance),
                        Double.parseDouble(cleanedGrossMonthlySemiRate),
                        Double.parseDouble(hourlyRate)
                );

                Account account = new Account(sss, tin, pagibig, philHealth);

                employees.add(
                        new Employee(
                                firstName.strip(),
                                lastName.strip(),
                                Integer.parseInt(empId),
                                birthday.strip(),
                                address.strip(),
                                phoneNumber.strip(),
                                status.strip(),
                                position.strip(),
                                immediateSupervisor.strip(),
                                account,
                                benefit
                        )
                );

            } catch (IndexOutOfBoundsException | NullPointerException err) {
                err.printStackTrace();
            }
        }

    }

    public static int getRowIndex(int employeeId, String range) throws IOException {
        List<List<Object>> values = getDataFromGoogleSheet(range);

        for (int i = 0; i < values.size(); i++) {
            List<?> row = values.get(i);
            String id = (String) row.get(0);
            if (id.equals(String.valueOf(employeeId))) {
                // Found the row with the given ID, return its index
                return i + 1; // Add 1 to account for header row
            }
        }

        // Employee ID not found in the sheet, return -1
        return -1;
    }

    public void deleteEmployee(int employeeId) throws IOException {
        String range = "Employee Details!A2:S26";
        int rowIndex = getRowIndex(employeeId, range);
        if (rowIndex == -1) {
            // Employee not found in the sheet, do nothing
            return;
        }

        List<Request> requests = new ArrayList<>();
        DeleteDimensionRequest deleteRequest = new DeleteDimensionRequest();
        DimensionRange rangeToDelete = new DimensionRange();

        List<Sheet> sheets = SheetsAPI.getSheetsService()
                .spreadsheets().get(spreadsheetId).execute().getSheets();
        
        int sheetId = sheets.get(0).getProperties().getSheetId();
        rangeToDelete.setSheetId(sheetId); // Use the sheet ID of the first sheet
        rangeToDelete.setDimension("ROWS");
        rangeToDelete.setStartIndex(rowIndex);
        rangeToDelete.setEndIndex(rowIndex + 1);
        deleteRequest.setRange(rangeToDelete);
        requests.add(new Request().setDeleteDimension(deleteRequest));

        BatchUpdateSpreadsheetRequest requestBody = new BatchUpdateSpreadsheetRequest().setRequests(requests);

        SheetsAPI.getSheetsService().spreadsheets().batchUpdate(spreadsheetId, requestBody).execute();
    }


    public void updateValuesInSheet(String cellRange, List<List<Object>> newValues) throws IOException {
        ValueRange body = new ValueRange().setValues(newValues);
        getSheetsService().spreadsheets().values()
                .update(spreadsheetId, cellRange, body)
                .setValueInputOption("RAW")
                .execute();
    }

    public void run() throws IOException {
        main();
    }

    public List<Attendance> getAttendanceList() {
        return attendances;
    }

    private void printAttendanceList() {
        for (Attendance attendance : attendances) {
            System.out.println(attendance);
        }
    }

    private void printEmployeeList() {
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    public List<Employee> getEmployeeList() {
        return employees;
    }

}
