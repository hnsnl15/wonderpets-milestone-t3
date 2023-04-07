package com.wonderpets.payroll_gui_v2.controller.employee;

import com.wonderpets.payroll_gui_v2.util.SheetsAPI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    protected TextField employeeTableSearchField;
    ObservableList<EmployeeObservableListModel> employeeObservableList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<EmployeeObservableListModel, Button> employeeTableAction;
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

                Button tableViewButton = new Button();
                tableViewButton.setText("View");
                tableViewButton.setStyle("-fx-background-color: #32a85a; -fx-text-fill: #ffffff;");

                EmployeeObservableListModel newList = new EmployeeObservableListModel(
                        employeeId,
                        firstName,
                        lastName,
                        phoneNumber,
                        address,
                        tableViewButton
                );
                employeeObservableList.add(newList);
                counter++;
            }
            employeeIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
            employeeFirstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            employeeLastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            employeePhoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            employeeAddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            employeeTableAction.setCellValueFactory(new PropertyValueFactory<>("tableViewButton"));
            employeeTableAction.setStyle("-fx-alignment: center;");

            employeeTableView.setItems(employeeObservableList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class EmployeeObservableListModel {
        int employeeId;
        String firstName, lastName, phoneNumber, address;
        @FXML
        Button tableViewButton;

        public EmployeeObservableListModel(int employeeId, String firstName, String lastName, String phoneNumber, String address, Button viewButton) {
            this.employeeId = employeeId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNumber = phoneNumber;
            this.address = address;
            this.tableViewButton = viewButton;
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

        public Button getTableViewButton() {
            return tableViewButton;
        }

        public void setTableViewButton(Button viewButton) {
            this.tableViewButton = viewButton;
        }
    }

}
