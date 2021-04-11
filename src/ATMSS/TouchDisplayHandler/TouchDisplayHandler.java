package ATMSS.TouchDisplayHandler;

import ATMSS.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;
import AppKickstarter.timer.Timer;


//======================================================================
// TouchDisplayHandler
public class TouchDisplayHandler extends HWHandler {
    private int waitingTime;
    private int timerID;
    private boolean timerOn = false;


    //------------------------------------------------------------
    // TouchDisplayHandler
    public TouchDisplayHandler(String id, AppKickstarter appKickstarter) throws Exception {
        super(id, appKickstarter);
        waitingTime = Integer.parseInt(appKickstarter.getProperty("TouchDisplay.WaitingTime"));
        timerID = Integer.parseInt(appKickstarter.getProperty("TouchDisplay.TimerID"));

    } // TouchDisplayHandler


    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
        switch (msg.getType()) {
            case TD_MouseClicked:
                atmss.send(new Msg(id, mbox, Msg.Type.TD_MouseClicked, msg.getDetails()));
                resetTimer();
                break;

            case TD_resetTimer:
                resetTimer();
                break;

            case TD_UpdateDisplay:
                if (msg.getDetails().equals("Eject") || msg.getDetails().equals("Locked")) {
                    if (timerOn) {
                        Timer.cancelTimer(id, mbox, timerID);
                        timerOn = false;
                        log.info(id + ": TouchDisplay timer freeze");
                    }
                }
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
                showInvalidInput(msg);
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

            case TD_Deposit:
                showDepositAccount(msg);
                break;

            case TimesUp:
                System.out.println("TouchDisplay accept input overtime.");
                atmss.send(new Msg(id, mbox, Msg.Type.TD_Overtime, msg.getDetails()));
                break;

            case TD_AcceptInput:
                Timer.setTimer(id, mbox, waitingTime, timerID);
                timerOn = true;
                System.out.println("TouchDisplay timer is counting down");
                break;

            case TD_UpdateDepositDetails:
                dealDetails(msg);
                break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    } // processMsg

    protected void dealDetails(Msg msg) {

    }

    protected void showAccounts(Msg msg) {
    }

    protected void showDepositAccount(Msg msg) {
    }

    protected void handleUpdateDisplay(Msg msg) {
    }

    protected void showPasswords(Msg msg) {
    }

    protected void showAmounts(Msg msg) {
    }

    protected void showResult(Msg msg) {
    }

    protected void showInvalidInput(Msg msg) {
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

    private void resetTimer() {
        if (timerOn) {
            Timer.cancelTimer(id, mbox, timerID);
            Timer.setTimer(id, mbox, waitingTime, timerID);
            log.info(id + ": TouchDisplay timer is recounting down");
        }
    }

} // TouchDisplayHandler
