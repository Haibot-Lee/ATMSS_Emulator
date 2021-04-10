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
    public TextField withdrawalField;
    public TextField invalidInputField;
    public TextField passwordMsg;
    public Text messageArea;


    public Text button1;
    public Text button2;
    public Text button3;
    public Text button4;
    public Text button5;
    public Text button6;

    public TextField transAmount;

    public TextField one;
    public TextField five;
    public TextField ten;
    public TextField total;

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

    public void appendPassword(String password) {
        String cur = passwordField.getText();
        cur += password;
        passwordField.setText(cur);
    }

    public void setInvalidInput(String msg) {
        withdrawalField.setText("");
        invalidInputField.setText(msg);
    }

    public void appendWithdrawal(String withdrawal) {
        String cur = withdrawalField.getText();
        cur += withdrawal;
        withdrawalField.setText(cur);
    }

    public void appendAmounts(String amount) {
        String cur = transAmount.getText();
        cur += amount;
        transAmount.setText(cur);
    }

    public void setPasswordMsg(String msg) {
        passwordMsg.setText(msg);
    }

    public void setAccPage(String msg) {
        Text[] buttons = {button1, button2, button3, button4};
        String[] accs = msg.split("/");
        for (int i = 0; i < buttons.length; i++) {
            if (i < accs.length) {
                buttons[i].setText(accs[i]);
            } else {
                buttons[i].setText("");
            }
        }
        button5.setText("Cancel");
        button6.setText("");
    }

    public void setAmountPage(String msg) {
        transAmount.setVisible(true);
        Text[] buttons = {button1, button2, button3, button4};
        String[] accs = msg.split("/");
        int from = Integer.parseInt(accs[0]), to = Integer.parseInt(accs[1]);
        messageArea.setText("[Transfer from (" + buttons[from - 1].getText() + ") to (" + buttons[to - 1].getText() + ")] Input transfer amount:");
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText("");
        }
        button5.setText("Cancel");
        button6.setText("Confirm");
    }

    public void showResult(String msg) {
        transAmount.setVisible(false);
        messageArea.setText(msg);
        button1.setText("Print Advice");
        button2.setText("");
        button3.setText("");
        button4.setText("");
        button5.setText("Back to Main Menu");
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

    public void setDepositCollectorInfo(String[] details) {
        int first = Integer.parseInt(one.getText())+Integer.parseInt(details[0]);
        int second = Integer.parseInt(five.getText())+Integer.parseInt(details[1]);
        int third = Integer.parseInt(ten.getText())+Integer.parseInt(details[2]);
        int forth = Integer.parseInt(total.getText())+Integer.parseInt(details[3]);
        one.setText(Integer.toString(first));
        five.setText(Integer.toString(second));
        ten.setText(Integer.toString(third));
        total.setText(Integer.toString(forth));

        String n = first + " " +second +" "+third+" "+forth;
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.DC_MoneyDetails, n));
    }
} // TouchDisplayEmulatorController
