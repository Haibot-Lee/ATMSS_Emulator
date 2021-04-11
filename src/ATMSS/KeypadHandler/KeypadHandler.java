package ATMSS.KeypadHandler;

import ATMSS.HWHandler.HWHandler;
import ATMSS.ATMSSStarter;
import AppKickstarter.misc.*;
import AppKickstarter.timer.Timer;


//======================================================================
// KeypadHandler
public class KeypadHandler extends HWHandler {
    private int waitingTime;
    private int timerID;
    private boolean timerOn = false;

    //------------------------------------------------------------
    // KeypadHandler
    public KeypadHandler(String id, ATMSSStarter atmssStarter) {
        super(id, atmssStarter);
        waitingTime = Integer.parseInt(appKickstarter.getProperty("Keypad.WaitingTime"));
        timerID = Integer.parseInt(appKickstarter.getProperty("Keypad.TimerID"));
    } // KeypadHandler


    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
        switch (msg.getType()) {
            case KP_AcceptPassword:
                Timer.setTimer(id, mbox, waitingTime, timerID);
                timerOn = true;
                System.out.println("Keypad timer is counting down");
                break;

            case KP_KeyPressed:
                atmss.send(new Msg(id, mbox, Msg.Type.KP_KeyPressed, msg.getDetails()));
                if (timerOn) {
                    Timer.cancelTimer(id, mbox, timerID);
                    Timer.setTimer(id, mbox, waitingTime, timerID);
                    log.info(id + ": Keypad timer is recounting down");
                }

                break;

            case KP_PushUp:
                handlePushUp(msg);
                break;

            case TimesUp:
                System.out.println("Keypad accept password overtime.");
                atmss.send(new Msg(id, mbox, Msg.Type.KP_Overtime, msg.getDetails()));
                break;

            case KP_Freeze:
                if (timerOn) {
                    Timer.cancelTimer(id, mbox, timerID);
                    timerOn = false;
                    log.info(id + ": Keypad timer freeze");
                }
                break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    } // processMsg

    //------------------------------------------------------------
    // handlePushUp
    protected void handlePushUp(Msg msg) {
    } // handlePushUp
} // KeypadHandler
