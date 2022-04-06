package consulting.saladinobelisario.view;

import consulting.saladinobelisario.EmailManager;
import consulting.saladinobelisario.controller.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewInitializer {

    private EmailManager emailManager;

    public ViewInitializer(EmailManager emailManager){
        this.emailManager = emailManager;
    }

    public Stage initializeStage(BaseController baseController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Scene scene = new Scene(parent);
        applyCurrentStylesToScene(scene);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        return stage;
    }

    public void applyCurrentStylesToScene(Scene scene){
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(emailManager.getTheme())).toExternalForm());
        scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(emailManager.getFontSize())).toExternalForm());
    }
}

