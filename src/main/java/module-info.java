module cs.ku {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens ku.cs to javafx.fxml;
    exports ku.cs;
    exports ku.cs.ku_help.controllers to javafx.fxml;
    opens ku.cs.ku_help.controllers to javafx.fxml;
}