package ATMSS.TouchDisplayHandler.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.logging.Logger;


//======================================================================
// TouchDisplayEmulatorController
public class TouchDisplayEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private TouchDisplayEmulator touchDisplayEmulator;
    private MBox touchDisplayMBox;
    public PasswordField passwordField;
    public TextField passwordMsg;
    public Text messageArea;

    public Text button1;
    public Text button2;
    public Text button3;
    public Text button4;
    public Text button5;
    public Text button6;

    public Text td_transferTitle;
    public TextField td_transferAccount1, td_transferAccount2, td_transferAccount3, td_transferAccount4;

    //------------------------------------------------------------
    // initialize
    public void initialize(String id, AppKickstarter appKickstarter, Logger log, TouchDisplayEmulator touchDisplayEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
        this.log = log;
        this.touchDisplayEmulator = touchDisplayEmulator;
        this.touchDisplayMBox = appKickstarter.getThread("TouchDisplayHandler").getMBox();
    } // initialize


    //------------------------------------------------------------
    // td_mouseClick
    public void td_mouseClick(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));
    } // td_mouseClick

    public void resetPassword() {
        passwordField.setText("");
    }

    public void appendPassword(String password) {
        String cur = passwordField.getText();
        cur += password;
        passwordField.setText(cur);
    }

    public void setPasswordMsg(String msg) {
        passwordMsg.setText(msg);
    }

    public void setAcc(String msg) {
        messageArea.setText("[Transfer] Choose payment account:");

        String[] accs = msg.split("/");

        Text[] buttons = {button1, button2, button3, button4};

        for (int i = 0; i < buttons.length; i++) {
            if (i < accs.length) {
                buttons[i].setText(accs[i]);
            } else {
                buttons[i].setText("");
            }
        }

        button5.setText("Cancel");
    }

    public void showBalance(String msg) {
        messageArea.setText(msg);
        button1.setText("Print Advice");
        button2.setText("");
        button3.setText("");
        button4.setText("");
        button5.setText("Cancel");
        button6.setText("Exit");
    }

    public void textReset() {
        button1.setText("Cash Withdrawal");
        button2.setText("Cash Deposit");
        button3.setText("Balance Enquiry");
        button4.setText("Money Transfer");
        button5.setText("");
        button6.setText("Exit");
    }

} // TouchDisplayEmulatorController
