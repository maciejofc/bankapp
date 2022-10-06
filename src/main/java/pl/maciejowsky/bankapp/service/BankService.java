package pl.maciejowsky.bankapp.service;

import pl.maciejowsky.bankapp.model.BankAnnouncement;
import pl.maciejowsky.bankapp.model.BankDepositSettings;
import pl.maciejowsky.bankapp.model.BankSettings;
import pl.maciejowsky.bankapp.model.BankTransferSettings;

import java.util.List;

public interface BankService {
    BankSettings getSettings();

    List<BankAnnouncement> getBankAnnouncements();

    void addAnnouncement(BankAnnouncement bankAnnouncement);

    void addDepositVariant(BankDepositSettings bankDepositSettings);

    void deleteDepositVariant(int depositVariantId);

    void changeTransferSettings(BankTransferSettings bankTransferSettings);

    void deleteAnnouncement(int announcementId);
}
