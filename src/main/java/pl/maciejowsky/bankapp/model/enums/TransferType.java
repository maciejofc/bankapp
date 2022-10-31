package pl.maciejowsky.bankapp.model.enums;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.maciejowsky.bankapp.dao.BankSettingsDAO;
import pl.maciejowsky.bankapp.model.BankTransferSettings;

import javax.annotation.PostConstruct;

public enum TransferType {


    INSTANT(0),

    EXPRESS(30000),

    NORMAL(60000);

    @Component
    public static class TransferFeeTypeDAOInjector {
        @Autowired
        private BankSettingsDAO bankSettingsDAO;

        @PostConstruct
        public void postConstruct() {
            BankTransferSettings bankSettings = bankSettingsDAO.findCurrentSettings().getBankTransferSettings();

            TransferType.valueOf("INSTANT").setFee(bankSettings.getTransferInstant());
            TransferType.valueOf("EXPRESS").setFee(bankSettings.getTransferExpress());
            TransferType.valueOf("NORMAL").setFee(bankSettings.getTransferNormal());
        }
    }


    private int fee;
    private int timeOfSendingInMiliSec;

    TransferType(int timeOfSendingInMiliSec) {
        this.timeOfSendingInMiliSec = timeOfSendingInMiliSec;
    }

    public int getFee() {
        return fee;
    }

    public int getTimeOfSendingInMiliSec() {
        return timeOfSendingInMiliSec;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }


}
