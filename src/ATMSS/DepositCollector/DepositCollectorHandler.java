package ATMSS.DepositCollector;

import ATMSS.HWHandler.HWHandler;
import ATMSS.ATMSSStarter;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;

public class DepositCollectorHandler extends HWHandler {

    public DepositCollectorHandler(String id, AppKickstarter appKickstarter) {
        super(id, appKickstarter);
    }

    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
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

    } // processMsg

    private void handleMoneyJammed() {
    }

    protected void handleEjectMoney(String  oneHundredAmount, String fiveHundrdAmount, String oneThousandAmount){
        log.info(id+": is ejecting money");
    }
    protected void handleEnquiryMoney(){
        log.info(id+": is answering enquiry");

    }
}
