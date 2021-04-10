package ATMSS.CashDispenserHandler.Emulator;

import ATMSS.ATMSSStarter;
import ATMSS.CashDispenserHandler.CashDispenserHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


public class CashDispenserEmulator extends CashDispenserHandler {
    private ATMSSStarter atmssStarter;
    private String id;
    private Stage myStage;
    private CashDispenserEmulatorController cashDispenserEmulatorController;
    private int oneHundredAmount;
    private int fiveHundredAmount;
    private int oneThousandAmount;

    public CashDispenserEmulator(String id, ATMSSStarter atmssStarter) throws Exception {
        super(id, atmssStarter);
        this.atmssStarter = atmssStarter;
        this.id = id;
        this.oneHundredAmount = Integer.parseInt(appKickstarter.getProperty("CashDispenser.oneHundredAmount"));
        this.fiveHundredAmount = Integer.parseInt(appKickstarter.getProperty("CashDispenser.fiveHundredAmount"));
        this.oneThousandAmount = Integer.parseInt(appKickstarter.getProperty("CashDispenser.oneThousandAmount"));
    }//cashDispenserEmulator

    public void start() throws Exception {
        Parent root;
        myStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        String fxmlName = "CashDispenserEmulator.fxml";
        loader.setLocation(CashDispenserEmulator.class.getResource(fxmlName));
        root = loader.load();
        cashDispenserEmulatorController = (CashDispenserEmulatorController) loader.getController();
        cashDispenserEmulatorController.initialize(id, atmssStarter, log, this);
        myStage.initStyle(StageStyle.DECORATED);
        myStage.setScene(new Scene(root, 600, 420));
        myStage.setTitle("Cash Dispenser");
        myStage.setResizable(false);
        myStage.setOnCloseRequest((WindowEvent event) -> {
            atmssStarter.stopApp();
            Platform.exit();
        });
        myStage.show();
    } // cashDispenserEmulator

    @Override
    protected void handleEjectMoney(String oneHundredAmount, String fiveHundredAmount, String oneThousandAmount) {
        super.handleEjectMoney(oneHundredAmount, fiveHundredAmount, oneThousandAmount);
        this.oneHundredAmount -= Integer.parseInt(oneHundredAmount);
        this.fiveHundredAmount -= Integer.parseInt(fiveHundredAmount);
        this.oneThousandAmount -= Integer.parseInt(oneThousandAmount);
        if (this.oneHundredAmount == 0 || this.fiveHundredAmount == 0 || this.oneThousandAmount == 0) {
            handleEnquiryMoney();
        }
        cashDispenserEmulatorController.oneHundredTextField.setText(oneHundredAmount);
        cashDispenserEmulatorController.fiveHundredTextField.setText(fiveHundredAmount);
        cashDispenserEmulatorController.oneThousandTextField.setText(oneThousandAmount);
        int totalAmount = 100 * Integer.parseInt(oneHundredAmount) + 500 * Integer.parseInt(fiveHundredAmount) + 1000 * Integer.parseInt(oneThousandAmount);
        cashDispenserEmulatorController.totalAmountTextField.setText(Integer.toString(totalAmount));
    }

    @Override
    protected void handleEnquiryMoney() {
        super.handleEnquiryMoney();
        String oneHundred = Integer.toString(oneHundredAmount);
        String fiveHundred = Integer.toString(fiveHundredAmount);
        String oneThousand = Integer.toString(oneThousandAmount);
        atmss.send(new Msg(id, mbox, Msg.Type.CD_EnquiryMoney, oneHundred + " " + fiveHundred + " " + oneThousand));
    }

    @Override
    protected void handleTimesup(Msg msg) {
        super.handleTimesup(msg);
        cashDispenserEmulatorController.clearArea();
        atmss.send(new Msg(id, mbox, Msg.Type.CD_MoneyJammed, msg.getDetails()));
        log.info(id + ": Money is collected");
    }
}
