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
import java.util.*;

import static com.wonderpets.payroll_gui_v2.model.Employee.*;

public class EmployeeController implements Initializable {

    private final SheetsAPI sheetsAPI = new SheetsAPI();
    @FXML
    protected TextField employeeTableSearchField;
    ObservableList<EmployeeObservableListModel> employeeObservableList = FXCollections.observableArrayList();
    @FXML
    private Button employeeTableRefreshButton;
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

    private void setTable() {
        // Creating and loop to populate a table
        int counter = 0;
        while (counter < sheetsAPI.getEmployeeList().size()) {
            int employeeId = sheetsAPI.getEmployeeList().get(counter).getId();
            String firstName = sheetsAPI.getEmployeeList().get(counter).getFirstName();
            String lastName = sheetsAPI.getEmployeeList().get(counter).getLastName();
            String phoneNumber = sheetsAPI.getEmployeeList().get(counter).getPhoneNumber();
            String address = sheetsAPI.getEmployeeList().get(counter).getAddress();

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
        // Binding button to a table selection handler
        employeeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            employeeTableViewButton.setOnAction(this::onViewButtonClick);
            employeeTableDeleteButton.setOnAction(e -> {
                try {
                    onDeleteButtonClick(e, newValue.getEmployeeId());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            employeeTableViewButton.setDisable(newValue == null);
            employeeTableDeleteButton.setDisable(newValue == null);
        });
        employeeTableRefreshButton.setOnAction(e -> {
            try {
                refreshUI();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void searchTable(FilteredList<EmployeeObservableListModel> employeeObservableListModelFilteredList) {

        // Implementing search functionality
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

    }

    private void sortTable(FilteredList<EmployeeObservableListModel> employeeObservableListModelFilteredList) {

        // Setting new values in the table after filtering from the search results
        SortedList<EmployeeObservableListModel> employeeObservableListModelSortedList = new SortedList<>(employeeObservableListModelFilteredList);
        employeeObservableListModelSortedList.comparatorProperty().bind(employeeTableView.comparatorProperty());
        employeeTableView.setItems(employeeObservableListModelSortedList);

    }

    private void loadTable() {

        setTable();
        FilteredList<EmployeeObservableListModel> employeeObservableListModelFilteredList =
                new FilteredList<>(employeeObservableList, b -> true);
        searchTable(employeeObservableListModelFilteredList);
        sortTable(employeeObservableListModelFilteredList);

    }

    private void onViewButtonClick(ActionEvent event) {

        FXMLLoader profileViewLoader = new FXMLLoader(getClass()
                .getResource("/com/wonderpets/payroll_gui_v2/profile-view.fxml"));
        Parent root;

        if (scene != null && scene.getWindow() != null) {
            // If a scene is already open, close it
            scene.getWindow().hide();
        }

        try {
            root = profileViewLoader.load();
            ProfileController controller = profileViewLoader.getController();
            Map<String, String> updateButtonChangedValues = new HashMap<>();

            int counter = 0;
            while (counter < sheetsAPI.getEmployeeList().size()) {
                List<Employee> listOfEmployee = sheetsAPI.getEmployeeList();
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

                    // Update button
                    Button updateButton = controller.getProfileDashboardUpdateButton();
                    ObservableList<TextField> textFieldList = FXCollections.observableArrayList();
                    textFieldList.addAll(
                            controller.getFirstNameTextFieldValue(),
                            controller.getLastNameTextFieldValue(),
                            controller.getAddressTextFieldValue(),
                            controller.getBirthdayTextFieldValue(),
                            controller.getPhoneNumberTextFieldValue(),
                            controller.getStatusTextFieldValue(),
                            controller.getBasicSalaryTextFieldValue(),
                            controller.getGrossSemiMonthlyRateTextFieldValue(),
                            controller.getSssTextFieldValue(),
                            controller.getRiceSubsidyTextFieldValue(),
                            controller.getPhoneAllowanceTextFieldValue(),
                            controller.getHourlyRateTextFieldValue(),
                            controller.getPositionTextFieldValue(),
                            controller.getImmediateSupervisorTextFieldValue(),
                            controller.getPhilhealthTextFieldValue(),
                            controller.getTinTextFieldValue(),
                            controller.getPagibigTextFieldValue(),
                            controller.getClothingAllowanceTextFieldValue()
                    );
                    for (TextField textField : textFieldList) {
                        textField.textProperty().addListener((observable, oldValue, newValue) -> {
                            updateButton.setDisable(false);
                            updateButtonChangedValues.put(textField.getId(), newValue);
                            updateButton.setOnAction(e -> {
                                try {
                                    onUpdateButtonClick(e, queryId, updateButtonChangedValues);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            });
                        });
                    }
                    controller.setProfileDashboardUpdateButton(updateButton);

                    DatePicker startDate = controller.getAttendanceTableStartDatePicker();
                    DatePicker endDate = controller.getAttendanceTableEndDatePicker();

                    // Creating another loop to find all attendance of the current employee
                    int nestedCounter = 0;
                    List<Attendance> listOfAttendance = sheetsAPI.getAttendanceList();
                    ObservableList<ProfileController.AttendanceObservableListModel> attendanceObservableListModel =
                            FXCollections.observableArrayList();

                    while (nestedCounter < sheetsAPI.getAttendanceList().size()) {
                        if (queryId == listOfAttendance.get(nestedCounter).getId()) {
                            String date = sheetsAPI.getAttendanceList().get(nestedCounter).getDate();
                            String timeIn = sheetsAPI.getAttendanceList().get(nestedCounter).getTimeIn();
                            String timeOut = sheetsAPI.getAttendanceList().get(nestedCounter).getTimeOut();

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
                    // Set the value of controller's calculate button
                    controller.setCalculateProfileDashboardButton(calculateButton);
                    // Set the value of controller's Attendance table
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

    private void onUpdateButtonClick(ActionEvent event, int id, Map<String, String> updatedValues) throws IOException {

        List<Employee> employees = sheetsAPI.getEmployeeList();
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == id && !updatedValues.isEmpty()) {
                String cellRange = "Employee Details!A" + (i + 2) + ":S" + (i + 2);
                List<List<Object>> values = SheetsAPI.getDataFromGoogleSheet(cellRange);
                List<Object> row = values.get(0);

                for (Map.Entry<String, String> entry : updatedValues.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    switch (key) {
                        case "lastNameTextFieldValue" -> row.set(1, value);
                        case "firstNameTextFieldValue" -> row.set(2, value);
                        case "addressTextFieldValue" -> row.set(4, value);
                        case "phoneNumberTextFieldValue" -> row.set(5, value);
                        case "sssTextFieldValue" -> row.set(6, value);
                        case "philhealthTextFieldValue" -> row.set(7, value);
                        case "tinTextFieldValue" -> row.set(8, value);
                        case "pagibigTextFieldValue" -> row.set(9, value);
                        case "statusTextFieldValue" -> row.set(10, value);
                        case "positionTextFieldValue" -> row.set(11, value);
                        case "immediateSupervisorTextFieldValue" -> row.set(12, value);
                        case "basicSalaryTextFieldValue" -> row.set(13, value);
                        case "riceSubsidyTextFieldValue" -> row.set(14, value);
                        case "phoneAllowanceTextFieldValue" -> row.set(15, value);
                        case "clothingAllowanceTextFieldValue" -> row.set(16, value);
                        case "grossSemiMonthlyRateTextFieldValue" -> row.set(17, value);
                        case "hourlyRateTextFieldValue" -> row.set(18, value);
                    }
                }

                List<List<Object>> newValues = new ArrayList<>();
                newValues.add(row);
                sheetsAPI.updateValuesInSheet(cellRange, newValues);

                break;
            }
        }
    }

    private void onDeleteButtonClick(ActionEvent event, int index) throws IOException {
        sheetsAPI.deleteEmployee(index);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        start();
    }

    private void start() {
        try {
            sheetsAPI.run();
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshUI() throws IOException {
        // Get the reference to the Stage object
        Stage window = (Stage) employeeTableRefreshButton.getScene().getWindow();
        employeeTableView.setItems(null);

        // Close the window
        window.close();

        // Create a new instance of the EmployeeApplication class
        EmployeeApplication employeeApplication = new EmployeeApplication();

        // Call the start() method of the new instance
        employeeApplication.start(new Stage());
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
