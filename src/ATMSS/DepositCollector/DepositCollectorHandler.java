package ATMSS.DepositCollector;

import ATMSS.DepositCollector.Emulator.DepositCollectorEmulatorController;
import ATMSS.HWHandler.HWHandler;
import ATMSS.ATMSSStarter;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;

import java.awt.*;

public class DepositCollectorHandler extends HWHandler {

    public Button done;

    public DepositCollectorHandler(String id, AppKickstarter appKickstarter) {
        super(id, appKickstarter);
    }

    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
        switch (msg.getType()) {
            case DC_Total:
                atmss.send(new Msg(id, mbox, Msg.Type.DC_Total, msg.getDetails()));
                break;
            case DC_ButtonControl:
                ButtonControl();
                break;
        }
    } // processMsg

    protected void ButtonControl(){

    }
}
