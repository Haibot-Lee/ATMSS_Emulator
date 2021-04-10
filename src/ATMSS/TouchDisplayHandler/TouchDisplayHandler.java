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

            case TD_Withdrawal:
                showWithdrawal(msg);
                break;

            case TD_showAccount:
                showAccounts(msg);
                break;

            case TD_InvalidInput:
                showInvalidInput();
                break;

            case TD_TransAmount:
                showAmounts(msg);
                break;

            case TD_ShowResult:
                showResult(msg);
                break;

            case TD_Message_transferFrom:
                changeTransferFrom(msg);
                break;

            case TD_Message_transferTo:
                changeTransferTo(msg);
                break;

            case TD_Message_transferAmount:
                changeTransferAmount(msg);
                break;

            case TD_SaveCash:
                showDepositAccount(msg);
                break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    } // processMsg

    protected void showAccounts(Msg msg) {
    }

    protected void showDepositAccount(Msg msg){}


    protected void handleUpdateDisplay(Msg msg) {
    }

    protected void showPasswords(Msg msg) {
    }

    protected void showAmounts(Msg msg) {
    }

    protected void showResult(Msg msg) {
    }

    protected void showInvalidInput() {
        log.info(id + ": invalid input");
    }

    protected void changeTransferFrom(Msg msg) {
    }

    protected void changeTransferTo(Msg msg) {
    }

    protected void changeTransferAmount(Msg msg) {
    }

    protected void showWithdrawal(Msg msg) {
    }
} // TouchDisplayHandler
