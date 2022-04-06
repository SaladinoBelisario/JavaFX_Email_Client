package consulting.saladinobelisario.controller;

import consulting.saladinobelisario.EmailManager;
import consulting.saladinobelisario.view.ColorTheme;
import consulting.saladinobelisario.view.FontSize;
import consulting.saladinobelisario.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsWindowController extends BaseController implements Initializable {

    @FXML
    private Slider fontSizePicker;

    @FXML
    private ChoiceBox<ColorTheme> themePicker;

    @FXML
    void applyBtnAction() {
        emailManager.setTheme(themePicker.getValue());
        emailManager.setFontSize(FontSize.values()[(int)(fontSizePicker.getValue())]);
        viewFactory.updateStyles();
    }

    @FXML
    void cancelBtnAction() {
        Stage stage = (Stage) fontSizePicker.getScene().getWindow();
        viewFactory.closeStage(stage);
    }

    public OptionsWindowController(ViewFactory viewFactory, EmailManager emailManager, String fxmlName) {
        super(viewFactory, emailManager, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpThemePicker();
        setUpSlider();
    }

    private void setUpThemePicker() {
        themePicker.setItems(FXCollections.observableArrayList(ColorTheme.values()));
        themePicker.setValue(emailManager.getTheme());
    }

    private void setUpSlider() {
        fontSizePicker.setMin(0);
        fontSizePicker.setMax(FontSize.values().length - 1);
        fontSizePicker.setValue(emailManager.getFontSize().ordinal());
        fontSizePicker.setMinorTickCount(0);
        fontSizePicker.setMajorTickUnit(1);
        fontSizePicker.setBlockIncrement(1);
        fontSizePicker.setSnapToTicks(true);
        fontSizePicker.setShowTickMarks(true);
        fontSizePicker.setShowTickLabels(true);
        fontSizePicker.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double aDouble) {
                int i = aDouble.intValue();
                return FontSize.values()[i].toString();
            }
            @Override
            public Double fromString(String s) {
                return null;
            }
        });
        fontSizePicker.valueProperty().addListener((obs, oldVal, newVal) ->
                fontSizePicker.setValue(newVal.intValue()));

    }
}
