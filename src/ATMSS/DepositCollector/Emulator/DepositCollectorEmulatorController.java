package ATMSS.DepositCollector.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import AppKickstarter.timer.Timer;
import com.sun.glass.ui.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

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
    public TextField Total;


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
        if(btn.getText().equals("Done")){
            int one = Integer.parseInt(One.getText());
            int five = Integer.parseInt(Five.getText());
            int ten = Integer.parseInt(Ten.getText());
            int total = 100*one + 500*five + 1000*ten;
            Total.setText(Integer.toString(total));
            depositCollectorMBox.send(new Msg(id, depositCollectorMBox, Msg.Type.DC_Total, one +"/"+ five +"/"+ ten +"/"+ total));
            One.setText("0");
            Five.setText("0");
            Ten.setText("0");
            Total.setText("0");
        }
    }// buttonPressed
}