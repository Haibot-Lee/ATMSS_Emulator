package ATMSS.CashDispenserHandler;

import ATMSS.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;
import AppKickstarter.timer.Timer;

public class CashDispenserHandler extends HWHandler {
    int waitingTime = 30000;

    public CashDispenserHandler(String id, AppKickstarter appKickstarter) {
        super(id, appKickstarter);
    } // CashDispenserHandler

    protected void processMsg(Msg msg) {
        switch (msg.getType()) {
            case CD_EjectMoney:
                String money = msg.getDetails();
                String[] m = money.split(" ");
                handleEjectMoney(m[0], m[1], m[2]);
                break;
            case CD_EnquiryMoney:
                handleEnquiryMoney();
                //atmss.send(new Msg(id, mbox, Msg.Type.CD_MoneyAmount, ""));
                break;
            case CD_MoneyJammed:
                handleMoneyJammed();
                break;
            //atmss.send(new Msg(id, mbox, Msg.Type.CD_MoneyJammed, msg.getDetails()));
            case TimesUp:
                handleTimesup(msg);
                break;
            case CD_AddDenomination:
                handeleAddDenomination(msg);
                break;


        }
    }

    protected void handleEjectMoney(String oneHundredAmount, String fiveHundrdAmount, String oneThousandAmount) {
        log.info(id + ": is ejecting money");
        Timer.setTimer(id, mbox, waitingTime, 77);

    }

    protected void handleEnquiryMoney() {
        log.info(id + ": is answering enquiry");

    }

    protected void handleMoneyJammed() {

    }

    protected void handleTimesup(Msg msg) {
        log.warning(id + ": Time is up");
    }

    protected void handeleAddDenomination(Msg msg) {

    }

}
