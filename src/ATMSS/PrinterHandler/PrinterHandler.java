package ATMSS.PrinterHandler;

import ATMSS.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;


//======================================================================
// PrinterHandler
public class PrinterHandler extends HWHandler {
    //------------------------------------------------------------
    // PrinterHandler
    public PrinterHandler(String id, AppKickstarter appKickstarter) {
        super(id, appKickstarter);
    } // PrinterHandler


    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
        switch (msg.getType()) {
            /*
            case CR_CardInserted:
                atmss.send(new Msg(id, mbox, Msg.Type.CR_CardInserted, msg.getDetails()));
                break;
             */

            case P_AdviceAccept:
                handleAdviceAccept();
                break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    } // processMsg


    //------------------------------------------------------------
    // handleAdviceAccept
    protected void handleAdviceAccept() {
        log.info(id + ": advice accepted");
    } // handleAdviceAccept
} // PrinterHandler
