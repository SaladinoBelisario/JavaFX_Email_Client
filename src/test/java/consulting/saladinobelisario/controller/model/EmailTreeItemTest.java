package consulting.saladinobelisario.controller.model;

import consulting.saladinobelisario.model.EmailMessage;
import consulting.saladinobelisario.model.EmailTreeItem;
import consulting.saladinobelisario.model.FormatableInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import jakarta.mail.Address;
import jakarta.mail.Flags;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.Date;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailTreeItemTest {

    private EmailTreeItem<String> emailTreeItem;
    @Mock
    private Message messageMock;
    @Mock
    private Flags flagsMock;
    @Mock
    private Address fromAddressMock;
    @Mock
    private Address toAddressMock;
    @Mock
    private Date sendDateMock;

    @BeforeEach
    public void setUp() throws MessagingException {
        initMocks(this);
        emailTreeItem = new EmailTreeItem<String>("Inbox");
        when(messageMock.getFlags()).thenReturn(flagsMock);
        when(flagsMock.contains(Flags.Flag.SEEN)).thenReturn(true);


        when(messageMock.getSubject()).thenReturn("someSubject");

        when(fromAddressMock.toString()).thenReturn("some@from.com");
        Address[] fromAddressArray = {fromAddressMock};
        when(messageMock.getFrom()).thenReturn(fromAddressArray);

        when(toAddressMock.toString()).thenReturn("some@recipient.com");
        Address[] toAddressArray = {toAddressMock};
        when(messageMock.getRecipients(MimeMessage.RecipientType.TO)).thenReturn(toAddressArray);

        when(messageMock.getSize()).thenReturn(1000);
        when(messageMock.getSentDate()).thenReturn(sendDateMock);
    }

    @Test
    public void testAddEmail() throws MessagingException {
        emailTreeItem.addEmail(messageMock);
        EmailMessage emailMessage = emailTreeItem.getEmails().get(0);
        assertEquals(emailMessage.getSubject(), "someSubject");
        assertEquals(emailMessage.getSender(), "some@from.com");
        assertEquals(emailMessage.getRecipient(), "some@recipient.com");
        assertEquals(emailMessage.getSize(), new FormatableInteger(1000));
        assertEquals(emailMessage.getDate(), sendDateMock);
        assertEquals(emailMessage.getMessage(), messageMock);

        assertEquals(emailTreeItem.getEmails().size(), 1);
        assertEquals(emailTreeItem.getValue(), "Inbox");
    }

    @Test
    public void testAddUnreadEmail() throws MessagingException {
        when(flagsMock.contains(Flags.Flag.SEEN)).thenReturn(false);
        emailTreeItem.addEmail(messageMock);
        EmailMessage emailMessage = emailTreeItem.getEmails().get(0);
        assertEquals(emailMessage.isRead(), false);

        assertEquals(emailTreeItem.getValue(), "Inbox(1)");
        emailTreeItem.decrementUnreadMessagesCount();
        assertEquals(emailTreeItem.getValue(), "Inbox");
    }
}
