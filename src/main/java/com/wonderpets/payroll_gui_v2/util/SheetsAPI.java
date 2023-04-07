package com.wonderpets.payroll_gui_v2.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
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
    private static final String APPLICATION_NAME = "MotorPH Google Sheet API";
    private static final String SERVICE_ACCOUNT_FILE = "/credentials.json";
    private static final List<Employee> employees = new ArrayList<>();
    private static final List<Attendance> attendances = new ArrayList<>();

    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static Credential authorize() throws IOException {
        return GoogleCredential
                .fromStream(Objects.requireNonNull(SheetsAPI.class.getResourceAsStream(SERVICE_ACCOUNT_FILE)))
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS_READONLY));
    }

    public static void main(String... args) throws IOException {
        queryEmployeeAttendance();
        queryListOfEmployees();
    }

    public static void run() throws IOException {
        main();
    }

    private static List<List<Object>> getDataFromGoogleSheet(String query) throws IOException {
        Sheets sheetsService = SheetsAPI.getSheetsService();
        String spreadsheetId = "1LaWzN0k-JI1Z9L3UVqIF3K8nthAkourEy7bzfulNQF0";
        ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, query).execute();
        return response.getValues();
    }

    private static void queryEmployeeAttendance() throws IOException {
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
                        new Attendance(employeeId.strip(),
                                firstName.strip() + " " + lastName.strip(),
                                dateOfAttendance.strip(),
                                timeIn.strip(),
                                timeOut.strip())
                );

            } catch (IndexOutOfBoundsException | NullPointerException err) {
                err.printStackTrace();
            }
        }
    }

    private static void queryListOfEmployees() throws IOException {
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
                                lastName.strip(),
                                firstName.strip(),
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

    public static List<Employee> getEmployeeList() {
        return employees;
    }

    public static List<Attendance> getAttendanceList() {
        return attendances;
    }

    private static void printAttendanceList() {
        for (Attendance attendance : attendances) {
            System.out.println(attendance);
        }
    }

    private static void printEmployeeList() {// A comment
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

}
