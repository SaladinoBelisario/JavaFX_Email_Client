package consulting.saladinobelisario.controller;

import consulting.saladinobelisario.EmailManager;
import consulting.saladinobelisario.controller.services.LoginService;
import consulting.saladinobelisario.model.EmailAccount;
import consulting.saladinobelisario.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController extends BaseController implements Initializable {

    public LoginWindowController(ViewFactory viewFactory, EmailManager emailManager, String fxmlName) {
        super(viewFactory, emailManager, fxmlName);
        this.loginService = new LoginService(emailManager, null);
    }

    public LoginWindowController(ViewFactory viewFactory, EmailManager emailManager, String fxmlName,
                                 TextField emailAddressField, PasswordField passwordField, Label errorLabel, LoginService loginService ) {
        super(viewFactory, emailManager, fxmlName);
        this.emailAddressField = emailAddressField;
        this.passwordField = passwordField;
        this.errorLabel = errorLabel;
        this.loginService = loginService;
    }

    private LoginService loginService;

    @FXML
    private TextField emailAddressField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    public void loginAction() {
        if(fieldsAreValid()){
            errorLabel.setText("Logging in ...");
            EmailAccount emailAccount = new EmailAccount(
                    emailAddressField.getText(),
                    passwordField.getText()
            );
            //LoginService loginService = new LoginService(emailManager, emailAccount);
            loginService.setEmailAccount(emailAccount);
            loginService.start();
            loginService.setOnSucceeded(e->{
                EmailLoginResult result = loginService.getValue();
                switch (result) {
                    case SUCCESS:
                        if(!this.viewFactory.isMainViewInitialized()){
                            this.viewFactory.showMainWindow();
                        }
                        Stage stage = (Stage) emailAddressField.getScene().getWindow();
                        this.viewFactory.closeStage(stage);
                        break;
                    case FAILED_BY_CREDENTIALS:
                        errorLabel.setText("Invalid credentials!");
                        break;
                    case FAILED_BY_NETWORK:
                        errorLabel.setText("Unexpected error!");
                        break;
                }
            });
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private boolean fieldsAreValid(){
        if(emailAddressField.getText().isEmpty()){
            errorLabel.setText("Please fill email");
            return false;
        }
        if(passwordField.getText().isEmpty()){
            errorLabel.setText("Please fill password");
            return false;
        }
        return true;
    }
}
