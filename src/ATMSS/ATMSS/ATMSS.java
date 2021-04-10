package ATMSS.ATMSS;

import BAMS.BAMS;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;
import AppKickstarter.timer.Timer;

import java.util.Date;


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
    private MBox buzzerMBox;

    private BAMS bams;
    private String currentPage = "";

    //For one card
    private String cardNo = "";
    private String password = "";
    private String trans = "";
    private String moneyWithdrawal = "";
    private String accountWithdrawal = "";
    private String accountDeposit = "";
    private String depositDetails = "";

    // the number of different kind of cash in Cash Dispenser
    private int oneHundredNum;
    private int fiveHundredNum;
    private int oneThousandNum;

    private String DepositTotal = "";
    private int intDepositTotal = 0;
    private int intDepositOne = 0;
    private int intDepositFive = 0;
    private int intDepositTen = 0;

    private int attempt = 0;
    boolean[] lockeds = {false, false, false};

    //------------------------------------------------------------
    // ATMSS
    public ATMSS(String id, AppKickstarter appKickstarter) throws Exception {
        super(id, appKickstarter);
        pollingTime = Integer.parseInt(appKickstarter.getProperty("ATMSS.PollingTime"));
        //Establish Connection
        bams = new BAMS();
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
        cashDispenserMBox = appKickstarter.getThread("CashDispenserHandler").getMBox();
        buzzerMBox = appKickstarter.getThread("BuzzerHandler").getMBox();

        for (boolean quit = false; !quit; ) {
            Msg msg = mbox.receive();

            log.fine(id + ": message received: [" + msg + "].");

            switch (msg.getType()) {
                case TD_MouseClicked:
                    log.info("MouseCLicked: " + msg.getDetails());
                    processMouseClicked(msg);
                    break;

                case KP_KeyPressed:
                    log.info("KeyPressed: " + msg.getDetails());
                    processKeyPressed(msg);
                    break;

                case KP_Overtime:
                    log.info("Keypad overtime: " + msg.getDetails());
                    cardReaderMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, ""));
                    touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Eject"));
                    //processKeyPressed(msg);
                    break;

                case TD_Overtime:
                    log.info("TouchDisplay overtime: " + msg.getDetails());
                    cardReaderMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, ""));
                    touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Eject"));
                    //processKeyPressed(msg);
                    break;

                case CR_CardInserted:
                    log.info("CardInserted: " + msg.getDetails());
                    touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Password"));
                    // push keypad for inputting password
                    keypadMBox.send(new Msg(id, mbox, Msg.Type.KP_PushUp, ""));
                    keypadMBox.send(new Msg(id, mbox, Msg.Type.KP_AcceptPassword, ""));
                    currentPage = "password";
                    cardNo = msg.getDetails();
                    break;

                case CR_CardRemoved:
                    log.info("CardRemoved: " + msg.getDetails());
                    touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Start"));
                    break;

                case CR_CardEjected:
                    log.info("CardEjected: " + msg.getDetails());
                    buzzerMBox.send(new Msg(id, mbox, Msg.Type.B_Alert, "Card ejected"));
                    break;

                case P_PrinterJammed:
                    log.info("PrinterJammed: " + msg.getDetails());
                    //touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Start"));
                    break;

                case DC_Total:
                    if (!msg.getDetails().equals("Invalid")) {
                        String[] msgs = msg.getDetails().split("/");
                        intDepositOne += Integer.parseInt(msgs[0]);
                        intDepositFive += Integer.parseInt(msgs[1]);
                        intDepositTen += Integer.parseInt(msgs[2]);
                        intDepositTotal += Integer.parseInt(msgs[3]);
                        DepositTotal = Integer.toString(intDepositTotal);
                    }
                    touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDepositDetails, msg.getDetails()));
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
                    buzzerMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
                    //cashDispenserMBox.send(new Msg(id,mbox,Msg.Type.CD_EnquiryMoney,""));
                    //cashDispenserMBox.send(new Msg(id,mbox,Msg.Type.CD_EjectMoney,"2 3 4"));
                    //cashDispenserMBox.send(new Msg(id,mbox,Msg.Type.CD_EnquiryMoney,""));
                    break;

                case PollAck:
                    log.info("PollAck: " + msg.getDetails());
                    break;

                case Terminate:
                    quit = true;
                    break;

                case CD_EnquiryMoney:
                    String[] m = msg.getDetails().split(" ");
                    oneHundredNum = Integer.parseInt(m[0]);
                    fiveHundredNum = Integer.parseInt(m[1]);
                    oneThousandNum = Integer.parseInt(m[2]);
                    log.info("Money Amount: " + msg.getDetails());
                    break;

                case CD_MoneyJammed:
                    System.out.println(cardNo + accountWithdrawal + moneyWithdrawal);
                    try {
                        bams.deposit(cardNo, accountWithdrawal, moneyWithdrawal);
                    } catch (Exception e) {
                        System.out.println("TestBAMSHandler: Exception caught: " + e.getMessage());
                        e.printStackTrace();
                    }
                    log.info(id + ": money is deposited");
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
            if (lockeds[Integer.parseInt("" + cardNo.charAt(cardNo.length() - 1))]) {
                cardReaderMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, "Locked"));
                touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Start"));

                //reset
                currentPage = "";
                cardNo = "";
                password = "";
                moneyWithdrawal = "";

                attempt = 0;
                attempt = 0;
                accountWithdrawal = "";
                DepositTotal = "";
                intDepositTotal = 0;
                intDepositOne = 0;
                intDepositFive = 0;
                intDepositTen = 0;

            } else {
                cardReaderMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, ""));
                touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Eject"));

                //reset
                currentPage = "";
                // cardNo = "";    // for the money jammed and deposit money back
                password = "";
                moneyWithdrawal = "";
                attempt = 0;
                accountWithdrawal = "";
                DepositTotal = "";
                intDepositTotal = 0;
                intDepositOne = 0;
                intDepositFive = 0;
                intDepositTen = 0;
            }
        } else {
            switch (currentPage) {
                case "password":
                    if (msg.getDetails().compareToIgnoreCase("Enter") == 0) {
                        if (bams.cardValidation(cardNo, password)) {
                            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                            keypadMBox.send(new Msg(id, mbox, Msg.Type.KP_Freeze, ""));
                            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_AcceptInput, "MainMenu"));

                            currentPage = "mainMenu";
                            attempt = 0;
                        } else {
                            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "wrongPassword"));
                            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_Passwords, "Clear"));
                            password = "";
                            attempt++;
                        }
                    } else if (!msg.getDetails().equals(".") && !msg.getDetails().equals("00")) {
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_Passwords, msg.getDetails()));
                        if (msg.getDetails().compareToIgnoreCase("Clear") == 0) {
                            password = "";
                        } else {
                            password += msg.getDetails();
                        }
                    }

                    if (attempt >= 3) {
                        cardReaderMBox.send(new Msg(id, mbox, Msg.Type.CR_LockCard, cardNo));
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Locked"));
                        lockeds[Integer.parseInt("" + cardNo.charAt(cardNo.length() - 1))] = true;
                    }
                    break;

                case "transferAccount":
                    if (msg.getDetails().compareToIgnoreCase("Enter") == 0) {
                        String result = bams.moneyTransfer(cardNo, trans);
                        trans += "/" + result;
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_ShowResult, result));
                        currentPage = "showTransferResult";
                    } else {
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_TransAmount, msg.getDetails()));
                        if (msg.getDetails().compareToIgnoreCase("Clear") == 0) {
                            String[] temp = trans.split("/");
                            trans = temp[0] + "/" + temp[1] + "/";
                        } else {
                            trans += msg.getDetails();
                        }
                    }
                    break;

                case "moneyWithdrawal":
                    if (msg.getDetails().compareToIgnoreCase("Enter") == 0) {
                        try {
                            int moneyAmount = Integer.parseInt(moneyWithdrawal);
                            String oneThousandAmount = Integer.toString(moneyAmount / 1000);
                            String oneHundredAmount = Integer.toString(moneyAmount % 1000 / 100);
                            int mod = moneyAmount % 100;
                            if (mod != 0) {
                                touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_InvalidInput, "At least 100"));
                                moneyWithdrawal = "";
                                break;
                            }
                            if (moneyAmount / 1000 > oneThousandNum || moneyAmount % 1000 / 100 > oneHundredNum) {
                                moneyWithdrawal = "";
                                touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_InvalidInput, "Not enough money"));
                                moneyWithdrawal = "";
                                break;
                            }

                            int outAmount = bams.withdraw(cardNo, accountWithdrawal, moneyWithdrawal);
                            if (outAmount != -1) {
                                cashDispenserMBox.send(new Msg(id, mbox, Msg.Type.CD_EjectMoney, oneHundredAmount + " 0 " + oneThousandAmount));

                                touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "WithdrawalReceipt"));
                                currentPage = "withdrawalReceipt";
                            } else {
                                touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_InvalidInput, "Balance not enough!"));
                                moneyWithdrawal = "";
                                break;
                            }

                        } catch (Exception e) {
                            moneyWithdrawal = "";
                            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_InvalidInput, "Wrong type"));
                        }
                    } else {
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_Withdrawal, msg.getDetails()));
                        if (msg.getDetails().compareToIgnoreCase("Clear") == 0) {
                            moneyWithdrawal = "";
                        } else {
                            moneyWithdrawal += msg.getDetails();
                        }

                    }
                    break;

                case "moneyDeposit":
                    if (msg.getDetails().compareToIgnoreCase("Enter") == 0) {
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "DepositReceipt"));
                        bams.deposit(cardNo, accountDeposit, DepositTotal);
                        currentPage = "depositReceipt";
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
                        currentPage = "selectAccountWithdrawal";
                        String allAccounts = bams.getAcc(cardNo);
                        if (!allAccounts.equals("")) {
                            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_showAccount, allAccounts));
                        }
                        //touchDisplayMBox.send(new Msg(id,mbox,Msg.Type.TD_UpdateDisplay,"Withdrawal"));
                        //keypadMBox.send(new Msg(id, mbox, Msg.Type.KP_PushUp, ""));
                        break;

                    case 2:
                        //Cash Deposit
                        currentPage = "selectAccountDeposit";
                        allAccounts = bams.getAcc(cardNo);
                        if (!allAccounts.equals("")) {
                            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_Deposit, allAccounts));
                        }
                        break;

                    case 3:
                        //money transfer
                        String accs = bams.getAcc(cardNo);
                        if (!accs.equals("")) {
                            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_Message_transferFrom, accs));
                        }

                        trans = "" + accs.split("/").length;
                        currentPage = "transferFrom";
                        printerMBox.send(new Msg(id, mbox, Msg.Type.P_Reset, ""));
                        break;

                    case 4:
                        //Balance Enquiry
                        String balance = "";
                        balance += "Account 1: " + bams.checkBalance(cardNo, "-0");
                        balance += "\nAccount 2: " + bams.checkBalance(cardNo, "-1");
                        balance += "\nAccount 3: " + bams.checkBalance(cardNo, "-2");
                        balance += "\nAccount 4: " + bams.checkBalance(cardNo, "-3");

                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_ShowResult, balance));

                        currentPage = "showBalance";
                        printerMBox.send(new Msg(id, mbox, Msg.Type.P_Reset, ""));
                        break;

                    case 6:
                        //Exit
                        cardReaderMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, ""));
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Eject"));
                        currentPage = "";
                        //cardNo = "";
                        password = "";
                        break;
                }
                break;

            case "showBalance":
                switch (buttonPressed) {
                    case 1:
                        //print advice
                        String balance = "";
                        balance += "Account 1: " + bams.checkBalance(cardNo, "-0");
                        balance += "\nAccount 2: " + bams.checkBalance(cardNo, "-1");
                        balance += "\nAccount 3: " + bams.checkBalance(cardNo, "-2");
                        balance += "\nAccount 4: " + bams.checkBalance(cardNo, "-3");

                        printerMBox.send(new Msg(id, mbox, Msg.Type.P_PrintAdvice, balance));
                        break;

                    case 5:
                        // cancel and go back to the main menu
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                        currentPage = "mainMenu";
                        break;

                    case 6:
                        //Exit
                        cardReaderMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, ""));
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Eject"));
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_Freeze, ""));

                        currentPage = "";
                        cardNo = "";
                        password = "";
                        break;

                }
                break;

            case "transferFrom":
                if (buttonPressed == 5) {
                    touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                    currentPage = "mainMenu";
                } else {
                    for (int i = 1; i <= Integer.parseInt(trans); i++) {
                        if (buttonPressed == i) {
                            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_Message_transferTo, ""));
                            trans += "/" + i;
                            currentPage = "transferTo";
                            break;
                        }
                    }
                }
                break;

            case "transferTo":
                if (buttonPressed == 5) {
                    touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                    currentPage = "mainMenu";
                } else {
                    String[] temp = trans.split("/");
                    trans = temp[1];
                    for (int i = 1; i <= Integer.parseInt(temp[0]); i++) {
                        if (buttonPressed == i) {
                            trans += "/" + i;
                            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_Message_transferAmount, trans));
                            trans += "/";
                            currentPage = "transferAccount";
                            break;
                        }

                    }
                }
                break;

            case "transferAccount":
                switch (buttonPressed) {
                    case 5:
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_Freeze, ""));
                        currentPage = "mainMenu";
                        break;
                    case 6:
                        String result = bams.moneyTransfer(cardNo, trans);
                        trans += "/" + result;
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_ShowResult, result));
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_Freeze, ""));
                        currentPage = "showTransferResult";
                        break;
                }
                break;

            case "showTransferResult":
                switch (buttonPressed) {
                    case 1:
                        //print advice
                        String[] accs = bams.getAcc(cardNo).split("/");
                        String[] idx = trans.split("/");
                        String result = "Money Transfer on card: " + cardNo;
                        result += "\nTransfer " + idx[2] + " from account " + accs[Integer.parseInt(idx[0]) - 1] + " to account " + accs[Integer.parseInt(idx[1]) - 1];
                        result += "\n" + idx[3];
                        printerMBox.send(new Msg(id, mbox, Msg.Type.P_PrintAdvice, result));
                        break;
                    case 5:
                        //Back to main menu
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                        currentPage = "mainMenu";
                        break;

                    case 6:
                        //Exit
                        cardReaderMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, ""));
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Eject"));
                        //touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_Freeze, ""));
                        currentPage = "";
                        //cardNo = "";
                        password = "";
                        break;

                }
                break;

            case "moneyWithdrawal":
                switch (buttonPressed) {
                    case 6:
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                        currentPage = "mainMenu";
                        break;
                }


            case "moneyDeposit":
                switch (buttonPressed) {
                    case 3:
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "DepositReceipt"));
                        bams.deposit(cardNo, accountDeposit, DepositTotal);
                        currentPage = "depositReceipt";
                        break;
                    case 5:
                        //update data
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "moneyDeposit"));
                        depositCollectorMBox.send(new Msg(id, mbox, Msg.Type.DC_ButtonControl, ""));
                        break;
                    case 6:
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                        currentPage = "mainMenu";
                        break;
                }
                break;

            case "selectAccountWithdrawal":
                if (buttonPressed == 5) {
                    touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                    currentPage = "mainMenu";
                } else {
                    String[] accs = bams.getAcc(cardNo).split("/");
                    for (int i = 1; i <= accs.length; i++) {
                        if (buttonPressed == i) {
                            cashDispenserMBox.send(new Msg(id, mbox, Msg.Type.CD_EnquiryMoney, "")); // ask for the money amount of three denominations of cash dispenser
                            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Withdrawal"));

                            accountWithdrawal = accs[i - 1];
                            moneyWithdrawal = "";
                            currentPage = "moneyWithdrawal";
                            break;
                        }
                    }
                }
                break;

            case "selectAccountDeposit":
                if (buttonPressed == 5) {
                    touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                    currentPage = "mainMenu";
                } else {
                    String[] accs = bams.getAcc(cardNo).split("/");
                    for (int i = 0; i <= accs.length; i++) {
                        if (buttonPressed == i) {
                            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Deposit"));
                            depositCollectorMBox.send(new Msg(id, mbox, Msg.Type.DC_ButtonControl, ""));
                            accountDeposit = accs[i - 1];
                            currentPage = "moneyDeposit";
                            break;
                        }
                    }
                }

                break;

            case "withdrawalReceipt":
                switch (buttonPressed) {
                    case 3:
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "WithdrawalEnd"));
                        currentPage = "withdrawalEnd";
                        long currentTime = (new Date()).getTime();
                        String receipt = currentTime + " " + cardNo + " " + accountWithdrawal + " withdraw " + moneyWithdrawal;
                        printerMBox.send(new Msg(id, mbox, Msg.Type.P_PrintAdvice, receipt));
                        break;
                    case 4:
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "WithdrawalEnd"));
                        currentPage = "withdrawalEnd";
                        break;
                }
                break;

            case "depositReceipt":
                switch (buttonPressed) {
                    case 3:
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "DepositEnd"));
                        currentPage = "withdrawalEnd";
                        long currentTime = (new Date()).getTime();
                        String receipt = currentTime + " " + cardNo + " " + accountDeposit + " deposit " + DepositTotal;
                        printerMBox.send(new Msg(id, mbox, Msg.Type.P_PrintAdvice, receipt));
                        break;
                    case 4:
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "DepositEnd"));
                        currentPage = "withdrawalEnd";
                        break;
                }
                break;


            case "withdrawalEnd":
            case "depositEnd":
                switch (buttonPressed) {
                    //back to main menu
                    case 3:
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                        currentPage = "mainMenu";
                        break;
                    //Exit
                    case 4:
                        cardReaderMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, ""));
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_Freeze, ""));
                        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Eject"));
                        //touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_Freeze, ""));
                        currentPage = "";
                        //cardNo = "";
                        password = "";
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
            } else if (y >= 200) {
                return 7;
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
}
