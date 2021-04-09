package ATMSS.KeypadHandler;

import ATMSS.HWHandler.HWHandler;
import ATMSS.ATMSSStarter;
import AppKickstarter.misc.*;
import AppKickstarter.timer.Timer;


//======================================================================
// KeypadHandler
public class KeypadHandler extends HWHandler {
    int waitingTime = 30000;
    int timerID = 114514;

    //------------------------------------------------------------
    // KeypadHandler
    public KeypadHandler(String id, ATMSSStarter atmssStarter) {
        super(id, atmssStarter);
    } // KeypadHandler


    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
        switch (msg.getType()) {
            case KP_AcceptPassword:
                Timer.setTimer(id, mbox, waitingTime, timerID);
                //System.out.println("Keypad timer is counting down");
                break;

            case KP_KeyPressed:
                Timer.cancelTimer(id, mbox, timerID);
                //System.out.println("Keypad timer is canceled.");
                atmss.send(new Msg(id, mbox, Msg.Type.KP_KeyPressed, msg.getDetails()));
                Timer.setTimer(id, mbox, waitingTime, timerID);
                //System.out.println("Keypad timer is recounting down");
                break;

            case KP_PushUp:
                handlePushUp(msg);
                break;

            case TimesUp:
                //System.out.println("Keypad accept password overtime.");
                atmss.send(new Msg(id, mbox, Msg.Type.KP_Overtime, msg.getDetails()));
                break;

            case KP_Freeze:
                Timer.cancelTimer(id, mbox, timerID);
                //System.out.println("Keypad timer freeze.");
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
