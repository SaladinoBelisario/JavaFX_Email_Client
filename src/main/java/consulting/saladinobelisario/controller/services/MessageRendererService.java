package consulting.saladinobelisario.controller.services;

import consulting.saladinobelisario.model.EmailMessage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import java.io.IOException;

public class MessageRendererService extends Service<Void> {

    private EmailMessage emailMessage;
    private WebEngine webEngine;
    private StringBuffer stringBuffer;

    public MessageRendererService(WebEngine webEngine) {
        this.webEngine = webEngine;
        this.stringBuffer = new StringBuffer();
        this.setOnSucceeded(e -> displayMessage());
    }


    public void setEmailMessage(EmailMessage emailMessage) {
        this.emailMessage = emailMessage;
    }


    private void displayMessage() {
        webEngine.loadContent(stringBuffer.toString());
    }


    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    loadMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    private synchronized void loadMessage() throws MessagingException, IOException {
        stringBuffer.setLength(0); //clears the SB
        emailMessage.clearAttachments();
        Message message = emailMessage.getMessage();
        String contentType = message.getContentType();
        if(isSimpleType(contentType)){
            stringBuffer.append(message.getContent().toString());
        } else if(isMultipartType(contentType)){
            Multipart multipart = (Multipart) message.getContent();
            loadMultipart(multipart, stringBuffer);
        }
    }

    private void loadMultipart(Multipart multipart, StringBuffer stringBuffer) throws MessagingException, IOException {
        for (int i = multipart.getCount() - 1; i>=0; i--){
            BodyPart bodyPart = multipart.getBodyPart(i);
            String contentType = bodyPart.getContentType();
            if (isSimpleType(contentType)){
                stringBuffer.append(bodyPart.getContent().toString());
            } else if(isMultipartType(contentType)){
                Multipart multipart2 = (Multipart) bodyPart.getContent();
                loadMultipart(multipart2, stringBuffer);
            } else if(!isTextPlain(contentType)){
                // here we get attachments
                MimeBodyPart mbp = (MimeBodyPart)bodyPart;
                emailMessage.addAttachment(mbp);
            }
        }
    }

    private boolean isTextPlain(String contentType){
        return contentType.contains("TEXT/PLAIN");
    }

    private boolean isSimpleType(String contentType) {
        if (contentType.contains("TEXT/HTML") ||
                contentType.contains("mixed")||
                contentType.contains("text")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isMultipartType(String contentType){
        if (contentType.contains("multipart")) {
            return true;
        } else {
            return false;
        }
    }
}
