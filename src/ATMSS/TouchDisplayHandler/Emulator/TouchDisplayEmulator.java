package ATMSS.TouchDisplayHandler.Emulator;

import ATMSS.ATMSSStarter;
import ATMSS.TouchDisplayHandler.TouchDisplayHandler;
import AppKickstarter.misc.Msg;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


//======================================================================
// TouchDisplayEmulator
public class TouchDisplayEmulator extends TouchDisplayHandler {
    private final int WIDTH = 680;
    private final int HEIGHT = 520;
    private ATMSSStarter atmssStarter;
    private String id;
    private Stage myStage;
    private TouchDisplayEmulatorController touchDisplayEmulatorController;

    //------------------------------------------------------------
    // TouchDisplayEmulator
    public TouchDisplayEmulator(String id, ATMSSStarter atmssStarter) throws Exception {
        super(id, atmssStarter);
        this.atmssStarter = atmssStarter;
        this.id = id;
    } // TouchDisplayEmulator


    //------------------------------------------------------------
    // start
    public void start() throws Exception {
        Parent root;
        myStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        String fxmlName = "TouchDisplayEmulatorStart.fxml";
        loader.setLocation(TouchDisplayEmulator.class.getResource(fxmlName));
        root = loader.load();
        touchDisplayEmulatorController = (TouchDisplayEmulatorController) loader.getController();
        touchDisplayEmulatorController.initialize(id, atmssStarter, log, this);
        myStage.initStyle(StageStyle.DECORATED);
        myStage.setScene(new Scene(root, WIDTH, HEIGHT));
        myStage.setTitle("Touch Display");
        myStage.setResizable(false);
        myStage.setOnCloseRequest((WindowEvent event) -> {
            atmssStarter.stopApp();
            Platform.exit();
        });
        myStage.show();
    } // TouchDisplayEmulator


    //------------------------------------------------------------
    // handleUpdateDisplay
    protected void handleUpdateDisplay(Msg msg) {

        log.info(id + ": update display -- " + msg.getDetails());

        switch (msg.getDetails()) {
            case "Start":
                reloadStage("TouchDisplayEmulatorStart.fxml");
                break;

            case "Password":
                reloadStage("TouchDisplayPinInput.fxml");
                break;

            case "wrongPassword":
                touchDisplayEmulatorController.setPasswordMsg("Wrong password! Please input again:");
                break;

            case "Eject":
                reloadStage("TouchDisplayEmulatorEject.fxml");
                break;

            case "MainMenu":
                reloadStage("TouchDisplayMainMenu.fxml");
                break;

            case "moneyTrans":
                reloadStage("TouchDisplayMoneyTrans.fxml");
                break;

            default:
                log.severe(id + ": update display with unknown display type -- " + msg.getDetails());
                break;
        }
    } // handleUpdateDisplay

    protected void showPasswords(Msg msg) {
        log.info(id + ": show passwords");

        if (msg.getDetails().compareToIgnoreCase("Clear") == 0) {
            touchDisplayEmulatorController.passwordField.setText("");
        } else {
            touchDisplayEmulatorController.appendPassword(msg.getDetails());
        }
    }

    protected void showAmounts(Msg msg) {
        log.info(id + ": show amounts");

        if (msg.getDetails().compareToIgnoreCase("Clear") == 0) {
            touchDisplayEmulatorController.transAmount.setText("");
        } else {
            touchDisplayEmulatorController.appendAmounts(msg.getDetails());
        }
    }

    protected void changeTransferFrom(Msg msg) {
        touchDisplayEmulatorController.setAccPage(msg.getDetails());
    }

    protected void changeTransferTo(Msg msg) {
        touchDisplayEmulatorController.messageArea.setText("[Transfer] Choose target account:");
    }

    protected void changeTransferAmount(Msg msg) {
        touchDisplayEmulatorController.setAmountPage(msg.getDetails());
    }

    protected void showResult(Msg msg) {
        log.info(id + ": show Result");

        touchDisplayEmulatorController.showResult(msg.getDetails());
    }


    //------------------------------------------------------------
    // reloadStage
    private void reloadStage(String fxmlFName) {
        TouchDisplayEmulator touchDisplayEmulator = this;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info(id + ": loading fxml: " + fxmlFName);

                    Parent root;
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(TouchDisplayEmulator.class.getResource(fxmlFName));
                    root = loader.load();
                    touchDisplayEmulatorController = (TouchDisplayEmulatorController) loader.getController();
                    touchDisplayEmulatorController.initialize(id, atmssStarter, log, touchDisplayEmulator);
                    myStage.setScene(new Scene(root, WIDTH, HEIGHT));
                } catch (Exception e) {
                    log.severe(id + ": failed to load " + fxmlFName);
                    e.printStackTrace();
                }
            }
        });
    } // reloadStage
} // TouchDisplayEmulator
