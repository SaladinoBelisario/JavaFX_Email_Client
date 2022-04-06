package consulting.saladinobelisario.controller.view;

import consulting.saladinobelisario.EmailManager;
import consulting.saladinobelisario.controller.BaseController;
import consulting.saladinobelisario.controller.LoginWindowController;
import consulting.saladinobelisario.controller.MainWindowController;
import consulting.saladinobelisario.controller.OptionsWindowController;
import consulting.saladinobelisario.view.ViewFactory;
import consulting.saladinobelisario.view.ViewInitializer;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ViewFactoryTest {

    private ViewFactory viewFactory;

    @Mock
    private ViewInitializer viewInitializerMock;
    @Mock
    private EmailManager emailManagerMock;
    @Mock
    private Stage stageMock;

    @BeforeEach
    public void setUp(){
        initMocks(this);
        viewFactory = new ViewFactory(emailManagerMock, viewInitializerMock);
    }

    @Test
    public void testShowLoginWindow(){
        BaseController loginWindowController = new LoginWindowController(
                viewFactory, emailManagerMock, "LoginWindow.fxml"
        );
        viewFactory.showLoginWindow();
        verify(viewInitializerMock).initializeStage(loginWindowController);
        assertFalse(viewFactory.isMainViewInitialized());
    }

    @Test
    public void testShowMainLoginWindow(){
        BaseController mainWindowController = new MainWindowController(
                viewFactory, emailManagerMock, "MainWindow.fxml"
        );
        viewFactory.showMainWindow();
        verify(viewInitializerMock).initializeStage(mainWindowController);
        assertTrue(viewFactory.isMainViewInitialized());
    }

    @Test
    public void testShowOptionsWindow(){
        BaseController optionsWindowController = new OptionsWindowController(
                viewFactory, emailManagerMock, "OptionsWindow.fxml"
        );
        viewFactory.showOptionsWindow();
        verify(viewInitializerMock).initializeStage(optionsWindowController);
    }

    @Test
    public void testCloseStage(){
        viewFactory.closeStage(stageMock);
        verify(stageMock).close();
    }
}
