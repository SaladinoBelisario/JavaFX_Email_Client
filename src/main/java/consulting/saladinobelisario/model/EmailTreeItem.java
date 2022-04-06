package consulting.saladinobelisario.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import jakarta.mail.Flags;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class EmailTreeItem<String> extends TreeItem<String> {

    private int unreadMessagesCount;
    private String name;
    private ObservableList<EmailMessage> emails;

    public EmailTreeItem(String name){
        super(name);
        this.name = name;
        this.emails = FXCollections.observableArrayList();
    }

    public ObservableList<EmailMessage> getEmails() {
        return emails;
    }

    public void addEmail(Message message) throws MessagingException {
        EmailMessage emailMessage = fetchEmail(message);
        emails.add(emailMessage);
    }

    public void addEmailToTop(Message message) throws MessagingException {
        EmailMessage emailMessage = fetchEmail(message);
        emails.add(0, emailMessage);
    }

    public void removeMessage(Message message) throws MessagingException {
        for (EmailMessage emailMessage : emails) {
            if(emailMessage.getMessage().equals(message)) {
                emails.remove(emailMessage);
            }
        }
    }

    private EmailMessage fetchEmail(Message message) throws MessagingException {
        boolean messageIsRead = message.getFlags().contains(Flags.Flag.SEEN);
        EmailMessage emailMessage = new EmailMessage(
                message.getFrom()[0].toString(),
                message.getSubject(),
                message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
                message.getSize(),
                message.getSentDate(),
                messageIsRead,
                message
        );
        if(!messageIsRead){
            incrementUnreadMessagesCount();
        }
        return emailMessage;
    }

    public void incrementUnreadMessagesCount(){
        unreadMessagesCount++;
        updateName();
    }

    public void decrementUnreadMessagesCount(){
        unreadMessagesCount --;
        updateName();
    }

    private void updateName(){
        if(unreadMessagesCount > 0) {
            this.setValue((String) (name + "(" + unreadMessagesCount + ")"));
        } else {
            this.setValue(name);
        }
    }
}
