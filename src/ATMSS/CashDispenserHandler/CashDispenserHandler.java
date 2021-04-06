package ATMSS.CashDispenserHandler;
import ATMSS.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;

public class CashDispenserHandler extends HWHandler{
    public CashDispenserHandler(String id, AppKickstarter appKickstarter) {
        super(id, appKickstarter);
    } // CashDispenserHandler
    protected void processMsg(Msg msg){
        switch (msg.getType()) {
            case CD_EjectMoney:
                String money=msg.getDetails();
                String[] m=money.split(" ");
                handleEjectMoney(m[0],m[1],m[2]);
                //atmss.send(new Msg(id, mbox, Msg.Type.CD_EjectMoney, id+": ejecting..."));
                break;
            case CD_EnquiryMoney:
                handleEnquiryMoney();
                //atmss.send(new Msg(id, mbox, Msg.Type.CD_MoneyAmount, ""));
            case CD_MoneyJammed:
                handleMoneyJammed();
                //atmss.send(new Msg(id, mbox, Msg.Type.CD_MoneyJammed, msg.getDetails()));


        }
    }
    protected void handleEjectMoney(String  oneHundredAmount, String fiveHundrdAmount, String oneThousandAmount){
        log.info(id+": is ejecting money");

    }
    protected void handleEnquiryMoney(){
        log.info(id+": is answering enquiry");

    }
    protected void handleMoneyJammed(){

    }
}
