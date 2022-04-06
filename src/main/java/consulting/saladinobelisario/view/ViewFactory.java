package consulting.saladinobelisario.view;

import consulting.saladinobelisario.EmailManager;
import consulting.saladinobelisario.controller.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ViewFactory {

    private EmailManager emailManager;
    private ViewInitializer viewInitializer;
    private ArrayList<Stage> activeStages;
    private boolean mainViewInitialized = false;

    public ViewFactory(EmailManager emailManager) {
        this(emailManager, new ViewInitializer(emailManager));
    }

    public ViewFactory(EmailManager emailManager, ViewInitializer viewInitializer) {
        this.emailManager = emailManager;
        this.viewInitializer = viewInitializer;
        activeStages = new ArrayList<Stage>();
    }


    public void showLoginWindow() {
        BaseController loginWindowController = new LoginWindowController(this, emailManager, "LoginWindow.fxml");
        Stage stage = viewInitializer.initializeStage(loginWindowController);
        activeStages.add(stage);
    }

    public void showMainWindow() {
        BaseController mainWindowController = new MainWindowController(this, emailManager, "MainWindow.fxml");
        Stage stage = viewInitializer.initializeStage(mainWindowController);
        activeStages.add(stage);
        mainViewInitialized = true;
    }

    public void showOptionsWindow() {
        BaseController optionsWindowController = new OptionsWindowController(this, emailManager, "OptionsWindow.fxml");
        Stage stage = viewInitializer.initializeStage(optionsWindowController);
        activeStages.add(stage);
    }

    public void showEmailDetailsWindow(){
        BaseController controller = new EmailDetailsController(this, emailManager, "EmailDetailsWindow.fxml");
        Stage stage = viewInitializer.initializeStage(controller);
        activeStages.add(stage);
    }

    public boolean isMainViewInitialized() {
        return this.mainViewInitialized;
    }

    public void closeStage(Stage stageToClose) {
        activeStages.remove(stageToClose);
        stageToClose.close();
    }

    public void updateStyles() {
        for (Stage stage : activeStages) {
            Scene scene = stage.getScene();
            viewInitializer.applyCurrentStylesToScene(scene);
        }
    }

    public void showComposeWindow() {
        BaseController composeWindowController = new ComposeWindowController(this, emailManager, "ComposeMessageWindow.fxml");
        Stage stage = viewInitializer.initializeStage(composeWindowController);
        activeStages.add(stage);
    }
}


