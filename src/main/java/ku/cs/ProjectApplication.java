package ku.cs;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        com.github.saacsos.FXRouter.bind(this, stage, "Report Application", 1200, 800);
        configRoute();
        com.github.saacsos.FXRouter.goTo("student_login_page");

    }

    private static void configRoute() {
        String packageStr = "ku/cs/";
        com.github.saacsos.FXRouter.when("student_main_page", packageStr+"student_main_page.fxml");
        com.github.saacsos.FXRouter.when("student_login_page", packageStr+"student_login_page.fxml");
        com.github.saacsos.FXRouter.when("officer_main_page", packageStr+"officer_main_page.fxml");
        com.github.saacsos.FXRouter.when("officer_login_page", packageStr+"officer_login_page.fxml");
        com.github.saacsos.FXRouter.when("student_create_report", packageStr+"student_create_report.fxml");
        com.github.saacsos.FXRouter.when("admin_change_password_page", packageStr+"admin_change_password_page.fxml");
        com.github.saacsos.FXRouter.when("admin_login_page", packageStr+"admin_login_page.fxml");
        com.github.saacsos.FXRouter.when("admin_main_page", packageStr+"admin_main_page.fxml");
        com.github.saacsos.FXRouter.when("student_registration_page", packageStr+"student_registration_page.fxml");

    }


    public static void main(String[] args) {
        launch();
    }
}
