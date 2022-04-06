package consulting.saladinobelisario.controller.model;

import consulting.saladinobelisario.model.EmailMessage;
import consulting.saladinobelisario.model.FormatableInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.MockitoAnnotations.initMocks;

import jakarta.mail.Message;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailMessageTest {

    private EmailMessage emailMessage;
    private String sender = "some@sender.com";
    private String subject = "some Subject";
    private String recipient = "some@recipient.com";
    private int size = 1000;
    @Mock
    private Date date;
    private boolean isRead = true;
    @Mock
    private Message messageMock;


    @BeforeEach
    public void setUp(){
        initMocks(this);
        emailMessage = new EmailMessage(
                sender,
                subject,
                recipient,
                size,
                date,
                isRead,
                messageMock
        );
    }

    @Test
    public void testFields(){
        assertEquals(emailMessage.getSender(), sender);
        assertEquals(emailMessage.getSubject(), subject);
        assertEquals(emailMessage.getRecipient(), recipient);
        assertEquals(emailMessage.getSize(), new FormatableInteger(size));
        assertEquals(emailMessage.getDate(), date);
        assertEquals(emailMessage.isRead(), isRead);
        assertEquals(emailMessage.getMessage(), messageMock);
    }
    @Test
    public void testSetRead(){
        emailMessage.setRead(false);
        assertEquals(emailMessage.isRead(), false);
    }
}
