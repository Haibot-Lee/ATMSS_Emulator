package BAMS;

import ATMSS.BAMSHandler.BAMSHandler;
import ATMSS.BAMSHandler.BAMSInvalidReplyException;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.*;

public class BAMS {
    private BAMSHandler bams;

    public BAMS() {
        bams = new ATMSS.BAMSHandler.BAMSHandler("http://cslinux0.comp.hkbu.edu.hk/comp4107_20-21_grp01/", initLogger());
    }

    public boolean cardValidation(String cardNo, String password) {
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

    public String getAcc(String cardNo) {
        try {
            return bams.getAccounts(cardNo, cardNo);
        } catch (Exception e) {
            System.out.println("TestBAMSHandler: Exception caught: " + e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    public String checkBalance(String cardNo, String accNo) {
        double balance = 0;
        try {
            balance = bams.enquiry(cardNo, accNo, cardNo);
            if (balance == -1) return "Invalid account";
            else return "" + balance;
        } catch (Exception e) {
            System.out.println("ATMSS: Exception caught: " + e.getMessage());
            e.printStackTrace();
        }
        return "" + balance;
    }

    public String moneyTransfer(String cardNo, String transInfo) {
        String[] accs = getAcc(cardNo).split("/");
        String[] idx = transInfo.split("/");
        try {
            double transAmount = bams.transfer(cardNo, cardNo, accs[Integer.parseInt(idx[0]) - 1], accs[Integer.parseInt(idx[1]) - 1], idx[2]);
            if (transAmount != -1.0) {
                return "Transfer successfully!";
            }
        } catch (Exception e) {
            System.out.println("TestBAMSHandler: Exception caught: " + e.getMessage());
            e.printStackTrace();
        }
        return "Transfer failed. Balance is not enough!";
    }

    public int withdraw(String cardNo, String accNo, String amount) {
        int outamount = 0;
        try {
            outamount = bams.withdraw(cardNo, accNo, cardNo, amount);
        } catch (Exception e) {
            System.out.println("TestBAMSHandler: Exception caught: " + e.getMessage());
            e.printStackTrace();
        }
        return outamount;
    }

    public double deposit(String cardNo, String accNo, String amount) {
        double depAmount = 0;
        try {
            depAmount = bams.deposit(cardNo, accNo, cardNo, amount);

        } catch (BAMSInvalidReplyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return depAmount;
    }

    //------------------------------------------------------------
    // initLogger
    static Logger initLogger() {
        // init our logger
        ConsoleHandler logConHdr = new ConsoleHandler();
        logConHdr.setFormatter(new LogFormatter());
        Logger log = Logger.getLogger("TestBAMSHandler");
        log.setUseParentHandlers(false);
        log.setLevel(Level.ALL);
        log.addHandler(logConHdr);
        logConHdr.setLevel(Level.ALL);
        return log;
    } // initLogger

    static class LogFormatter extends Formatter {
        //------------------------------------------------------------
        // format
        public String format(LogRecord rec) {
            Calendar cal = Calendar.getInstance();
            String str = "";

            // get date
            cal.setTimeInMillis(rec.getMillis());
            str += String.format("%02d%02d%02d-%02d:%02d:%02d ",
                    cal.get(Calendar.YEAR) - 2000,
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    cal.get(Calendar.SECOND));

            // level of the log
            str += "[" + rec.getLevel() + "] -- ";

            // message of the log
            str += rec.getMessage();
            return str + "\n";
        } // format
    } // LogFormatter

}
