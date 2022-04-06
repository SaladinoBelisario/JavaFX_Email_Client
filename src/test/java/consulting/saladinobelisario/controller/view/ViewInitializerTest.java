package consulting.saladinobelisario.controller.view;

import consulting.saladinobelisario.EmailManager;
import consulting.saladinobelisario.controller.BaseController;
import consulting.saladinobelisario.controller.LoginWindowController;
import consulting.saladinobelisario.view.ViewFactory;
import consulting.saladinobelisario.view.ViewInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ViewInitializerTest {

    @Mock
    private EmailManager emailManagerMock;
    @Mock
    private ViewFactory viewFactoryMock;

    private ViewInitializer viewInitializer;

    @BeforeEach
    public void setUp(){
        viewInitializer = new ViewInitializer(emailManagerMock);
    }

    @Test()
    public void testInitializeStage(){
        BaseController loginWindowController = new LoginWindowController(
                viewFactoryMock,
                emailManagerMock,
                "somePath.fxml"
        );
        assertThrows(IllegalStateException.class, ()-> {
            viewInitializer.initializeStage(loginWindowController);
        });


    }
}
