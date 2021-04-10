package ATMSS.DepositCollector.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import AppKickstarter.timer.Timer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.logging.Logger;

public class DepositCollectorEmulatorController {
    public static Labeled oneHundredTextField;
    public static Labeled fiveHundredTextField;
    public static Labeled oneThousandTextField;
    public static Labeled totalAmountTextField;
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private MBox depositCollectorMBox;
    private DepositCollectorEmulator depositCollectorEmulator;
//    public TextField oneHundredTextField;
//    public TextField fiveHundredTextField;
//    public TextField oneThousandTextField;
//    public TextField totalAmountTextField;


    public void initialize(String id, AppKickstarter appKickstarter, Logger log, DepositCollectorEmulator depositCollectorEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
        this.log = log;
        this.depositCollectorEmulator = depositCollectorEmulator;
        this.depositCollectorMBox = appKickstarter.getThread("DepositCollectorHandler").getMBox();
    } // initialize

    //------------------------------------------------------------
    // buttonPressed
    public void buttonPressed(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();
        if(btn.getText().compareToIgnoreCase("Take money")==0){
            clearArea();
            Timer.cancelTimer(id, depositCollectorMBox,77);
            log.info(id+": Money has been saved");
        }
    }// buttonPressed0

    public void clearArea(){
        oneThousandTextField.setText("");
        fiveHundredTextField.setText("");
        oneHundredTextField.setText("");
        totalAmountTextField.setText("");
    }
}