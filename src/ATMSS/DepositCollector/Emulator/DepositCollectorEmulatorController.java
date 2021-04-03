package ATMSS.DepositCollector.Emulator;

import ATMSS.CardReaderHandler.Emulator.CardReaderEmulator;
import ATMSS.KeypadHandler.Emulator.KeypadEmulator;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;

import java.util.logging.Logger;

public class DepositCollectorEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private MBox depositCollectorMBox;


    private DepositCollectorEmulator depositCollectorEmulator;

    public void initialize(String id, AppKickstarter appKickstarter, Logger log, DepositCollectorEmulator depositCollectorEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
        this.log = log;
        this.depositCollectorEmulator = depositCollectorEmulator;
        this.depositCollectorMBox = appKickstarter.getThread("depositCollectorMBox").getMBox();
    } // initialize

    //------------------------------------------------------------
    // buttonPressed
    public void buttonPressed(ActionEvent actionEvent) {

    } // buttonPressed


    //------------------------------------------------------------
    // keyPressed
    public void keyPressed(KeyEvent keyEvent) {

    } // keyPressed
}
