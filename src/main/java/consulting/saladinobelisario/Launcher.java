package consulting.saladinobelisario;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {
    ProgramState programState = new ProgramState();

    @Override
    public void start(Stage stage){
        programState.init();
    }

    @Override
    public void stop(){
        programState.stop();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
