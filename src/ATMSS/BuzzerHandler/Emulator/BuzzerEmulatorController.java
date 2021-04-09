package ATMSS.BuzzerHandler.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import javafx.event.ActionEvent;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.logging.Logger;


//======================================================================
// BuzzerEmulatorController
public class BuzzerEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private BuzzerEmulator buzzerEmulator;
    private MBox buzzerMBox;

    public TextField buzzerStatusField;
    public TextArea buzzerTextArea;

    //------------------------------------------------------------
    // initialize
    public void initialize(String id, AppKickstarter appKickstarter, Logger log, BuzzerEmulator buzzerEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
        this.log = log;
        this.buzzerEmulator = buzzerEmulator;
        this.buzzerMBox = appKickstarter.getThread("BuzzerHandler").getMBox();

    } // initialize

    //------------------------------------------------------------
    // updateBuzzerStatus
    public void updateBuzzerStatus(String status) {
        javafx.application.Platform.runLater( () -> buzzerStatusField.setText(status));
    } // updateBuzzerStatus

    //------------------------------------------------------------
    // appendTextArea
    public void appendTextArea(String status) {
        javafx.application.Platform.runLater( () -> buzzerTextArea.appendText(status + "\n"));
    } // appendTextArea


} // BuzzerEmulatorController
