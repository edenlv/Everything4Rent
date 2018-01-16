package Controller;

import Model.Model;
import View.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterDialog {
    public TextField reg_user;
    public TextField reg_password;
    public TextField reg_email;

    public void onClickRegister(ActionEvent event){
        String user = reg_user.getText();
        String password = reg_password.getText();
        String email = reg_email.getText();

        boolean isOK = checkIntegrity();
        if (isOK) {

            boolean regSuccess = Model.registerUser(user, password, email);

            if (regSuccess) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Registration Successful!\n\nPlease log in with your username and password.");
                alert.show();
            }
            Main.mainController.registerStage.close();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Bad input! Make sure you enter valid information!");
            alert.show();
        }
    }

    public boolean checkIntegrity(){
        String user = reg_user.getText();
        String password = reg_password.getText();
        String email = reg_email.getText();

        if(user==null || user.equals("")){
            return false;
        }
        if (password==null || password.equals("")){
            return false;
        }

        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(email);

        if (!m.find()) return false;

        return true;
    }

}
