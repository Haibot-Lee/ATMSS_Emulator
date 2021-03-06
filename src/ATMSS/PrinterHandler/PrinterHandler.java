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
            case P_PrintAdvice:
                handlePrintAdvice(msg);
                break;

            case P_Reset:
                handleReset(msg);
                break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    } // processMsg

    // handlePrintAdvice
    protected void handlePrintAdvice(Msg msg) {

    } // handlePrintAdvice

    // handleReset
    protected void handleReset(Msg msg) {
    } // handleReset
} // PrinterHandler
