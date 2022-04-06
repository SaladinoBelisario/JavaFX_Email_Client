package consulting.saladinobelisario.controller;

import consulting.saladinobelisario.EmailManager;
import consulting.saladinobelisario.controller.services.MessageRendererService;
import consulting.saladinobelisario.model.EmailMessage;
import consulting.saladinobelisario.model.EmailTreeItem;
import consulting.saladinobelisario.model.FormatableInteger;
import consulting.saladinobelisario.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    private MenuItem markUnread = new MenuItem("mark as unread");
    private MenuItem deleteMessage = new MenuItem("delete message");
    private MenuItem viewDetails = new MenuItem("view details");

    @FXML
    private TreeView<String> folders;

    @FXML
    private TableView<EmailMessage> emailTableView;

    @FXML
    private TableColumn<EmailMessage, String> subjectCol;

    @FXML
    private TableColumn<EmailMessage, String> senderCol;

    @FXML
    private TableColumn<EmailMessage, String> recipientCol;

    @FXML
    private TableColumn<EmailMessage, FormatableInteger> sizeCol;

    @FXML
    private TableColumn<EmailMessage, Date> dateCol;

    @FXML
    private WebView messageView;

    private MessageRendererService messageRendererService;

    public MainWindowController(ViewFactory viewFactory, EmailManager emailManager, String fxmlName) {
        super(viewFactory, emailManager, fxmlName);
    }

    @FXML
    void onOptionsClick() {
        this.viewFactory.showOptionsWindow();
    }

    @FXML
    void addAccountClick() {
        this.viewFactory.showLoginWindow();
    }

    @FXML
    void composeMessageAction() {
        this.viewFactory.showComposeWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpFoldersView();
        setUpFolderSelection();
        setUpEmailsList();
        setUpBoldRows();
        setUpMessageSelection();
        setUpMessageRenderer();
        setUpContextMenus();
    }

    private void setUpMessageRenderer() {
        messageRendererService = new MessageRendererService(messageView.getEngine());
    }

    private void setUpMessageSelection() {
        emailTableView.setOnMouseClicked(e -> {
            EmailMessage message = emailTableView.getSelectionModel().getSelectedItem();
            if (message != null) {
                emailManager.setSelectedMessage(message);
                if (!message.isRead()) {
                    emailManager.setRead();
                }
                messageRendererService.setEmailMessage(message);
                messageRendererService.restart();
            }
        });
    }

    private void setUpFoldersView() {
        folders.setRoot(emailManager.getFoldersRoot());
        folders.setShowRoot(false);
    }

    private void setUpEmailsList() {
        subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("subject"));
        senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("sender"));
        recipientCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("recipient"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, FormatableInteger>("size"));
        dateCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, Date>("date"));

        emailTableView.setContextMenu(new ContextMenu(markUnread, deleteMessage, viewDetails));
    }

    private void setUpFolderSelection() {
        folders.setOnMouseClicked(e -> {
            EmailTreeItem<String> item = (EmailTreeItem<String>) folders.getSelectionModel().getSelectedItem();
            if (item != null) {
                emailTableView.setItems(item.getEmails());
                emailManager.setSelectedFolder(item);
            }
        });
    }

    private void setUpContextMenus() {
        markUnread.setOnAction(e -> {
            emailManager.setUnRead();
        });
        deleteMessage.setOnAction(e -> {
            emailManager.deleteSelectedMessage();
            messageView.getEngine().loadContent("");
        });
        viewDetails.setOnAction(e->{
            viewFactory.showEmailDetailsWindow();
        });
    }

    private void setUpBoldRows() {
        emailTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>() {
            @Override
            public TableRow<EmailMessage> call(TableView<EmailMessage> emailMessageTableView) {
                return new TableRow<EmailMessage>() {
                    @Override
                    protected void updateItem(EmailMessage item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || item.isRead()) {
                            setStyle("");
                        } else {
                            setStyle("-fx-font-weight: bold");
                        }
                    }
                };
            }
        });
    }
}
