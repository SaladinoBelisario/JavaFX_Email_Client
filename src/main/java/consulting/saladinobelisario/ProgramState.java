package consulting.saladinobelisario;

import consulting.saladinobelisario.controller.persistence.PersistenceAccess;
import consulting.saladinobelisario.controller.persistence.ValidAccount;
import consulting.saladinobelisario.controller.services.LoginService;
import consulting.saladinobelisario.model.EmailAccount;
import consulting.saladinobelisario.view.ViewFactory;

import java.util.ArrayList;
import java.util.List;

public class ProgramState {

    private PersistenceAccess persistenceAccess;
    private ViewFactory viewFactory;
    private EmailManager emailManager;

    public ProgramState(PersistenceAccess persistenceAccess, ViewFactory viewFactory) {
        this.persistenceAccess = persistenceAccess;
        this.viewFactory = viewFactory;
    }

    public ProgramState() {
        this.persistenceAccess = new PersistenceAccess();
        this.emailManager = new EmailManager();
        this.viewFactory = new ViewFactory(emailManager);
    }

    public void init() {
        this.checkPersistence();
    }

    public void stop(){
        List<ValidAccount> validAccounts = new ArrayList<>();
        for (EmailAccount emailAccount: emailManager.getEmailAccounts()){
            validAccounts.add(new ValidAccount(emailAccount.getAddress(), emailAccount.getPassword()));
        }
        persistenceAccess.saveToPersistence(validAccounts);
    }

    private void checkPersistence() {
        List<ValidAccount> validAccounts = this.persistenceAccess.loadFromPersistence();

        if (validAccounts.size() > 0) {
            showMainScreen();
            loadAccounts(validAccounts);
        } else {
            showLoginWindow();
        }
    }

    private void loadAccounts(List<ValidAccount> validAccounts) {
        for (ValidAccount validAccount: validAccounts){
            EmailAccount emailAccount = new EmailAccount(validAccount.getAddress(), validAccount.getPassword());
            LoginService loginService = new LoginService(emailManager, emailAccount);
            loginService.start();
        }
    }


    private void showLoginWindow() {
        this.viewFactory.showLoginWindow();
    }

    private void showMainScreen() {
        this.viewFactory.showMainWindow();
    }


}
