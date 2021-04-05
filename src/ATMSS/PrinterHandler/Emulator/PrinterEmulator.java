package ATMSS.PrinterHandler.Emulator;

import ATMSS.ATMSSStarter;
import ATMSS.PrinterHandler.PrinterHandler;

import AppKickstarter.misc.Msg;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


//======================================================================
// PrinterEmulator
public class PrinterEmulator extends PrinterHandler {
    private ATMSSStarter atmssStarter;
    private String id;
    private Stage myStage;
    private PrinterEmulatorController printerEmulatorController;

    //------------------------------------------------------------
    // PrinterEmulator
    public PrinterEmulator(String id, ATMSSStarter atmssStarter) {
        super(id, atmssStarter);
        this.atmssStarter = atmssStarter;
        this.id = id;
    } // PrinterEmulator

    //------------------------------------------------------------
    // start
    public void start() throws Exception {
        Parent root;
        myStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        String fxmlName = "PrinterEmulator.fxml";
        loader.setLocation(PrinterEmulator.class.getResource(fxmlName));
        root = loader.load();
        printerEmulatorController = (PrinterEmulatorController) loader.getController();
        printerEmulatorController.initialize(id, atmssStarter, log, this);
        myStage.initStyle(StageStyle.DECORATED);
        myStage.setScene(new Scene(root, 350, 470));
        myStage.setTitle("Printer");
        myStage.setResizable(false);
        myStage.setOnCloseRequest((WindowEvent event) -> {
            atmssStarter.stopApp();
            Platform.exit();
        });
        myStage.show();
    } // PrinterEmulator


    //------------------------------------------------------------
    // handlePrintAdvice
    protected void handlePrintAdvice(Msg msg) {
        // fixme
        super.handlePrintAdvice();
        if (printerEmulatorController.printerTextField.getText().equals("")) {
            printerEmulatorController.setTextArea(msg.getDetails());
            printerEmulatorController.setTextField("");

            printerEmulatorController.printerButton.setDisable(false);
        }else {
            atmss.send(new Msg(id, mbox, Msg.Type.P_PrinterJammed, ""));
        }
    } // handlePrintAdvice


} // PrinterEmulator

