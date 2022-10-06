package pl.maciejowsky.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maciejowsky.bankapp.dao.BankSettingsDAO;
import pl.maciejowsky.bankapp.model.BankAnnouncement;
import pl.maciejowsky.bankapp.model.BankDepositSettings;
import pl.maciejowsky.bankapp.model.BankSettings;
import pl.maciejowsky.bankapp.model.BankTransferSettings;

import java.util.List;

@Service
public class BankServiceImpl implements BankService {
    @Autowired
    BankSettingsDAO bankSettingsDAO;

    @Override
    public BankSettings getSettings() {
        return bankSettingsDAO.findCurrentSettings();
    }

    public List<BankAnnouncement> getBankAnnouncements() {
        List<BankAnnouncement> announcements = bankSettingsDAO.findAnnouncements();
        announcements.stream().forEach(element ->
        {
            if (element.getContent().length() < 50) return;
            element.setShortenedContent(element.getContent().substring(0, 50));

        });
        return announcements;
    }

    public void addAnnouncement(BankAnnouncement bankAnnouncement) {
        bankSettingsDAO.insertAnnouncements(bankAnnouncement);
    }

    @Override
    public void deleteAnnouncement(int announcementId) {
        bankSettingsDAO.deleteAnnouncementById(announcementId);
    }

    @Override
    public void addDepositVariant(BankDepositSettings bankDepositSettings) {
        bankSettingsDAO.insertDepositVariant(bankDepositSettings);
    }

    @Override
    public void deleteDepositVariant(int depositVariantId) {
        bankSettingsDAO.deleteDepositVariant(depositVariantId);
    }

    @Override
    public void changeTransferSettings(BankTransferSettings bankTransferSettings) {
        bankSettingsDAO.insertTransferSettings(bankTransferSettings);
    }
}
