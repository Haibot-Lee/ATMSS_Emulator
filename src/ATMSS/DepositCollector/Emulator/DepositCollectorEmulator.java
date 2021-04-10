package ATMSS.DepositCollector.Emulator;

import ATMSS.ATMSSStarter;
import ATMSS.DepositCollector.DepositCollectorHandler;

import AppKickstarter.misc.Msg;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


public class DepositCollectorEmulator extends DepositCollectorHandler {
    private ATMSSStarter atmssStarter;
    private String id;
    private Stage myStage;
    private DepositCollectorEmulatorController depositCollectorEmulatorController;
    private int oneHundredAmount=0;
    private int fiveHundredAmount=0;
    private int oneThousandAmount=0;


    public DepositCollectorEmulator(String id, ATMSSStarter atmssStarter) throws Exception {
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
        myStage.setScene(new Scene(root, 600, 420));
        myStage.setTitle("Deposit Collector");
        myStage.setResizable(false);
        myStage.setOnCloseRequest((WindowEvent event) -> {
            atmssStarter.stopApp();
            Platform.exit();
        });
        myStage.show();
    } // depositCollectorEmulator

    @Override
    protected void handleEjectMoney(String oneHundredAmount, String fiveHundredAmount, String oneThousandAmount) {
        super.handleEjectMoney(oneHundredAmount, fiveHundredAmount,oneThousandAmount);
        this.oneHundredAmount+=Integer.parseInt(oneHundredAmount);
        this.fiveHundredAmount+=Integer.parseInt(fiveHundredAmount);
        this.oneThousandAmount+=Integer.parseInt(oneThousandAmount);
        DepositCollectorEmulatorController.oneHundredTextField.setText(oneHundredAmount);
        DepositCollectorEmulatorController.fiveHundredTextField.setText(fiveHundredAmount);
        DepositCollectorEmulatorController.oneThousandTextField.setText(oneThousandAmount);
        int totalAmount=100*Integer.parseInt(oneHundredAmount)+500*Integer.parseInt(fiveHundredAmount)+1000*Integer.parseInt(oneThousandAmount);
        DepositCollectorEmulatorController.totalAmountTextField.setText(Integer.toString(totalAmount));
    }

    @Override
    protected void handleEnquiryMoney() {
        super.handleEnquiryMoney();
        String oneHundred=Integer.toString(oneHundredAmount);
        String fiveHundred=Integer.toString(fiveHundredAmount);
        String oneThousand=Integer.toString(oneThousandAmount);
        atmss.send(new Msg(id, mbox, Msg.Type.CD_EnquiryMoney, oneHundred+" "+fiveHundred+" "+oneThousand));
    }
}