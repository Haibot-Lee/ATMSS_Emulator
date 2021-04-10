package ATMSS.CashDispenserHandler.Emulator;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;

import java.util.logging.Logger;

import AppKickstarter.timer.Timer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CashDispenserEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private CashDispenserEmulator cashDispenserEmulator;
    private MBox cashDispenserMBox;
    public TextField oneHundredTextField;
    public TextField fiveHundredTextField;
    public TextField oneThousandTextField;
    public TextField totalAmountTextField;

    public void initialize(String id, AppKickstarter appKickstarter, Logger log, CashDispenserEmulator cashDispenserEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
        this.log = log;
        this.cashDispenserEmulator = cashDispenserEmulator;
        this.cashDispenserMBox = appKickstarter.getThread("CashDispenserHandler").getMBox();
    } // initialize
    public void buttonPressed(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();
        if(btn.getText().compareToIgnoreCase("Take money")==0){
            clearArea();
            Timer.cancelTimer(id, cashDispenserMBox,77);
            log.info(id+": Money has been taken");
        }
    }
    public void clearArea(){
        oneThousandTextField.setText("");
        fiveHundredTextField.setText("");
        oneHundredTextField.setText("");
        totalAmountTextField.setText("");
    }
}
