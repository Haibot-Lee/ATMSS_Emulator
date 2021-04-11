package ATMSS.CardReaderHandler;

import ATMSS.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;
import AppKickstarter.timer.Timer;


//======================================================================
// CardReaderHandler
public class CardReaderHandler extends HWHandler {
    private int waitingTime;
    private int timerID;

    //------------------------------------------------------------
    // CardReaderHandler
    public CardReaderHandler(String id, AppKickstarter appKickstarter) {
        super(id, appKickstarter);
        waitingTime = Integer.parseInt(appKickstarter.getProperty("CardReader.WaitingTime"));
        timerID = Integer.parseInt(appKickstarter.getProperty("CardReader.TimerID"));
    } // CardReaderHandler


    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
        switch (msg.getType()) {
            case CR_CardInserted:
                handleCardInsert();
                atmss.send(new Msg(id, mbox, Msg.Type.CR_CardInserted, msg.getDetails()));
                break;

            case CR_EjectCard:
                Timer.setTimer(id, mbox, waitingTime, timerID);
                atmss.send(new Msg(id, mbox, Msg.Type.CR_CardEjected, msg.getDetails()));
                handleCardEject(msg);
                break;

            case CR_CardRemoved:
                Timer.cancelTimer(id, mbox, timerID);
                handleCardRemove();
                atmss.send(new Msg(id, mbox, Msg.Type.CR_CardRemoved, msg.getDetails()));
                break;

            case CR_LockCard:
                Timer.cancelTimer(id, mbox, timerID);
                handleLockCard(msg);
                break;

            case TimesUp:
                handleOvertime();
                atmss.send(new Msg(id, mbox, Msg.Type.CR_Overtime, "CarReader overtime, retain card!"));
                break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    } // processMsg


    //------------------------------------------------------------
    // handleCardInsert
    protected void handleCardInsert() {
        log.info(id + ": card inserted");
    } // handleCardInsert

    //------------------------------------------------------------
    // handleCardEject
    protected void handleCardEject(Msg msg) {
        log.info(id + ": card ejected");
    } // handleCardEject


    //------------------------------------------------------------
    // handleCardRemove
    protected void handleCardRemove() {
        log.info(id + ": card removed");
    } // handleCardRemove


    //------------------------------------------------------------
    // handleLockCard
    protected void handleLockCard() {
        log.info(id + ": card locked");
    } // handleLockCard


    //------------------------------------------------------------
    // handleLockCard
    protected void handleLockCard(Msg msg) {
        log.info(id + ": card locked");
    } // handleLockCard


    //------------------------------------------------------------
    // handleOvertime
    protected void handleOvertime() {
        log.info(id + ": overtime");
    } // handleOvertime


} // CardReaderHandler
