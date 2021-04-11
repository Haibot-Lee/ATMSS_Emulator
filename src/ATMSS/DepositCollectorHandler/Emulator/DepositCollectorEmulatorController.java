package ATMSS.DepositCollectorHandler.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.logging.Logger;

public class DepositCollectorEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private MBox depositCollectorMBox;
    private DepositCollectorEmulator depositCollectorEmulator;
    public TextField One;
    public TextField Five;
    public TextField Ten;
    public Button Done;


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
        if (btn.getText().equals("Done")) {
            try {
                //get info from the Deposit Collector
                int one = Integer.parseInt(One.getText());
                int five = Integer.parseInt(Five.getText());
                int ten = Integer.parseInt(Ten.getText());
                //calculate the total cash Deposited
                int total = 100 * one + 500 * five + 1000 * ten;
                //send the deposited money to the deposit collector handler
                depositCollectorMBox.send(new Msg(id, depositCollectorMBox, Msg.Type.DC_Total, one + "/" + five + "/" + ten + "/" + total));
                //reset the textFile
                One.setText("0");
                Five.setText("0");
                Ten.setText("0");
                Done.setDisable(true);
            } catch (Exception e) {
                //check if the input is invalid
                depositCollectorMBox.send(new Msg(id, depositCollectorMBox, Msg.Type.DC_Total, "Invalid"));
            }


        }
    }// buttonPressed
}