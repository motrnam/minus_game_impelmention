module com.example.bo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bo to javafx.fxml;
    exports com.example.bo;
}