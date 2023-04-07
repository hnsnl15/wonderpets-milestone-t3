package com.wonderpets.payroll_gui_v2.controller.employee;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmployeeApplication.class.getResource("/com/wonderpets/payroll_gui_v2/employee-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 568);
        stage.setTitle("Employee");
        stage.setScene(scene);
        stage.show();
    }

}
