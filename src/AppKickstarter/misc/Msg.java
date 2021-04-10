package AppKickstarter.misc;


//======================================================================
// Msg
public class Msg {
    private String sender;
    private MBox senderMBox;
    private Type type;
    private String details;

    //------------------------------------------------------------
    // Msg

    /**
     * Constructor for a msg.
     *
     * @param sender     id of the msg sender (String)
     * @param senderMBox mbox of the msg sender
     * @param type       message type
     * @param details    details of the msg (free format String)
     */
    public Msg(String sender, MBox senderMBox, Type type, String details) {
        this.sender = sender;
        this.senderMBox = senderMBox;
        this.type = type;
        this.details = details;
    } // Msg


    //------------------------------------------------------------
    // getSender

    /**
     * Returns the id of the msg sender
     *
     * @return the id of the msg sender
     */
    public String getSender() {
        return sender;
    }


    //------------------------------------------------------------
    // getSenderMBox

    /**
     * Returns the mbox of the msg sender
     *
     * @return the mbox of the msg sender
     */
    public MBox getSenderMBox() {
        return senderMBox;
    }


    //------------------------------------------------------------
    // getType

    /**
     * Returns the message type
     *
     * @return the message type
     */
    public Type getType() {
        return type;
    }


    //------------------------------------------------------------
    // getDetails

    /**
     * Returns the details of the msg
     *
     * @return the details of the msg
     */
    public String getDetails() {
        return details;
    }


    //------------------------------------------------------------
    // toString

    /**
     * Returns the msg as a formatted String
     *
     * @return the msg as a formatted String
     */
    public String toString() {
        return sender + " (" + type + ") -- " + details;
    } // toString


    //------------------------------------------------------------
    // Msg Types

    /**
     * Message Types used in Msg.
     *
     * @see Msg
     */
    public enum Type {
        /**
         * Terminate the running thread
         */
        Terminate,
        /**
         * Generic error msg
         */
        Error,
        /**
         * Set a timer
         */
        SetTimer,
        /**
         * Set a timer
         */
        CancelTimer,
        /**
         * Timer clock ticks
         */
        Tick,
        /**
         * Time's up for the timer
         */
        TimesUp,
        /**
         * Health poll
         */
        Poll,
        /**
         * Health poll acknowledgement
         */
        PollAck,
        /**
         * Update Display
         */
        TD_UpdateDisplay,
        /**
         * Invalid Input
         */
        TD_InvalidInput,
        /**
         * Mouse Clicked
         */
        TD_MouseClicked,
        /**
         * Cash Deposit
         */
        TD_Deposit,
        /**
         * Text field of Passwords/Transfer Amount
         */
        TD_Passwords,
        TD_TransAmount,
        /**
         * Text filed of Money Withdrawal
         */
        TD_Withdrawal,
        /**
         * show the result of operations
         */
        TD_ShowResult,
        /**
         *  show all the accounts of the card
         */
        TD_showAccount,
        /**
         *  TouchDisplay start to accept input within time limit
         */
        TD_AcceptInput,
        /**
         *  TouchDisplay freeze
         */
        TD_Freeze,
        /**
         *  TouchDisplay is over time
         */
        TD_Overtime,
        /**
         * show message of transfer
         */
        TD_Message_transferFrom,
        TD_Message_transferTo,
        TD_Message_transferAmount,
        /**
         *  show message of deposit
         */
        TD_UpdateDepositDetails,
        /**
         * Card inserted
         */
        CR_CardInserted,
        /**
         * Card ejected
         */
        CR_CardEjected,
        /**
         * Card removed
         */
        CR_CardRemoved,
        /**
         * Eject card
         */
        CR_EjectCard,
        /**
         * Key pressed
         */
        CR_LockCard,
        /**
         * Accept password
         */
        KP_AcceptPassword,
        /**
         * Print advice
         */
        KP_KeyPressed,
        /**
         * Push up Keypad
         */
        KP_Overtime,
        /**
         * Keypad stop counting
         */
        KP_Freeze,
        /**
         * Push up Keypad
         */
        KP_PushUp,
        /**
         * Print advice
         */
        P_PrintAdvice,
        /**
         * Printer jammed
         */
        P_PrinterJammed,
        /**
         * Printer reset
         */
        P_Reset,
        /**
         * Buzzer stop alert
         */
        B_Stop,
        /**
         * Buzzer alert
         */
        B_Alert,
        /**
         * Eject money
         */
        CD_EjectMoney,
        /**
         * Cash Dispenser jammed
         */
        CD_MoneyJammed,
        /**
         * Enquiry Money
         */
        CD_EnquiryMoney,
        /**
         * Add the number of denomination
         */
        CD_AddDenomination,
        /**
         * Cash Deposit Collector
         */
        DC_Total,
        DC_MoneyDetails,
        DC_ButtonControl,


    } // Type
} // Msg
