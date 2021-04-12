package ATMSS.DepositCollectorHandler;

import ATMSS.HWHandler.HWHandler;
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
                //send the cash details info to teh ATMSS
                atmss.send(new Msg(id, mbox, Msg.Type.DC_Total, msg.getDetails()));
                break;
                //get the command to change the status of "Done" button
            case DC_ButtonControl:
                ButtonControl();
                break;
        }
    } // processMsg

    protected void ButtonControl(){

    }
}