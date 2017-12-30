package Controller;

import Model.Model;
import View.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class RegisterDialog {
    public TextField reg_user;
    public TextField reg_password;
    public TextField reg_email;

    public void onClickRegister(ActionEvent event){
        String user = reg_user.getText();
        String password = reg_password.getText();
        String email = reg_email.getText();

        boolean regSuccess = Model.registerUser(user, password, email);

        if (regSuccess){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Registration Successful!\n\nPlease log in with your username and password.");
            alert.show();
        }
        Main.mainController.registerStage.close();
    }

}
