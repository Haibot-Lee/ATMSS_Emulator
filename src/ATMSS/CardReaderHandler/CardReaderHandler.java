package ATMSS.CardReaderHandler;

import ATMSS.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;


//======================================================================
// CardReaderHandler
public class CardReaderHandler extends HWHandler {
    //------------------------------------------------------------
    // CardReaderHandler
    public CardReaderHandler(String id, AppKickstarter appKickstarter) {
        super(id, appKickstarter);
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
                handleCardEject(msg);
                break;

            case CR_CardRemoved:
                handleCardRemove();
                atmss.send(new Msg(id, mbox, Msg.Type.CR_CardRemoved, msg.getDetails()));
                break;

            case CR_LockCard:
                handleLockCard(msg);
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
    protected void handleCardEject() {
        log.info(id + ": card ejected");
    } // handleCardEject

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

} // CardReaderHandler
