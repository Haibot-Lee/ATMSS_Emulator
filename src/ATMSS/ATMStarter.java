package ATMSS;

import AppKickstarter.AppKickstarter;

public class ATMStarter extends AppKickstarter {
    /**
     * Constructor for AppKickstarter (default log file NOT appending)
     *
     * @param id       name of the application
     * @param cfgFName file name of the configuration file
     */
    public ATMStarter(String id, String cfgFName) {
        super("ATM", cfgFName);
    }

    @Override
    protected void startApp() {

    }

    @Override
    protected void stopApp() {

    }
}
