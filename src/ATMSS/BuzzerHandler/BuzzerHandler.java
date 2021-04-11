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
import java.net.MalformedURLException;


//======================================================================
// BuzzerHandler
public class BuzzerHandler extends HWHandler {
    File file;
    AudioClip audioClip = null;

    //------------------------------------------------------------
    // BuzzerHandler
    public BuzzerHandler(String id, AppKickstarter appKickstarter) {
        super(id, appKickstarter);
        file = new File(appKickstarter.getProperty("Buzzer.Music"));
        try {
            audioClip = Applet.newAudioClip(file.toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    } // BuzzerHandler


    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
        switch (msg.getType()) {

            case B_Alert:
                handleAlert(msg);

                System.out.println("Try to play music");

                audioClip.play();

                System.out.println("Playing music now");


                break;

            case B_Stop:
                handleStop();
                try {
                    System.out.println("Stop playing music");

                    audioClip.stop();

                    System.out.println("Stop playing music now");

                } catch (Exception e) {

                }

                //atmss.send(new Msg(id, mbox, Msg.Type.CR_CardInserted, msg.getDetails()));
                break;


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


} // BuzzerHandler
