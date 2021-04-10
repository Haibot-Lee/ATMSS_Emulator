package ATMSS.BuzzerHandler;

import ATMSS.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.Msg;
import AppKickstarter.timer.Timer;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;


//======================================================================
// BuzzerHandler
public class BuzzerHandler extends HWHandler {
    String filename;
    File file;
    AudioClip audioClip = null;

    //------------------------------------------------------------
    // BuzzerHandler
    public BuzzerHandler(String id, AppKickstarter appKickstarter) {
        super(id, appKickstarter);
        filename = appKickstarter.getProperty("Buzzer.Music");
        file = new File(filename);
        audioClip = null;
    } // BuzzerHandler


    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
        switch (msg.getType()) {

            case B_Alert:
                handleAlert(msg);
                try {
                    System.out.println("Try to play music");

                    audioClip = Applet.newAudioClip(file.toURL());
                    audioClip.play();
                    Thread.sleep(3000);
                    audioClip.stop();

                    System.out.println("Playing music now");

                }catch (Exception e) {

                }

                break;

            case B_Stop:
                handleStop();
                try {
                    System.out.println("Stop playing music");

                    audioClip.stop();

                    System.out.println("Stop playing music now");

                }catch (Exception e) {

                }

                //atmss.send(new Msg(id, mbox, Msg.Type.CR_CardInserted, msg.getDetails()));
                break;
/*
            case CR_CardInserted:
                handleCardInsert();
                atmss.send(new Msg(id, mbox, Msg.Type.CR_CardInserted, msg.getDetails()));
                break;
                */
                /*
            case CR_CardInserted:
                handleCardInsert();
                atmss.send(new Msg(id, mbox, Msg.Type.CR_CardInserted, msg.getDetails()));
                break;

            case CR_EjectCard:
                Timer.setTimer(id, mbox, waitingTime, timerID);
                //System.out.println("CardReader timer is counting down");
                handleCardEject(msg);
                break;

            case CR_CardRemoved:
                Timer.cancelTimer(id, mbox, timerID);
                //System.out.println("CardReader timer is canceled.");
                handleCardRemove();
                atmss.send(new Msg(id, mbox, Msg.Type.CR_CardRemoved, msg.getDetails()));
                break;

            case CR_LockCard:
                Timer.cancelTimer(id, mbox, timerID);
                //System.out.println("CardReader timer is canceled.");
                handleLockCard(msg);
                break;

            case TimesUp:
                //System.out.println("Eject card overtime.");
                handleOvertime();
                //atmss.send(new Msg(id, mbox, Msg.Type.CR_Overtime, msg.getDetails()));
                break;
*/
            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    } // processMsg

    //------------------------------------------------------------
    // handleAlert
    protected void handleAlert(Msg msg) {
        log.info(id + ": alert");
    } // handleAlert

    //------------------------------------------------------------
    // handleStop
    protected void handleStop() {
        log.info(id + ": stop alert");
    } // handleStop

    /*
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


    //------------------------------------------------------------
    // handleOvertime
    protected void handleOvertime() {
        log.info(id + ": overtime");
    } // handleOvertime
*/

} // BuzzerHandler
