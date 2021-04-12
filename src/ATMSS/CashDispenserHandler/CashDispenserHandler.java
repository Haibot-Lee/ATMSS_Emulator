package ATMSS.CashDispenserHandler;
import ATMSS.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;

public class CashDispenserHandler extends HWHandler {


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
                break;
            case CD_MoneyJammed:
                handleMoneyJammed();
                break;
            case TimesUp:
                handleTimesup(msg);
                break;
            case CD_AddDenomination:
                handeleAddDenomination(msg);
                break;
            case CD_MoneyTaken:
                atmss.send(new Msg(id,mbox,Msg.Type.CD_MoneyTaken,""));
                break;
        }
    }

    protected void handleEjectMoney(String oneHundredAmount, String fiveHundredAmount, String oneThousandAmount) {
        log.info(id + ": is ejecting money");
    }

    protected void handleEnquiryMoney() {
        log.info(id + ": is answering enquiry");

    }

    protected void handleMoneyJammed() {
        log.warning(id + ": Money Jammed");
    }

    protected void handleTimesup(Msg msg) {
        log.warning(id + ": Time is up");
    }

    protected void handeleAddDenomination(Msg msg) {

    }

}
