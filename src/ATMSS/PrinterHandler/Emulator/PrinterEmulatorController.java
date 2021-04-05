package ATMSS.PrinterHandler.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


//======================================================================
// PrinterEmulatorController
public class PrinterEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private PrinterEmulator printerEmulator;
    private MBox printerMBox;

    public TextArea printerTextArea;
    public TextField printerTextField;
    public Button printerButton;


    //------------------------------------------------------------
    // initialize
    public void initialize(String id, AppKickstarter appKickstarter, Logger log, PrinterEmulator printerEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
        this.log = log;
        this.printerEmulator = printerEmulator;
        this.printerMBox = appKickstarter.getThread("PrinterHandler").getMBox();
        printerButton.setDisable(true);
    } // initialize


    //------------------------------------------------------------
    // buttonPressed
    public void buttonPressed(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();

        switch (btn.getText()) {
            case "Take Advice":
                setTextArea("");
                setTextField("Advice Taken");
                printerButton.setDisable(true);
                break;

            default:
                log.warning(id + ": unknown button: [" + btn.getText() + "]");
                break;
        }
    } // buttonPressed

    //------------------------------------------------------------
    // setTextArea
    public void setTextArea(String status) {
        javafx.application.Platform.runLater( () -> printerTextArea.setText(status));
    } // setTextArea

    // setTextField
    public void setTextField(String status) {
        javafx.application.Platform.runLater( () -> printerTextField.setText(status));
    } // setTextField

    public void reset() {
        printerTextArea.setText("");
        printerTextField.setText("");
        printerButton.setDisable(true);
    }
} // CardReaderEmulatorController

