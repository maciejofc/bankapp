package pl.maciejowsky.bankapp.dao;

import pl.maciejowsky.bankapp.model.*;

import java.util.List;

public interface BankSettingsDAO {
    BankSettings findCurrentSettings();

    void insertDepositVariant(BankDepositSettings bankDepositSettings);
    void deleteDepositVariant(int depositVariantId);


    void insertTransferSettings(BankTransferSettings bankTransferSettings);

    List<BankAnnouncement> findAnnouncements();

    void deleteAnnouncementById(int announcementId);

    void insertAnnouncements(BankAnnouncement bankAnnouncement);


}
