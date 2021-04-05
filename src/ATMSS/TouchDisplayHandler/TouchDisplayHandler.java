package ATMSS.TouchDisplayHandler;

import ATMSS.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;


//======================================================================
// TouchDisplayHandler
public class TouchDisplayHandler extends HWHandler {
    //------------------------------------------------------------
    // TouchDisplayHandler
    public TouchDisplayHandler(String id, AppKickstarter appKickstarter) throws Exception {
        super(id, appKickstarter);
    } // TouchDisplayHandler


    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
        switch (msg.getType()) {
            case TD_MouseClicked:
                atmss.send(new Msg(id, mbox, Msg.Type.TD_MouseClicked, msg.getDetails()));
                break;

            case TD_UpdateDisplay:
                handleUpdateDisplay(msg);
                break;

            case TD_Passwords:
                showPasswords(msg);
                break;

            case TD_ShowBalance:
                showBalance(msg);
                break;

            case TD_Message_transferFrom:
                changeTransferFrom(msg);
                break;

            case TD_Message_transferTo:
                changeTransferTo(msg);
                break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    } // processMsg

    protected void handleUpdateDisplay(Msg msg) {
    }

    protected void showPasswords(Msg msg) {
    }

    protected void showBalance(Msg msg) {
    }

    protected void changeTransferFrom(Msg msg){}

    protected void changeTransferTo(Msg msg){}
} // TouchDisplayHandler
