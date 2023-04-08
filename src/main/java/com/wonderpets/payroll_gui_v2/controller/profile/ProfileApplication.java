package com.wonderpets.payroll_gui_v2.controller.profile;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ProfileApplication.class.getResource("/com/wonderpets/payroll_gui_v2/profile-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 400);
        stage.setTitle("MotorPH Payroll Management System");
        stage.setScene(scene);
        stage.show();
    }
}
