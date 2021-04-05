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
                handlePrintAdvice();
                handlePrintAdvice(msg);
                break;
            case P_Reset:
                handleReset();
                handleReset(msg);
                break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    } // processMsg


    //------------------------------------------------------------
    // handlePrintAdvice
    protected void handlePrintAdvice() {
        log.info(id + ": print advice");
    } // handlePrintAdvice

    // handlePrintAdvice with msg
    protected void handlePrintAdvice(Msg msg) {
    } // handlePrintAdvice with msg

    //------------------------------------------------------------
    // handleReset
    protected void handleReset() {
        log.info(id + ": reset printer");
    } // handleReset

    // handleReset with msg
    protected void handleReset(Msg msg) {
    } // handleReset with msg
} // PrinterHandler
