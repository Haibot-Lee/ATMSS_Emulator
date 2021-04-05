package ATMSS.ATMSS;

import ATMSS.BAMSHandler.BAMSHandler;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;
import AppKickstarter.timer.Timer;


//======================================================================
// ATMSS
public class ATMSS extends AppThread {
    private int pollingTime;
    private MBox cardReaderMBox;
    private MBox keypadMBox;
    private MBox touchDisplayMBox;
    private MBox printerMBox;
    private MBox depositCollectorMBox;
    private MBox cashDispenserMBox;

    private BAMSHandler bams;
    private String currentPage = "";

    //For one card
    private String cardNo = "";
    private String password = "";
    private String trans = "";

    //------------------------------------------------------------
    // ATMSS
    public ATMSS(String id, AppKickstarter appKickstarter) throws Exception {
        super(id, appKickstarter);
        pollingTime = Integer.parseInt(appKickstarter.getProperty("ATMSS.PollingTime"));
        bams = new BAMSHandler("http://cslinux0.comp.hkbu.edu.hk/comp4107_20-21_grp01/");
    } // ATMSS


    //------------------------------------------------------------
    // run
    public void run() {
        Timer.setTimer(id, mbox, pollingTime);
        log.info(id + ": starting...");

        cardReaderMBox = appKickstarter.getThread("CardReaderHandler").getMBox();
        keypadMBox = appKickstarter.getThread("KeypadHandler").getMBox();
        touchDisplayMBox = appKickstarter.getThread("TouchDisplayHandler").getMBox();
        depositCollectorMBox = appKickstarter.getThread("DepositCollectorHandler").getMBox();
        printerMBox = appKickstarter.getThread("PrinterHandler").getMBox();
        cashDispenserMBox = appKickstarter.getThread("PrinterHandler").getMBox();

        for (boolean quit = false; !quit; ) {
            Msg msg = mbox.receive();

            log.fine(id + ": message received: [" + msg + "].");

            switch (msg.getType()) {
                case TD_MouseClicked:
                    log.info("MouseCLicked: " + msg.getDetails());
                    processMouseClicked(msg);
                    break;

                case KP_KeyPressed:
                    //log.info("KeyPressed: " + msg.getDetails());
                    processKeyPressed(msg);
                    break;

                case CR_CardInserted:
                    log.info("CardInserted: " + msg.getDetails());
                    touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Password"));
                    currentPage = "password";
                    cardNo = msg.getDetails();
                    break;

                case CR_CardRemoved:
                    log.info("CardRemoved: " + msg.getDetails());
                    touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Start"));
                    break;

                case P_PrinterJammed:
                    log.info("PrinterJammed: " + msg.getDetails());
                    //touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Start"));
                    break;

                case TimesUp:
                    Timer.setTimer(id, mbox, pollingTime);
                    log.info("Poll: " + msg.getDetails());
                    cardReaderMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
                    keypadMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
                    touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
                    printerMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
                    depositCollectorMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
                    cashDispenserMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
                    break;

                case PollAck:
                    log.info("PollAck: " + msg.getDetails());
                    break;

                case Terminate:
                    quit = true;
                    break;

                default:
                    log.warning(id + ": unknown message type: [" + msg + "]");
            }
        }

        // declaring our departure
        appKickstarter.unregThread(this);
        log.info(id + ": terminating...");
    } // run


    //------------------------------------------------------------
    // processKeyPressed
    private void processKeyPressed(Msg msg) {
        if (currentPage.equals("")) {
            log.info("Invalid key pressed: " + msg.getDetails());
            return;
        }

        if (msg.getDetails().compareToIgnoreCase("Cancel") == 0) {
            cardReaderMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, ""));
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Eject"));

            //reset
            currentPage = "";
            cardNo = "";
            password = "";
        } else {
            switch (currentPage) {
                case "password":
                    if (msg.getDetails().compareToIgnoreCase("Enter") == 0) {
                        if (cardValidation()) {
                            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                            currentPage = "mainMenu";
                        } else {
                            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "wrongPassword"));
                            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_Passwords, "Clear"));
                            password = "";
                        }
                    } else if (!msg.getDetails().equals(".") && !msg.getDetails().equals("00")) {
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_Passwords, msg.getDetails()));
                        if (msg.getDetails().compareToIgnoreCase("Clear") == 0) {
                            password = "";
                        } else {
                            password += msg.getDetails();
                        }
                    }
                    break;
            }
        }

    } // processKeyPressed


    //------------------------------------------------------------
    // processMouseClicked
    private void processMouseClicked(Msg msg) {
        String[] msgs = msg.getDetails().split(" ");
        int x = Integer.parseInt(msgs[0]), y = Integer.parseInt(msgs[1]);

        int buttonPressed = buttonPressed(x, y);

        switch (currentPage) {
            case "mainMenu":
                switch (buttonPressed) {
                    case 1:
                        //cash withdrawal
                        break;
                    case 2:
                        //Cash Deposit
                        break;
                    case 3:
                        //money transfer
                        String accs = getAcc();
                        if (!accs.equals("")) {
                            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_Message_transferFrom, accs));
                        }
                        currentPage = "transferFrom";
                        break;
                    case 4:
                        //Balance Enquiry
                        String balance = "";
                        balance += "Account 1: " + checkBalance("-0");
                        balance += "\nAccount 2: " + checkBalance("-1");
                        balance += "\nAccount 3: " + checkBalance("-2");
                        balance += "\nAccount 4: " + checkBalance("-3");

                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_ShowBalance, balance));

                        currentPage = "showBalance";
                        break;
                    case 6:
                        //Exit
                        cardReaderMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, ""));
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Eject"));
                        currentPage = "";
                        cardNo = "";
                        password = "";
                        break;

                }

            case "showBalance":
                switch (buttonPressed) {
                    case 1:
                        //print advice
                        String balance = "";
                        balance += "Account 1: " + checkBalance("-0");
                        balance += "\nAccount 2: " + checkBalance("-1");
                        balance += "\nAccount 3: " + checkBalance("-2");
                        balance += "\nAccount 4: " + checkBalance("-3");

                        printerMBox.send(new Msg(id, mbox, Msg.Type.P_PrintAdvice, balance));
                        break;
                    case 6:
                        //Exit
                        cardReaderMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, ""));
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Eject"));
                        currentPage = "";
                        cardNo = "";
                        password = "";
                        break;

                }
                break;

            case "transferFrom":
                switch (buttonPressed) {
                    case 5:
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                        currentPage = "mainMenu";
                        break;
                }
                break;
        }

    } // processMouseClicked

    private int buttonPressed(int x, int y) {
        if (x >= 0 && x <= 300) {
            if (y >= 410) {
                return 5;
            } else if (y >= 340) {
                return 3;
            } else if (y >= 270) {
                return 1;
            }
        } else if (x >= 340 && x <= 640) {
            if (y >= 410) {
                return 6;
            } else if (y >= 340) {
                return 4;
            } else if (y >= 270) {
                return 2;
            }
        }
        return 0;
    }

    private String checkBalance(String number) {
        double balance = 0;
        try {
            // cred?
            balance = bams.enquiry(cardNo, cardNo + number, cardNo);
            if (balance == -1) return "Invalid account";
            else return "" + balance;
        } catch (Exception e) {
            System.out.println("ATMSS: Exception caught: " + e.getMessage());
            e.printStackTrace();
        }
        return "" + balance;
    }

    //-------------------------------------------------------------
    //BAMS Connection
    private boolean cardValidation() {
        String cred = "";
        try {
            cred = bams.login(cardNo, password);
        } catch (Exception e) {
            System.out.println("TestBAMSHandler: Exception caught: " + e.getMessage());
            e.printStackTrace();
        }
        if (!cred.equals("") && !cred.equals("Error")) {
            return true;
        }
        return false;
    }

    private String getAcc() {
        try {
            return bams.getAccounts(cardNo, cardNo);
        } catch (Exception e) {
            System.out.println("TestBAMSHandler: Exception caught: " + e.getMessage());
            e.printStackTrace();
        }
        return "";
    }
}
