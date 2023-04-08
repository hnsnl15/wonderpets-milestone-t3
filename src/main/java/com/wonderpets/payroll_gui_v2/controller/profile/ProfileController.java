package com.wonderpets.payroll_gui_v2.controller.profile;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class ProfileController {

    @FXML
    private Button calculateProfileDashboardButton;
    @FXML
    private Button profileDashboardCloseButton;
    @FXML
    private TextField birthdayTextFieldValue;
    @FXML
    private TextField addressTextFieldValue;
    @FXML
    private TextField phoneNumberTextFieldValue;
    @FXML
    private TextField statusTextFieldValue;
    @FXML
    private TextField positionTextFieldValue;
    @FXML
    private TextField immediateSupervisorTextFieldValue;
    @FXML
    private TextField sssTextFieldValue;
    @FXML
    private TextField pagibigTextFieldValue;
    @FXML
    private TextField philhealthTextFieldValue;
    @FXML
    private TextField tinTextFieldValue;
    @FXML
    private TextField basicSalaryTextFieldValue;
    @FXML
    private TextField riceSubsidyTextFieldValue;
    @FXML
    private TextField phoneAllowanceTextFieldValue;
    @FXML
    private TextField clothingAllowanceTextFieldValue;
    @FXML
    private TextField grossSemiMonthlyRateTextFieldValue;
    @FXML
    private TextField hourlyRateTextFieldValue;
    @FXML
    private TableView<AttendanceObservableListModel> attendanceTableView;
    @FXML
    private TableColumn<AttendanceObservableListModel, String> attendanceTableDateColumn;
    @FXML
    private TableColumn<AttendanceObservableListModel, String> attendanceTableTimeInColumn;
    @FXML
    private TableColumn<AttendanceObservableListModel, String> attendanceTableTimeOutColumn;
    @FXML
    private DatePicker attendanceTableStartDatePicker;
    @FXML
    private DatePicker attendanceTableEndDatePicker;
    @FXML
    private TextField attendanceTableComputedSalaryBasedOnDatePick;
    @FXML
    private TextField firstNameTextFieldValue;
    @FXML
    private TextField lastNameTextFieldValue;

    public ProfileController() {
    }

    public ProfileController(TextField birthdayTextFieldValue, TextField addressTextFieldValue, TextField phoneNumberTextFieldValue, TextField statusTextFieldValue, TextField positionTextFieldValue, TextField immediateSupervisorTextFieldValue, TextField sssTextFieldValue, TextField pagibigTextFieldValue, TextField philhealthTextFieldValue, TextField tinTextFieldValue, TextField basicSalaryTextFieldValue, TextField riceSubsidyTextFieldValue, TextField phoneAllowanceTextFieldValue, TextField clothingAllowanceTextFieldValue, TextField grossSemiMonthlyRateTextFieldValue, TextField hourlyRateTextFieldValue, TableView<AttendanceObservableListModel> attendanceTableView, TableColumn<AttendanceObservableListModel, String> attendanceTableDateColumn, TableColumn<AttendanceObservableListModel, String> attendanceTableTimeInColumn, TableColumn<AttendanceObservableListModel, String> attendanceTableTimeOutColumn, DatePicker attendanceTableStartDatePicker, DatePicker attendanceTableEndDatePicker, TextField attendanceTableComputedSalaryBasedOnDatePick, TextField firstNameTextFieldValue, TextField lastNameTextFieldValue, AnchorPane profileApplicationViewMainContainer) {
        this.birthdayTextFieldValue = birthdayTextFieldValue;
        this.addressTextFieldValue = addressTextFieldValue;
        this.phoneNumberTextFieldValue = phoneNumberTextFieldValue;
        this.statusTextFieldValue = statusTextFieldValue;
        this.positionTextFieldValue = positionTextFieldValue;
        this.immediateSupervisorTextFieldValue = immediateSupervisorTextFieldValue;
        this.sssTextFieldValue = sssTextFieldValue;
        this.pagibigTextFieldValue = pagibigTextFieldValue;
        this.philhealthTextFieldValue = philhealthTextFieldValue;
        this.tinTextFieldValue = tinTextFieldValue;
        this.basicSalaryTextFieldValue = basicSalaryTextFieldValue;
        this.riceSubsidyTextFieldValue = riceSubsidyTextFieldValue;
        this.phoneAllowanceTextFieldValue = phoneAllowanceTextFieldValue;
        this.clothingAllowanceTextFieldValue = clothingAllowanceTextFieldValue;
        this.grossSemiMonthlyRateTextFieldValue = grossSemiMonthlyRateTextFieldValue;
        this.hourlyRateTextFieldValue = hourlyRateTextFieldValue;
        this.attendanceTableView = attendanceTableView;
        this.attendanceTableDateColumn = attendanceTableDateColumn;
        this.attendanceTableTimeInColumn = attendanceTableTimeInColumn;
        this.attendanceTableTimeOutColumn = attendanceTableTimeOutColumn;
        this.attendanceTableStartDatePicker = attendanceTableStartDatePicker;
        this.attendanceTableEndDatePicker = attendanceTableEndDatePicker;
        this.attendanceTableComputedSalaryBasedOnDatePick = attendanceTableComputedSalaryBasedOnDatePick;
        this.firstNameTextFieldValue = firstNameTextFieldValue;
        this.lastNameTextFieldValue = lastNameTextFieldValue;
    }

    public TextField getBirthdayTextFieldValue() {
        return birthdayTextFieldValue;
    }

    public void setBirthdayTextFieldValue(String birthdayTextFieldValue) {
        this.birthdayTextFieldValue.setText(birthdayTextFieldValue);
    }

    public TextField getAddressTextFieldValue() {
        return addressTextFieldValue;
    }

    public void setAddressTextFieldValue(String addressTextFieldValue) {
        this.addressTextFieldValue.setText(addressTextFieldValue);
    }

    public TextField getPhoneNumberTextFieldValue() {
        return phoneNumberTextFieldValue;
    }

    public void setPhoneNumberTextFieldValue(String phoneNumberTextFieldValue) {
        this.phoneNumberTextFieldValue.setText(phoneNumberTextFieldValue);
    }

    public TextField getStatusTextFieldValue() {
        return statusTextFieldValue;
    }

    public void setStatusTextFieldValue(String statusTextFieldValue) {
        this.statusTextFieldValue.setText(statusTextFieldValue);
    }

    public TextField getPositionTextFieldValue() {
        return positionTextFieldValue;
    }

    public void setPositionTextFieldValue(String positionTextFieldValue) {
        this.positionTextFieldValue.setText(positionTextFieldValue);
    }

    public TextField getImmediateSupervisorTextFieldValue() {
        return immediateSupervisorTextFieldValue;
    }

    public void setImmediateSupervisorTextFieldValue(String immediateSupervisorTextFieldValue) {
        this.immediateSupervisorTextFieldValue.setText(immediateSupervisorTextFieldValue);
    }

    public TextField getSssTextFieldValue() {
        return sssTextFieldValue;
    }

    public void setSssTextFieldValue(String sssTextFieldValue) {
        this.sssTextFieldValue.setText(sssTextFieldValue);
    }

    public TextField getPagibigTextFieldValue() {
        return pagibigTextFieldValue;
    }

    public void setPagibigTextFieldValue(String pagibigTextFieldValue) {
        this.pagibigTextFieldValue.setText(pagibigTextFieldValue);
    }

    public TextField getPhilhealthTextFieldValue() {
        return philhealthTextFieldValue;
    }

    public void setPhilhealthTextFieldValue(String philhealthTextFieldValue) {
        this.philhealthTextFieldValue.setText(philhealthTextFieldValue);
    }

    public TextField getTinTextFieldValue() {
        return tinTextFieldValue;
    }

    public void setTinTextFieldValue(String tinTextFieldValue) {
        this.tinTextFieldValue.setText(tinTextFieldValue);
    }

    public TextField getBasicSalaryTextFieldValue() {
        return basicSalaryTextFieldValue;
    }

    public void setBasicSalaryTextFieldValue(String basicSalaryTextFieldValue) {
        this.basicSalaryTextFieldValue.setText(basicSalaryTextFieldValue);
    }

    public TextField getRiceSubsidyTextFieldValue() {
        return riceSubsidyTextFieldValue;
    }

    public void setRiceSubsidyTextFieldValue(String riceSubsidyTextFieldValue) {
        this.riceSubsidyTextFieldValue.setText(riceSubsidyTextFieldValue);
    }

    public TextField getPhoneAllowanceTextFieldValue() {
        return phoneAllowanceTextFieldValue;
    }

    public void setPhoneAllowanceTextFieldValue(String phoneAllowanceTextFieldValue) {
        this.phoneAllowanceTextFieldValue.setText(phoneAllowanceTextFieldValue);
    }

    public TextField getClothingAllowanceTextFieldValue() {
        return clothingAllowanceTextFieldValue;
    }

    public void setClothingAllowanceTextFieldValue(String clothingAllowanceTextFieldValue) {
        this.clothingAllowanceTextFieldValue.setText(clothingAllowanceTextFieldValue);
    }

    public TextField getGrossSemiMonthlyRateTextFieldValue() {
        return grossSemiMonthlyRateTextFieldValue;
    }

    public void setGrossSemiMonthlyRateTextFieldValue(String grossSemiMonthlyRateTextFieldValue) {
        this.grossSemiMonthlyRateTextFieldValue.setText(grossSemiMonthlyRateTextFieldValue);
    }

    public TextField getHourlyRateTextFieldValue() {
        return hourlyRateTextFieldValue;
    }

    public void setHourlyRateTextFieldValue(String hourlyRateTextFieldValue) {
        this.hourlyRateTextFieldValue.setText(hourlyRateTextFieldValue);
    }

    public TableView<AttendanceObservableListModel> getAttendanceTableView() {
        return attendanceTableView;
    }

    public void setAttendanceTableView(TableView<AttendanceObservableListModel> attendanceTableView) {
        this.attendanceTableView = attendanceTableView;
    }

    public TableColumn<AttendanceObservableListModel, String> getAttendanceTableDateColumn() {
        return attendanceTableDateColumn;
    }

    public void setAttendanceTableDateColumn(TableColumn<AttendanceObservableListModel, String> attendanceTableDateColumn) {
        this.attendanceTableDateColumn = attendanceTableDateColumn;
    }

    public TableColumn<AttendanceObservableListModel, String> getAttendanceTableTimeInColumn() {
        return attendanceTableTimeInColumn;
    }

    public void setAttendanceTableTimeInColumn(TableColumn<AttendanceObservableListModel, String> attendanceTableTimeInColumn) {
        this.attendanceTableTimeInColumn = attendanceTableTimeInColumn;
    }

    public TableColumn<AttendanceObservableListModel, String> getAttendanceTableTimeOutColumn() {
        return attendanceTableTimeOutColumn;
    }

    public void setAttendanceTableTimeOutColumn(TableColumn<AttendanceObservableListModel, String> attendanceTableTimeOutColumn) {
        this.attendanceTableTimeOutColumn = attendanceTableTimeOutColumn;
    }

    public DatePicker getAttendanceTableStartDatePicker() {
        return attendanceTableStartDatePicker;
    }

    public void setAttendanceTableStartDatePicker(DatePicker attendanceTableStartDatePicker) {
        this.attendanceTableStartDatePicker = attendanceTableStartDatePicker;
    }

    public DatePicker getAttendanceTableEndDatePicker() {
        return attendanceTableEndDatePicker;
    }

    public void setAttendanceTableEndDatePicker(DatePicker attendanceTableEndDatePicker) {
        this.attendanceTableEndDatePicker = attendanceTableEndDatePicker;
    }

    public TextField getAttendanceTableComputedSalaryBasedOnDatePick() {
        return attendanceTableComputedSalaryBasedOnDatePick;
    }

    public void setAttendanceTableComputedSalaryBasedOnDatePick(String attendanceTableComputedSalaryBasedOnDatePick) {
        this.attendanceTableComputedSalaryBasedOnDatePick.setText(attendanceTableComputedSalaryBasedOnDatePick);
    }

    public TextField getFirstNameTextFieldValue() {
        return firstNameTextFieldValue;
    }

    public void setFirstNameTextFieldValue(String firstNameTextFieldValue) {
        this.firstNameTextFieldValue.setText(firstNameTextFieldValue);
    }

    public TextField getLastNameTextFieldValue() {
        return lastNameTextFieldValue;
    }

    public void setLastNameTextFieldValue(String lastNameTextFieldValue) {
        this.lastNameTextFieldValue.setText(lastNameTextFieldValue);
    }


    public Button getProfileDashboardCloseButton() {
        return profileDashboardCloseButton;
    }

    public Button getCalculateProfileDashboardButton() {
        return calculateProfileDashboardButton;
    }

    public void setCalculateProfileDashboardButton(Button calculateProfileDashboardButton) {
        this.calculateProfileDashboardButton = calculateProfileDashboardButton;
    }

    public static class AttendanceObservableListModel {
        private String date;
        private String timeIn;
        private String timeOut;

        public AttendanceObservableListModel(String date, String timeIn, String timeOut) {
            this.date = date;
            this.timeIn = timeIn;
            this.timeOut = timeOut;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTimeIn() {
            return timeIn;
        }

        public void setTimeIn(String timeIn) {
            this.timeIn = timeIn;
        }

        public String getTimeOut() {
            return timeOut;
        }

        public void setTimeOut(String timeOut) {
            this.timeOut = timeOut;
        }

    }


}
