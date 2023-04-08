package com.wonderpets.payroll_gui_v2.controller.employee;

import com.wonderpets.payroll_gui_v2.controller.profile.ProfileController;
import com.wonderpets.payroll_gui_v2.model.Employee;
import com.wonderpets.payroll_gui_v2.util.SheetsAPI;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    protected TextField employeeTableSearchField;
    ObservableList<EmployeeObservableListModel> employeeObservableList = FXCollections.observableArrayList();
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
                List<Employee> list = SheetsAPI.getEmployeeList();
                if (employeeTableView.getSelectionModel().getSelectedItem().getEmployeeId() == list.get(counter).getId()) {
                    controller.setFirstNameTextFieldValue(list.get(counter).getFirstName());
                    controller.setLastNameTextFieldValue(list.get(counter).getLastName());
                    controller.setAddressTextFieldValue(list.get(counter).getAddress());
                    controller.setBirthdayTextFieldValue(list.get(counter).getBirthday().toString());
                    controller.setPhoneNumberTextFieldValue(list.get(counter).getPhoneNumber());
                    controller.setStatusTextFieldValue(list.get(counter).getStatus());
                    controller.setBasicSalaryTextFieldValue(String.valueOf(list.get(counter).getBenefit().getBasicSalary()));
                    controller.setGrossSemiMonthlyRateTextFieldValue(String.valueOf(list.get(counter).getBenefit().getGrossSemiMonthlyRate()));
                    controller.setSssTextFieldValue(list.get(counter).getGovernmentAccounts().sss());
                    controller.setRiceSubsidyTextFieldValue(String.valueOf(list.get(counter).getBenefit().getRiceSubsidy()));
                    controller.setPhoneAllowanceTextFieldValue(String.valueOf(list.get(counter).getBenefit().getPhoneAllowance()));
                    controller.setHourlyRateTextFieldValue(String.valueOf(list.get(counter).getBenefit().getHourlyRate()));
                    controller.setPositionTextFieldValue(list.get(counter).getPosition());
                    controller.setImmediateSupervisorTextFieldValue(list.get(counter).getImmediateSupervisor());
                    controller.setPhilhealthTextFieldValue(list.get(counter).getGovernmentAccounts().philhealth());
                    controller.setTinTextFieldValue(list.get(counter).getGovernmentAccounts().tin());
                    controller.setPagibigTextFieldValue(list.get(counter).getGovernmentAccounts().tin());
                    controller.setClothingAllowanceTextFieldValue(String.valueOf(list.get(counter).getBenefit().getClothingAllowance()));
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

            int counter = 0;
            while (counter < SheetsAPI.getEmployeeList().size()) {
                int employeeId = SheetsAPI.getEmployeeList().get(counter).getId();
                String firstName = SheetsAPI.getEmployeeList().get(counter).getFirstName();
                String lastName = SheetsAPI.getEmployeeList().get(counter).getLastName();
                String phoneNumber = SheetsAPI.getEmployeeList().get(counter).getPhoneNumber();
                String address = SheetsAPI.getEmployeeList().get(counter).getAddress();


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
