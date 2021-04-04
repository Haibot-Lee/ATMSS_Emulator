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
// CardReaderEmulatorController
public class PrinterEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private PrinterEmulator printerEmulator;
    private MBox printerMBox;
    public TextArea printerTextArea;

    //public TextField cardNumField;
    //public TextField cardStatusField;




    //------------------------------------------------------------
    // initialize
    public void initialize(String id, AppKickstarter appKickstarter, Logger log, PrinterEmulator printerEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
        this.log = log;
        this.printerEmulator = printerEmulator;
        this.printerMBox = appKickstarter.getThread("PrinterHandler").getMBox();
    } // initialize


    //------------------------------------------------------------
    // buttonPressed
    public void buttonPressed(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();

        switch (btn.getText()) {
            case "Accept Advice":
                //cardReaderMBox.send(new Msg(id, cardReaderMBox, Msg.Type.CR_CardInserted, cardNumField.getText()));
                //cardReaderTextArea.appendText("Sending " + cardNumField.getText() + "\n");
                //cardStatusField.setText("Card Inserted");
                appendTextArea("Advice Accepted.");
                break;

            default:
                log.warning(id + ": unknown button: [" + btn.getText() + "]");
                break;
        }
    } // buttonPressed

    //------------------------------------------------------------
    // appendTextArea
    public void appendTextArea(String status) {
        javafx.application.Platform.runLater( () -> printerTextArea.appendText(status + "\n"));
    } // appendTextArea
} // CardReaderEmulatorController

