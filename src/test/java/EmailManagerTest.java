import consulting.saladinobelisario.EmailManager;
import consulting.saladinobelisario.controller.services.ServiceManager;
import consulting.saladinobelisario.model.EmailAccount;
import consulting.saladinobelisario.view.ColorTheme;
import consulting.saladinobelisario.view.FontSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.Mock;

import jakarta.mail.Store;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class EmailManagerTest {

    private EmailManager emailManager;

    @Mock
    private ServiceManager serviceManagerMock;
    @Mock
    private EmailAccount emailAccountMock;
    @Mock
    private Store storeMock;

    @BeforeEach
    public void setUp(){
        initMocks(this);
        emailManager = new EmailManager(serviceManagerMock);
    }

    @Test
    public void testThemes(){
        assertEquals(ColorTheme.DEFAULT, emailManager.getTheme());
        emailManager.setTheme(ColorTheme.DARK);
        assertEquals(ColorTheme.DARK, emailManager.getTheme());
    }
    @Test
    public void testFonts(){
        assertEquals(FontSize.MEDIUM, emailManager.getFontSize());
        emailManager.setFontSize(FontSize.SMALL);
        assertEquals(FontSize.SMALL, emailManager.getFontSize());
    }

    @Test
    public void testFoldersRoot(){
        assertTrue(emailManager.getFoldersRoot().getValue().isEmpty());
    }

    @Test
    public void testAddEmailAccount(){
        when(emailAccountMock.getAddress()).thenReturn("some@address.com");
        when(emailAccountMock.getStore()).thenReturn(storeMock);
        emailManager.addEmailAccount(emailAccountMock);
        assertTrue(emailManager.getFoldersRoot().getChildren().get(0).getValue() == "some@address.com");
        verify(serviceManagerMock).submitFetchFoldersJob(eq(storeMock), any(), any());
    }
}
