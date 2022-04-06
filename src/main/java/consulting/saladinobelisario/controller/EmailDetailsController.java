package consulting.saladinobelisario.controller;


import java.awt.Desktop;

import consulting.saladinobelisario.EmailManager;
import consulting.saladinobelisario.controller.services.MessageRendererService;
import consulting.saladinobelisario.model.EmailMessage;
import consulting.saladinobelisario.view.ViewFactory;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.web.WebView;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;

public class EmailDetailsController extends BaseController implements Initializable {

    private String LOCATION_OF_DOWNLOADS = System.getProperty("user.home") + "/Downloads/";

    @FXML
    private WebView webWiew;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label attachLabel;

    @FXML
    private Label fromLabel;

    @FXML
    private HBox hBoxDownloads;

    public EmailDetailsController(ViewFactory viewFactory, EmailManager emailManager, String fxmlName) {
        super(viewFactory, emailManager, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        EmailMessage emailMessage = emailManager.getSelectedMessage();

        subjectLabel.setText(emailMessage.getSubject());
        fromLabel.setText(emailMessage.getSender());
        loadAttachments(emailMessage);

        MessageRendererService messageRendererService = new MessageRendererService(webWiew.getEngine());

        messageRendererService.setEmailMessage(emailMessage);
        messageRendererService.restart();

    }

    private void loadAttachments(EmailMessage emailMessage) {
        if (emailMessage.hasAttachments()) {
            for (MimeBodyPart mimeBodyPart : emailMessage.getAttachmentList()) {
                try {
                    AttachmentButton attachmentButton = new AttachmentButton(mimeBodyPart);
                    hBoxDownloads.getChildren().add(attachmentButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            attachLabel.setText("");
        }
    }

    private class AttachmentButton extends Button {

        private MimeBodyPart mimeBodyPart;
        private String downloadedFilePath;

        public AttachmentButton(MimeBodyPart mimeBodyPart) throws MessagingException {
            this.mimeBodyPart = mimeBodyPart;
            this.setText(mimeBodyPart.getFileName());
            this.downloadedFilePath = LOCATION_OF_DOWNLOADS + mimeBodyPart.getFileName();

            this.setOnAction(e -> downloadAttachment());
        }

        private void downloadAttachment() {
            colorBlue();
            Service service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<>() {
                        @Override
                        protected Void call() throws Exception {
                            mimeBodyPart.saveFile(downloadedFilePath);
                            return null;
                        }
                    };
                }
            };
            service.start();
            service.setOnSucceeded(e -> {
                colorGreen();
                this.setOnAction(e2 -> {
                    File file = new File(downloadedFilePath);
                    Desktop desktop = Desktop.getDesktop();
                    if (file.exists()) {
                        try {
                            desktop.open(file);
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }

                    }
                });
            });
        }

        private void colorBlue() {
            this.setStyle("-fx-background-color: Blue");
        }

        private void colorGreen() {
            this.setStyle("-fx-background-color: Green");
        }

    }
}