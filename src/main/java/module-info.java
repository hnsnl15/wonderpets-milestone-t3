module com.wonderpets.payroll_gui_v2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires google.api.client;
    requires google.oauth.client;
    requires google.http.client;
    requires google.http.client.jackson2;
    requires google.api.services.sheets.v4.rev581;

    opens com.wonderpets.payroll_gui_v2 to javafx.fxml;
    exports com.wonderpets.payroll_gui_v2;
    exports com.wonderpets.payroll_gui_v2.controller.employee;
    opens com.wonderpets.payroll_gui_v2.controller.employee to javafx.fxml;
}