package ATMSS.DepositCollector.Emulator;

import ATMSS.ATMSSEmulatorStarter;
import ATMSS.ATMSSStarter;
import ATMSS.CardReaderHandler.Emulator.CardReaderEmulator;
import ATMSS.CardReaderHandler.Emulator.CardReaderEmulatorController;
import ATMSS.DepositCollector.DepositCollectorHandler;
import ATMSS.KeypadHandler.Emulator.KeypadEmulatorController;
import ATMSS.TouchDisplayHandler.Emulator.TouchDisplayEmulator;
import ATMSS.TouchDisplayHandler.Emulator.TouchDisplayEmulatorController;
import ATMSS.TouchDisplayHandler.TouchDisplayHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.util.logging.Logger;

public class DepositCollectorEmulator extends DepositCollectorHandler {
    private ATMSSStarter atmssStarter;
    private String id;
    private Stage myStage;
    private DepositCollectorEmulatorController depositCollectorEmulatorController;


    public DepositCollectorEmulator(String id, ATMSSStarter atmssStarter) throws Exception{
        super(id, atmssStarter);
        this.atmssStarter = atmssStarter;
        this.id = id;
    }//depositCollectorEmulator

    public void start() throws Exception {
        Parent root;
        myStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        String fxmlName = "DepositCollectorEmulator.fxml";
        loader.setLocation(DepositCollectorEmulator.class.getResource(fxmlName));
        root = loader.load();
        depositCollectorEmulatorController = (DepositCollectorEmulatorController) loader.getController();
        depositCollectorEmulatorController.initialize(id, atmssStarter, log, this);
        myStage.initStyle(StageStyle.DECORATED);
        myStage.setScene(new Scene(root, 350, 470));
        myStage.setTitle("Deposit Collector");
        myStage.setResizable(false);
        myStage.setOnCloseRequest((WindowEvent event) -> {
            atmssStarter.stopApp();
            Platform.exit();
        });
        myStage.show();
    } // depositCollectorEmulator
}