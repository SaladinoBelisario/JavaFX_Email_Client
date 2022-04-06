package consulting.saladinobelisario.controller;

import consulting.saladinobelisario.EmailManager;
import consulting.saladinobelisario.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;

public class ComposeMessageController extends BaseController{

    @FXML
    private TextField recipientTextFIeld;

    @FXML
    private TextField subjectTextField;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Label errorLabel;

    public ComposeMessageController(ViewFactory viewFactory, EmailManager emailManager, String fxmlName) {
        super(viewFactory, emailManager, fxmlName);
    }

    @FXML
    void sendButtonAction() {
        System.out.println(htmlEditor.getHtmlText());
        System.out.println("send button!");
    }


}