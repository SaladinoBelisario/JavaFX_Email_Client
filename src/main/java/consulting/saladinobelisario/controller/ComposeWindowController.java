package consulting.saladinobelisario.controller;

import consulting.saladinobelisario.EmailManager;
import consulting.saladinobelisario.controller.services.EmailSenderService;
import consulting.saladinobelisario.model.EmailAccount;
import consulting.saladinobelisario.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ComposeWindowController extends BaseController implements Initializable {

    @FXML
    private TextField recipientTextField;

    @FXML
    private TextField subjectTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private ChoiceBox<EmailAccount> emailAccountChoice;

    @FXML
    void sendButtonAction() {
        EmailSenderService emailSenderService = new EmailSenderService(
                emailAccountChoice.getValue(),
                subjectTextField.getText(),
                recipientTextField.getText(),
                htmlEditor.getHtmlText()
        );
        emailSenderService.start();
        emailSenderService.setOnSucceeded(e->{
            EmailSendingResult emailSendingResult = emailSenderService.getValue();
            switch (emailSendingResult) {
                case SUCCESS:
                    Stage stage = (Stage) recipientTextField.getScene().getWindow();
                    viewFactory.closeStage(stage);
                    break;
                case FAILED_BY_TRANSPORT:
                    errorLabel.setText("Transport error!");
                    break;
                case FAILED_BY_PROVIDER:
                    errorLabel.setText("Provider error!");
                    break;
                case FAILED_BY_OTHER_REASON:
                    errorLabel.setText("Unexpected error!");
                    break;
            }
        });

    }

    public ComposeWindowController(ViewFactory viewFactory, EmailManager emailManager, String fxmlName) {
        super(viewFactory, emailManager, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailAccountChoice.setItems(emailManager.getEmailAccounts());
        emailAccountChoice.setValue(emailManager.getEmailAccounts().get(0));
    }
}
