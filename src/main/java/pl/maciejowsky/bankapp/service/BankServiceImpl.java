package pl.maciejowsky.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maciejowsky.bankapp.dao.BankSettingsDAO;
import pl.maciejowsky.bankapp.model.BankAnnouncement;
import pl.maciejowsky.bankapp.model.BankDepositSettings;
import pl.maciejowsky.bankapp.model.BankSettings;
import pl.maciejowsky.bankapp.model.BankTransferSettings;
import pl.maciejowsky.bankapp.model.enums.AnnouncementVisibility;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        announcements.stream().forEach(announcement ->
        {
            if (announcement.getContent().length() < 100) return;
            announcement.setShortenedContent(announcement.getContent().substring(0, 100));

        });
        return announcements;
    }

    @Override
    public List<BankAnnouncement> getBankAnnouncements(String roleOfConsumer) {
        Set<AnnouncementVisibility> announcementVisibilities = new HashSet<>();

        System.out.println("ROLE " + roleOfConsumer);
        System.out.println("ROLE " + roleOfConsumer);
        System.out.println("ROLE " + roleOfConsumer);
        switch (roleOfConsumer) {
            case "user":
                announcementVisibilities.add(AnnouncementVisibility.USERS);
                announcementVisibilities.add(AnnouncementVisibility.MEMBERS);
                break;
            case "manager":
                announcementVisibilities.add(AnnouncementVisibility.MANAGERS);
                announcementVisibilities.add(AnnouncementVisibility.MEMBERS);
                break;
            case "guest":
                announcementVisibilities.add(AnnouncementVisibility.GUESTS);
                break;

        }

        List<BankAnnouncement> announcements = getBankAnnouncements().stream()
                .filter(announcement -> announcementVisibilities.contains(announcement.getAnnouncementVisibility()))
                .collect(Collectors.toList());


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
