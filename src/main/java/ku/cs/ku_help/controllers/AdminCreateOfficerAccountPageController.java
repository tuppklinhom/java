package ku.cs.ku_help.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.ku_help.models.*;
import ku.cs.ku_help.service.CategoryListFileDataSource;
import ku.cs.ku_help.service.DataSource;
import ku.cs.ku_help.service.OfficerListFileDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TreeSet;

public class AdminCreateOfficerAccountPageController {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label errorLabel;
    @FXML
    private Label successLabel;
    @FXML
    private ImageView profileImageView;
    @FXML
    private ChoiceBox<String>categorySelectChoiceBox;
    @FXML
    private TextArea showCategoryTextArea;

    private String usernameInput;
    private String passwordInput;
    private String confirmPasswordInput;
    private String imagePath;
    private String nameInput;

    private DataSource<OfficerList> dataSource;
    private OfficerList officerList;

    private CategoryList newCategory;
    private DataSource<CategoryList> dataSourceCategoryList;
    private CategoryList categoryList;

    private Admin adminUser;

    public void handleAddNewCategory(){
        String ChoiceBox = categorySelectChoiceBox.getValue();
        newCategory.addCategory(ChoiceBox);
        String line = "";
        for (String category : newCategory.getAllCategories()) {
            line = line + category+"\n";
        }
        showCategoryTextArea.setText(line);

    }

    private void showCategoryChoiceBox() {
        categoryList = dataSourceCategoryList.readData();
        Set<String> categoryListForChoiceBox = new TreeSet<String>();
        categoryListForChoiceBox.addAll(categoryList.getAllCategories());
        categorySelectChoiceBox.getItems().addAll(categoryListForChoiceBox);
    }

    public void initialize(){
        dataSource = new OfficerListFileDataSource("data","Officer.csv");
        dataSourceCategoryList = new CategoryListFileDataSource("data", "Category.csv");
        newCategory = new CategoryList();
        officerList = dataSource.readData();
        imagePath = "images/default.jpg";
        adminUser = (Admin) com.github.saacsos.FXRouter.getData();
        showImageProfile();
        clearSuccessLabel();
        clearErrorLabel();
        showCategoryChoiceBox();
    }

    private void showImageProfile(){
        File imageFile = new File(imagePath);
        profileImageView.setImage(new Image(imageFile.toURI().toString()));

    }
    private void clearErrorLabel() {
        errorLabel.setText("");
    }
    private void clearSuccessLabel(){ successLabel.setText("");}


    public void handleUploadPictureButton(ActionEvent event){
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null){
            try{
                File destDir = new File("images");
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }
                String[] fileSplit = file.getName().split("\\.");

                String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm.ss")) + "_" + System.currentTimeMillis() + "." + fileSplit[fileSplit.length-1];
                Path target = FileSystems.getDefault().getPath(destDir.getAbsolutePath()+System.getProperty("file.separator")+filename);
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                profileImageView.setImage(new Image(target.toUri().toString()));
                imagePath = destDir + "/" + filename;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void handleCreateAccountButton(ActionEvent actionEvent){
        nameInput = nameTextField.getText();
        usernameInput = usernameTextField.getText();
        passwordInput = passwordField.getText();
        confirmPasswordInput = confirmPasswordField.getText();



        Officer S = officerList.findOfficer(usernameInput);

        if(S == null){
            if(passwordInput.equals(confirmPasswordInput)){
                Officer newOfficer = new Officer(nameInput, usernameInput, passwordInput, imagePath);
                newOfficer.addCategoryList(newCategory);
                newCategory.clearCategoryList();
                officerList.addOfficer(newOfficer);
                dataSource.writeData(officerList);
                successLabel.setText("Successfully create account");
                errorLabel.setText("");

            }
            else{
                errorLabel.setText("Password doesn't match.");
                successLabel.setText("");
            }
        }
        else{
            errorLabel.setText("This username is already in use.");
            successLabel.setText("");
        }


    }

    public void handleBackButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("admin_main_page", adminUser);
        } catch (IOException e) {
            System.err.println("ไปทีหน้า student_login_report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
        }
    }
}

