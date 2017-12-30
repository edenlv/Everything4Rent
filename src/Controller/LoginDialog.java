package Controller;

import Model.Model;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import View.Main;

public class LoginDialog {

    public TextField login_user;
    public TextField login_password;


    public void onClickLogin(ActionEvent event){
        String user = login_user.getText();
        String password = login_password.getText();

        String authUser = Model.login(user, password);

        if (authUser!=null){
            Main.mainController.loginSuccessful(authUser);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error!! User not found or password incorrect!");
            alert.show();
            Main.mainController.home_helloUser.setVisible(false);
        }
        Main.mainController.loginStage.close();


    }

}
