package consulting.saladinobelisario.controller.model;

import consulting.saladinobelisario.model.EmailAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import jakarta.mail.Store;

import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailAccountTest {

    private EmailAccount emailAccount;
    private String someAddress = "some@address.com";
    private String somePassword = "somePassword";
    private Properties props;
    @Mock
    private Store storeMock;

    @BeforeEach
    public void setUp(){
        initMocks(this);
        props = new Properties();
        emailAccount = new EmailAccount(someAddress, somePassword);
    }
    @Test
    public void testFields(){
        assertEquals(emailAccount.getAddress(), someAddress);
        assertEquals(emailAccount.getPassword(), somePassword);
    }
    @Test
    public void testProperties(){
        emailAccount.setProperties(props);
        assertEquals(emailAccount.getProperties(), props);
        emailAccount.setStore(storeMock);
        assertEquals(emailAccount.getStore(), storeMock);
    }
}
