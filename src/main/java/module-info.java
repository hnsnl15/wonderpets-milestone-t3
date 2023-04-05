module com.wonderpets.payroll_gui_v2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;

    opens com.wonderpets.payroll_gui_v2 to javafx.fxml;
    exports com.wonderpets.payroll_gui_v2;
}