package com.wonderpets.payroll_gui_v2.controller.employee;

import com.wonderpets.payroll_gui_v2.controller.profile.ProfileController;
import com.wonderpets.payroll_gui_v2.model.Attendance;
import com.wonderpets.payroll_gui_v2.model.Employee;
import com.wonderpets.payroll_gui_v2.util.SheetsAPI;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.wonderpets.payroll_gui_v2.model.Employee.*;

public class EmployeeController implements Initializable {
    @FXML
    protected TextField employeeTableSearchField;
    ObservableList<EmployeeObservableListModel> employeeObservableList = FXCollections.observableArrayList();
    @FXML
    private Button employeeTableUpdateButton;
    @FXML
    private Button employeeTableDeleteButton;
    @FXML
    private Button employeeTableAddButton;
    @FXML
    private Button employeeTableViewButton;
    private Scene scene;
    @FXML
    private TableView<EmployeeObservableListModel> employeeTableView;
    @FXML
    private TableColumn<EmployeeObservableListModel, Integer> employeeIdTableColumn;

    @FXML
    private TableColumn<EmployeeObservableListModel, String> employeeFirstNameTableColumn;
    @FXML
    private TableColumn<EmployeeObservableListModel, String> employeeLastNameTableColumn;
    @FXML
    private TableColumn<EmployeeObservableListModel, String> employeePhoneNumberTableColumn;
    @FXML
    private TableColumn<EmployeeObservableListModel, String> employeeAddressTableColumn;

    private void onViewButtonClick(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/wonderpets/payroll_gui_v2/profile-view.fxml"));
        Parent root;

        if (scene != null && scene.getWindow() != null) {
            // If a scene is already open, close it
            scene.getWindow().hide();
        }

        try {
            SheetsAPI.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            root = fxmlLoader.load();
            ProfileController controller = fxmlLoader.getController();

            int counter = 0;
            while (counter < SheetsAPI.getEmployeeList().size()) {
                List<Employee> listOfEmployee = SheetsAPI.getEmployeeList();
                int queryId = employeeTableView.getSelectionModel().getSelectedItem().getEmployeeId();

                if (queryId == listOfEmployee.get(counter).getId()) {
                    controller.setFirstNameTextFieldValue(listOfEmployee.get(counter).getFirstName());
                    controller.setLastNameTextFieldValue(listOfEmployee.get(counter).getLastName());
                    controller.setAddressTextFieldValue(listOfEmployee.get(counter).getAddress());
                    controller.setBirthdayTextFieldValue(listOfEmployee.get(counter).getBirthday().toString());
                    controller.setPhoneNumberTextFieldValue(listOfEmployee.get(counter).getPhoneNumber());
                    controller.setStatusTextFieldValue(listOfEmployee.get(counter).getStatus());
                    controller.setBasicSalaryTextFieldValue(String.valueOf(listOfEmployee.get(counter).getBenefit().getBasicSalary()));
                    controller.setGrossSemiMonthlyRateTextFieldValue(String.valueOf(listOfEmployee.get(counter).getBenefit().getGrossSemiMonthlyRate()));
                    controller.setSssTextFieldValue(listOfEmployee.get(counter).getGovernmentAccounts().sss());
                    controller.setRiceSubsidyTextFieldValue(String.valueOf(listOfEmployee.get(counter).getBenefit().getRiceSubsidy()));
                    controller.setPhoneAllowanceTextFieldValue(String.valueOf(listOfEmployee.get(counter).getBenefit().getPhoneAllowance()));
                    controller.setHourlyRateTextFieldValue(String.valueOf(listOfEmployee.get(counter).getBenefit().getHourlyRate()));
                    controller.setPositionTextFieldValue(listOfEmployee.get(counter).getPosition());
                    controller.setImmediateSupervisorTextFieldValue(listOfEmployee.get(counter).getImmediateSupervisor());
                    controller.setPhilhealthTextFieldValue(listOfEmployee.get(counter).getGovernmentAccounts().philhealth());
                    controller.setTinTextFieldValue(listOfEmployee.get(counter).getGovernmentAccounts().tin());
                    controller.setPagibigTextFieldValue(listOfEmployee.get(counter).getGovernmentAccounts().tin());
                    controller.setClothingAllowanceTextFieldValue(String.valueOf(listOfEmployee.get(counter).getBenefit().getClothingAllowance()));

                    DatePicker startDate = controller.getAttendanceTableStartDatePicker();
                    DatePicker endDate = controller.getAttendanceTableEndDatePicker();

                    // Creating another loop to find all attendance of the current employee
                    int nestedCounter = 0;
                    List<Attendance> listOfAttendance = SheetsAPI.getAttendanceList();
                    ObservableList<ProfileController.AttendanceObservableListModel> attendanceObservableListModel =
                            FXCollections.observableArrayList();

                    while (nestedCounter < SheetsAPI.getAttendanceList().size()) {
                        if (queryId == listOfAttendance.get(nestedCounter).getId()) {
                            String date = SheetsAPI.getAttendanceList().get(nestedCounter).getDate();
                            String timeIn = SheetsAPI.getAttendanceList().get(nestedCounter).getTimeIn();
                            String timeOut = SheetsAPI.getAttendanceList().get(nestedCounter).getTimeOut();

                            ProfileController.AttendanceObservableListModel newList =
                                    new ProfileController.AttendanceObservableListModel(date, timeIn, timeOut);
                            attendanceObservableListModel.add(newList);

                            // Populate the attendance table in the profile dashboard
                            TableColumn<ProfileController.AttendanceObservableListModel, String> dateColumn =
                                    controller.getAttendanceTableDateColumn();
                            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
                            dateColumn.setStyle("-fx-alignment: center;");
                            controller.setAttendanceTableDateColumn(dateColumn);

                            TableColumn<ProfileController.AttendanceObservableListModel, String> timeInColumn =
                                    controller.getAttendanceTableTimeInColumn();
                            timeInColumn.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
                            timeInColumn.setStyle("-fx-alignment: center;");
                            controller.setAttendanceTableTimeInColumn(timeInColumn);

                            TableColumn<ProfileController.AttendanceObservableListModel, String> timeOutColumn =
                                    controller.getAttendanceTableTimeOutColumn();
                            timeOutColumn.setCellValueFactory(new PropertyValueFactory<>("timeOut"));
                            timeOutColumn.setStyle("-fx-alignment: center;");
                            controller.setAttendanceTableTimeOutColumn(timeOutColumn);

                        }
                        nestedCounter++;
                    }


                    // Initialize the calculate button for binding necessary events to calculate value based on
                    Button calculateButton = controller.getCalculateProfileDashboardButton();

                    // Initialize the table view with its initial values for attendance
                    TableView<ProfileController.AttendanceObservableListModel> attendanceTableView = controller.getAttendanceTableView();
                    attendanceTableView.setItems(attendanceObservableListModel);

                    FilteredList<ProfileController.AttendanceObservableListModel> attendanceObservableListModelFilteredList =
                            new FilteredList<>(attendanceObservableListModel, b -> true);

                    // Validate start and end date fields when their values change
                    ChangeListener<LocalDate> dateRangeListener = (observable, oldValue, newValue) -> {
                        LocalDate startDateValue = startDate.getValue();
                        LocalDate endDateValue = endDate.getValue();

                        // Check if end date is after start date and disable the button to calculate
                        calculateButton.setDisable(endDateValue != null && startDateValue != null && endDateValue.isBefore(startDateValue));

                        // Filter selected date and update the table
                        attendanceObservableListModelFilteredList.setPredicate(attendance -> {
                            LocalDate date = LocalDate.parse(attendance.getDate());
                            return (startDateValue == null || date.isAfter(startDateValue) || date.isEqual(startDateValue))
                                    && (endDateValue == null || date.isBefore(endDateValue) || date.isEqual(endDateValue));
                        });
                    };
                    startDate.valueProperty().addListener(dateRangeListener);
                    endDate.valueProperty().addListener(dateRangeListener);

                    // Filter selected date and update the table
                    attendanceTableView.setItems(attendanceObservableListModelFilteredList);


                    // If the calculate button is clicked, this event will trigger
                    calculateButton.setOnAction(ev -> {
                        // Calculate date difference in days
                        Duration duration = Duration.between(startDate.getValue().atStartOfDay(),
                                endDate.getValue().atStartOfDay());
                        long days = duration.toDays();

                        int totalHoursWorked = 0;
                        double rate = Double.parseDouble(controller.getHourlyRateTextFieldValue().getText());
                        double salary = 0;
                        for (ProfileController.AttendanceObservableListModel att : attendanceObservableListModelFilteredList) {
                            Attendance att1 = new Attendance(att.getTimeIn(), att.getTimeOut());
                            totalHoursWorked += att1.getHoursWorkedPerDay();
                            salary += (rate * totalHoursWorked);
                        }
                        BigDecimal s = new BigDecimal(salary);
                        Locale ph = new Locale("en", "PH");
                        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(ph);
                        double tax = calculateTax(BigDecimal.valueOf(salary), calculateSSSContribution(BigDecimal.valueOf(salary)),
                                calculatePagibigContribution(BigDecimal.valueOf(salary)),
                                calculatePhilhealthContribution(BigDecimal.valueOf(salary)));


//                        double totalDeductions = calculateSSSContribution(BigDecimal.valueOf(salary))
//                                + calculatePhilhealthContribution(BigDecimal.valueOf(salary))
//                                + calculatePagibigContribution(BigDecimal.valueOf(salary));

                        controller.setAttendanceTableComputedSalaryBasedOnDatePick(moneyFormat.format(s.subtract(BigDecimal.valueOf(tax))));

                    });

                    controller.setCalculateProfileDashboardButton(calculateButton);

                    controller.setAttendanceTableView(attendanceTableView);
                }
                counter++;
            }

            scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("MotorPH Payroll Management System");
            stage.setScene(scene);
            stage.show();

            // Close profile window
            controller.getProfileDashboardCloseButton().setOnAction(actionEvent -> stage.close());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            SheetsAPI.run();
            // Creating and loop to populate a table
            int counter = 0;
            while (counter < SheetsAPI.getEmployeeList().size()) {
                int employeeId = SheetsAPI.getEmployeeList().get(counter).getId();
                String firstName = SheetsAPI.getEmployeeList().get(counter).getFirstName();
                String lastName = SheetsAPI.getEmployeeList().get(counter).getLastName();
                String phoneNumber = SheetsAPI.getEmployeeList().get(counter).getPhoneNumber();
                String address = SheetsAPI.getEmployeeList().get(counter).getAddress();

                // Creating new table cell with employee data
                EmployeeObservableListModel newList = new EmployeeObservableListModel(
                        employeeId,
                        firstName,
                        lastName,
                        phoneNumber,
                        address
                );
                employeeObservableList.add(newList);
                counter++;
            }
            // Setting up the initial value of the table
            employeeIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
            employeeIdTableColumn.setStyle("-fx-alignment: center;");
            employeeFirstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            employeeFirstNameTableColumn.setStyle("-fx-alignment: center;");
            employeeLastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            employeeLastNameTableColumn.setStyle("-fx-alignment: center;");
            employeePhoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            employeePhoneNumberTableColumn.setStyle("-fx-alignment: center;");
            employeeAddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            employeeAddressTableColumn.setStyle("-fx-alignment: center;");

            employeeTableView.setItems(employeeObservableList);
            // Binding button to a handler
            employeeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                employeeTableViewButton.setOnAction(this::onViewButtonClick);
                employeeTableViewButton.setDisable(newValue == null);
            });

            // Implementing search functionality
            FilteredList<EmployeeObservableListModel> employeeObservableListModelFilteredList = new FilteredList<>(employeeObservableList, b -> true);
            employeeTableSearchField.textProperty().addListener((observable, oldValue, newValue) ->
                    employeeObservableListModelFilteredList.setPredicate(emp -> {

                        if (newValue.isEmpty() || newValue.isBlank()) return true;

                        String searchKeyword = newValue.toLowerCase();

                        if ((String.valueOf(emp.getEmployeeId())).contains(searchKeyword)) {
                            return true;
                        } else if (emp.getFirstName().toLowerCase().contains(searchKeyword)) {
                            return true;
                        } else return emp.getLastName().toLowerCase().contains(searchKeyword);
                    }));

            // Setting new values in the table after filtering from the search results
            SortedList<EmployeeObservableListModel> employeeObservableListModelSortedList = new SortedList<>(employeeObservableListModelFilteredList);
            employeeObservableListModelSortedList.comparatorProperty().bind(employeeTableView.comparatorProperty());
            employeeTableView.setItems(employeeObservableListModelSortedList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class EmployeeObservableListModel {

        private int employeeId;
        private String firstName, lastName, phoneNumber, address;

        public EmployeeObservableListModel(int employeeId, String firstName, String lastName, String phoneNumber, String address) {
            this.employeeId = employeeId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNumber = phoneNumber;
            this.address = address;
        }

        public int getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }


    }

}
