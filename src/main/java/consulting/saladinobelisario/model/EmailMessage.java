package consulting.saladinobelisario.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import jakarta.mail.Message;
import jakarta.mail.internet.MimeBodyPart;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class EmailMessage {

    private SimpleStringProperty sender;
    private SimpleStringProperty subject;
    private SimpleStringProperty recipient;
    private SimpleObjectProperty<FormatableInteger> size;
    private SimpleObjectProperty<Date> date;
    private List<MimeBodyPart> attachmentList = new ArrayList<MimeBodyPart>();

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isRead() {
        return isRead;
    }

    private boolean isRead;
    private boolean hasAttachments = false;
    private Message message;
    private int hashcode;

    public EmailMessage(String sender, String subject, String recipient, int size, Date date, boolean isRead, Message message) {
        this.sender = new SimpleStringProperty(sender);
        this.subject = new SimpleStringProperty(subject);
        this.recipient = new SimpleStringProperty(recipient);
        this.size = new SimpleObjectProperty<FormatableInteger>(new FormatableInteger(size));
        this.date = new SimpleObjectProperty<Date>(date);
        this.isRead = isRead;
        this.message = message;
        this.computeHashcode(sender, subject, size);
    }

    public boolean hasAttachments(){
        return hasAttachments;
    }

    public String getSender() {
        return sender.get();
    }

    public String getSubject() {
        return subject.get();
    }

    public String getRecipient() {
        return recipient.get();
    }

    public FormatableInteger getSize() {
        return size.get();
    }

    public Date getDate() {
        return date.get();
    }

    public Message getMessage() {
        return message;
    }

    public void addAttachment(MimeBodyPart mimeBodyPart) {
        hasAttachments = true;
        attachmentList.add(mimeBodyPart);
    }
    public void clearAttachments(){
        this.attachmentList.clear();
    }

    public List<MimeBodyPart> getAttachmentList() {
        return attachmentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailMessage that = (EmailMessage) o;
        return isRead == that.isRead &&
                Objects.equals(sender, that.sender) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(recipient, that.recipient) &&
                Objects.equals(size, that.size) &&
                Objects.equals(date, that.date);
    }

    private void computeHashcode(String sender, String subject, int size){
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sender == null) ? 0 : sender.hashCode());
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + size;
        this.hashcode =  result;
    }

    @Override
    public int hashCode(){
        return hashcode;
    }
}
