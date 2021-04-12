package ATMSS.BuzzerHandler.Emulator;

import ATMSS.ATMSSStarter;
import ATMSS.BuzzerHandler.BuzzerHandler;

import AppKickstarter.misc.Msg;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;


//======================================================================
// BuzzerEmulator
public class BuzzerEmulator extends BuzzerHandler {
    private ATMSSStarter atmssStarter;
    private String id;
    private Stage myStage;
    private BuzzerEmulatorController buzzerEmulatorController;

    File file;
    AudioClip audioClip = null;

    //------------------------------------------------------------
    // BuzzerEmulator
    public BuzzerEmulator(String id, ATMSSStarter atmssStarter) {
        super(id, atmssStarter);
        this.atmssStarter = atmssStarter;
        this.id = id;

        file = new File(appKickstarter.getProperty("Buzzer.Music"));
        try {
            audioClip = Applet.newAudioClip(file.toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    } // BuzzerEmulator


    //------------------------------------------------------------
    // start
    public void start() throws Exception {
        Parent root;
        myStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        String fxmlName = "BuzzerEmulator.fxml";
        loader.setLocation(BuzzerEmulator.class.getResource(fxmlName));
        root = loader.load();
        buzzerEmulatorController = (BuzzerEmulatorController) loader.getController();
        buzzerEmulatorController.initialize(id, atmssStarter, log, this);
        myStage.initStyle(StageStyle.DECORATED);
        myStage.setScene(new Scene(root, 350, 470));
        myStage.setTitle("Buzzer");
        myStage.setResizable(false);
        myStage.setOnCloseRequest((WindowEvent event) -> {
            atmssStarter.stopApp();
            Platform.exit();
        });
        myStage.show();
    } // BuzzerEmulator


    //------------------------------------------------------------
    // handleCardInsert
    protected void handleAlert(Msg msg) {
        super.handleAlert(msg);
        audioClip.loop();
        buzzerEmulatorController.appendTextArea(msg.getDetails());
        buzzerEmulatorController.updateBuzzerStatus(msg.getDetails());
    } // handleCardInsert

    protected void handleStop(Msg msg) {
        super.handleStop(msg);
        audioClip.stop();
        buzzerEmulatorController.appendTextArea(msg.getDetails());
        buzzerEmulatorController.updateBuzzerStatus(msg.getDetails());
    } // handleStop
} // BuzzerEmulator
