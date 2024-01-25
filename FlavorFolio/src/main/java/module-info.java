module com.example.pop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pop to javafx.fxml;
    exports com.example.pop;
}