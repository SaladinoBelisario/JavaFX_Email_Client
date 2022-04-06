import consulting.saladinobelisario.controller.persistence.PersistenceAccess;
import consulting.saladinobelisario.ProgramState;
import consulting.saladinobelisario.controller.persistence.ValidAccount;
import consulting.saladinobelisario.view.ViewFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProgramStateTest {

    @Mock
    private PersistenceAccess persistenceAccessMock;
    @Mock
    private ViewFactory viewFactoryMock;

    ProgramState programState;

    @BeforeEach
    public void setUp(){
        initMocks(this);

        programState = new ProgramState(persistenceAccessMock, viewFactoryMock);
    }

    @Test
    public void testValidPersistence(){
        when(persistenceAccessMock.loadFromPersistence()).thenReturn(
                List.of(
                        new ValidAccount("uno", "uno"),
                        new ValidAccount("dos", "dos")
                )
        );
        programState.init();
        verify(viewFactoryMock, times(1)).showMainWindow();
    }

    @Test
    public void testMissingPersistence(){
        when(persistenceAccessMock.loadFromPersistence()).thenReturn(null);
        programState.init();
        verify(viewFactoryMock, times(1)).showLoginWindow();
    }
}
