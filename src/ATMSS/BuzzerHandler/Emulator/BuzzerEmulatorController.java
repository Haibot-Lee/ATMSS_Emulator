package ATMSS.BuzzerHandler.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import javafx.event.ActionEvent;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.logging.Logger;


//======================================================================
// BuzzerEmulatorController
public class BuzzerEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private BuzzerEmulator buzzerEmulator;
    private MBox buzzerMBox;

    public TextField cardStatusField;
    public TextArea cardReaderTextArea;



    //------------------------------------------------------------
    // initialize
    public void initialize(String id, AppKickstarter appKickstarter, Logger log, BuzzerEmulator buzzerEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
        this.log = log;
        this.buzzerEmulator = buzzerEmulator;
        this.buzzerMBox = appKickstarter.getThread("BuzzerHandler").getMBox();

    } // initialize

/*
    //------------------------------------------------------------
    // buttonPressed
    public void buttonPressed(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();

        switch (btn.getText()) {
            case "Card 1":
                cardNumField.setText(appKickstarter.getProperty("CardReader.Card1"));
                break;

            case "Card 2":
                cardNumField.setText(appKickstarter.getProperty("CardReader.Card2"));
                break;

            case "Card 3":
                cardNumField.setText(appKickstarter.getProperty("CardReader.Card3"));
                break;

            case "Reset":
                cardNumField.setText("");
                break;

            case "Insert Card":
                if (cardNumField.getText().length() != 0) {
                    buzzerMBox.send(new Msg(id, buzzerMBox, Msg.Type.CR_CardInserted, cardNumField.getText()));
                    appendTextArea("Sending " + cardNumField.getText());
                }
                break;

            case "Remove Card":
                if (cardStatusField.getText().compareTo("Card Ejected") == 0) {
                    buzzerMBox.send(new Msg(id, buzzerMBox, Msg.Type.CR_CardRemoved, cardNumField.getText()));
                    appendTextArea("Removing card");
                }
                break;

            default:
                log.warning(id + ": unknown button: [" + btn.getText() + "]");
                break;
        }
    } // buttonPressed
*/

    /*
    //------------------------------------------------------------
    // updateCardStatus
    public void updateCardStatus(String status) {
        cardStatusField.setText(status);

        switch(status) {
            case "Card Inserted":
                cardReaderInsertButton.setDisable(true);
                cardReaderRemoveButton.setDisable(true);
                break;

            case "Card Ejected":
                cardReaderInsertButton.setDisable(true);
                cardReaderRemoveButton.setDisable(false);
                break;
            case "Card Reader Empty":
                cardReaderInsertButton.setDisable(false);
                cardReaderRemoveButton.setDisable(true);
                break;
            case "Card Locked":
                cardReaderInsertButton.setDisable(false);
                cardReaderRemoveButton.setDisable(true);
                cardStatusField.setText("");
                break;

            default:
                System.out.println("Unknown card status: " + status);
        }
    } // updateCardStatus


    //------------------------------------------------------------
    // appendTextArea
    public void appendTextArea(String status) {
        javafx.application.Platform.runLater( () -> cardReaderTextArea.appendText(status + "\n"));
    } // appendTextArea

    //------------------------------------------------------------
    // lockCard
    public void lockCard(int cardNo) {
        javafx.application.Platform.runLater( () -> cards[cardNo - 1].setDisable(true));
        javafx.application.Platform.runLater( () -> cardReaderRemoveButton.setDisable(true));
        javafx.application.Platform.runLater( () -> cardReaderInsertButton.setDisable(false));
        javafx.application.Platform.runLater( () -> cardNumField.setText(""));
        //javafx.application.Platform.runLater( () -> cardReaderTextArea.appendText("Card locked\n"));
        javafx.application.Platform.runLater( () -> cardStatusField.setText("Card locked"));
    } // lockCard


    //------------------------------------------------------------
    // overtime
    public void overtime() {
        int cardNo = Integer.parseInt(""+cardNumField.getText().charAt(cardNumField.getText().length() - 1));
        lockCard(cardNo);
     } // overtime
*/

} // CardReaderEmulatorController
