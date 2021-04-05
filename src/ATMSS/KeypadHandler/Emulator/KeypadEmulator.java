package ATMSS.KeypadHandler.Emulator;

import ATMSS.ATMSSStarter;
import ATMSS.KeypadHandler.KeypadHandler;

import ATMSS.TouchDisplayHandler.Emulator.TouchDisplayEmulator;
import ATMSS.TouchDisplayHandler.Emulator.TouchDisplayEmulatorController;
import AppKickstarter.misc.Msg;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


//======================================================================
// KeypadEmulator
public class KeypadEmulator extends KeypadHandler {
    private final int WIDTH = 340;
    private final int HEIGHT = 270;

    private ATMSSStarter atmssStarter;
    private String id;
    private Stage myStage;
    private KeypadEmulatorController keypadEmulatorController;

    //------------------------------------------------------------
    // KeypadEmulator
    public KeypadEmulator(String id, ATMSSStarter atmssStarter) {
        super(id, atmssStarter);
        this.atmssStarter = atmssStarter;
        this.id = id;
    } // KeypadEmulator


    //------------------------------------------------------------
    // start
    public void start() throws Exception {
        Parent root;
        myStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        String fxmlName = "KeypadEmulator.fxml";
        loader.setLocation(KeypadEmulator.class.getResource(fxmlName));
        root = loader.load();
        keypadEmulatorController = (KeypadEmulatorController) loader.getController();
        keypadEmulatorController.initialize(id, atmssStarter, log, this);
        myStage.initStyle(StageStyle.DECORATED);
        myStage.setScene(new Scene(root, WIDTH, HEIGHT));
        myStage.setTitle("KeypadHandler");
        myStage.setResizable(false);
        myStage.setOnCloseRequest((WindowEvent event) -> {
            atmssStarter.stopApp();
            Platform.exit();
        });
        myStage.show();
    } // KeypadEmulator

    //------------------------------------------------------------
    // handlePushUp
    protected void handlePushUp(Msg msg) {
        log.info(id + ": keypad pushed up");
        reloadStage("KeypadEmulator.fxml");
    } // handlePushUp

    //------------------------------------------------------------
    // reloadStage
    private void reloadStage(String fxmlFName) {
        KeypadEmulator keypadEmulator = this;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info(id + ": loading fxml: " + fxmlFName);

                    Parent root;
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(KeypadEmulator.class.getResource(fxmlFName));
                    root = loader.load();
                    keypadEmulatorController = (KeypadEmulatorController) loader.getController();
                    keypadEmulatorController.initialize(id, atmssStarter, log, keypadEmulator);
                    myStage.setScene(new Scene(root, WIDTH, HEIGHT));
                } catch (Exception e) {
                    log.severe(id + ": failed to load " + fxmlFName);
                    e.printStackTrace();
                }
            }
        });
    } // reloadStage

} // KeypadEmulator
