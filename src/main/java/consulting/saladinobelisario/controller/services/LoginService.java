package consulting.saladinobelisario.controller.services;

import consulting.saladinobelisario.EmailManager;
import consulting.saladinobelisario.controller.EmailLoginResult;
import consulting.saladinobelisario.model.EmailAccount;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import jakarta.mail.*;

public class LoginService extends Service<EmailLoginResult> {

    private EmailManager emailManager;
    private EmailAccount emailAccount;

    public LoginService(EmailManager emailManager, EmailAccount emailAccount) {
        this.emailAccount = emailAccount;
        this.emailManager = emailManager;
    }

    public void setEmailAccount(EmailAccount emailAccount){
        this.emailAccount = emailAccount;
    }

    @Override
    protected Task<EmailLoginResult> createTask() {
        return new Task<EmailLoginResult>() {
            @Override
            protected EmailLoginResult call() throws Exception {
                return login();
            }
        };
    }

    private EmailLoginResult login() {
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAccount.getAddress(), emailAccount.getPassword());
            }
        };

        try {
            Session session = Session.getInstance(emailAccount.getProperties(), auth);
            emailAccount.setSession(session);
            Store store = session.getStore("imaps");
            store.connect(emailAccount.getProperties().getProperty("incomingHost"),
                    emailAccount.getAddress(),
                    emailAccount.getPassword());
            emailAccount.setStore(store);
            emailManager.addEmailAccount(emailAccount);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_NETWORK;
        } catch (AuthenticationFailedException e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_CREDENTIALS;
        } catch (Exception e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_UNEXPECTED_ERROR;
        }
        return EmailLoginResult.SUCCESS;
    }
}
