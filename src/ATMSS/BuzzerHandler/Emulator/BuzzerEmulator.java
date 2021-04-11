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


//======================================================================
// BuzzerEmulator
public class BuzzerEmulator extends BuzzerHandler {
    private ATMSSStarter atmssStarter;
    private String id;
    private Stage myStage;
    private BuzzerEmulatorController buzzerEmulatorController;

    //------------------------------------------------------------
    // BuzzerEmulator
    public BuzzerEmulator(String id, ATMSSStarter atmssStarter) {
        super(id, atmssStarter);
        this.atmssStarter = atmssStarter;
        this.id = id;
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
        buzzerEmulatorController.appendTextArea(msg.getDetails());
        buzzerEmulatorController.updateBuzzerStatus(msg.getDetails());
    } // handleCardInsert

} // BuzzerEmulator
