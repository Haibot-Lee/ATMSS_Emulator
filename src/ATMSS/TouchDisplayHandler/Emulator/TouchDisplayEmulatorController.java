package ATMSS.TouchDisplayHandler.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;

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
        td_transferTitle.setText("Select Payment Account:");
        String[] accs = msg.split("/");
        TextField[] td_transferAccount = {td_transferAccount1, td_transferAccount2, td_transferAccount3, td_transferAccount4};
        for (int i = 0; i < td_transferAccount.length; i++) {
            if (i < accs.length) {
                td_transferAccount[i].setText(accs[i]);
            } else {
                td_transferAccount[i].setVisible(false);
            }
        }
    }

} // TouchDisplayEmulatorController
