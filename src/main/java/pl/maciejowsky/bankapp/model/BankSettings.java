package pl.maciejowsky.bankapp.model;

import java.util.List;

public class BankSettings {
    List<BankDepositSettings> bankDepositSettings;
    BankTransferSettings bankTransferSettings;

    public BankSettings(List<BankDepositSettings> bankDepositSettings, BankTransferSettings bankTransferSettings) {
        this.bankDepositSettings = bankDepositSettings;
        this.bankTransferSettings = bankTransferSettings;
    }

    public List<BankDepositSettings> getBankDepositSettings() {
        return bankDepositSettings;
    }

    public BankTransferSettings getBankTransferSettings() {
        return bankTransferSettings;
    }
}
