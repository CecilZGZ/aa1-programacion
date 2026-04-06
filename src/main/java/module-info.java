module com.jbes.aa1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jbes.aa1 to javafx.fxml;
    exports com.jbes.aa1;
}