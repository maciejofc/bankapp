package pl.maciejowsky.bankapp.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pl.maciejowsky.bankapp.mappers.BankAnnouncementMapper;
import pl.maciejowsky.bankapp.mappers.BankDepositSettingsMapper;
import pl.maciejowsky.bankapp.mappers.BankTransferSettingsMapper;
import pl.maciejowsky.bankapp.model.BankAnnouncement;
import pl.maciejowsky.bankapp.model.BankDepositSettings;
import pl.maciejowsky.bankapp.model.BankSettings;
import pl.maciejowsky.bankapp.model.BankTransferSettings;

import java.util.List;


@Component
public class BankSettingsDAOImpl implements BankSettingsDAO {

    private final JdbcTemplate jdbcTemplate;

    public BankSettingsDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BankSettings findCurrentSettings() {

        String sql1 = "select * from bank_deposit_settings";
        List<BankDepositSettings> bankDepositSettings = jdbcTemplate.query(sql1, new BankDepositSettingsMapper());

        String sql2 = "select * from bank_transfer_fee_settings where id = (select MAX(id) from bank_transfer_fee_settings)";
        BankTransferSettings bankTransferSettings = jdbcTemplate.queryForObject(sql2, new BankTransferSettingsMapper());

        return new BankSettings(bankDepositSettings, bankTransferSettings);
    }

    @Override
    public List<BankAnnouncement> findAnnouncements() {
        String sql = "select * from bank_announcement";
        return jdbcTemplate.query(sql, new BankAnnouncementMapper());

    }

    @Override
    public void insertAnnouncements(BankAnnouncement bankAnnouncement) {
        String sql = "insert into bank_announcement(content, visibility) " +
                " values(?,?)";

        jdbcTemplate.update(sql,
                bankAnnouncement.getContent(),
                bankAnnouncement.getAnnouncementVisibility().name().toLowerCase()
        );

    }

    @Override
    public void deleteAnnouncementById(int announcementId) {
        String sql = "delete from bank_announcement where id=?";
        jdbcTemplate.update(sql, announcementId);
    }

    @Override
    public void insertDepositVariant(BankDepositSettings bankDepositSettings) {
        String sql = "insert into bank_deposit_settings(min_deposit_time_in_min, name," +
                "minute_percentage_rate," +
                "min_amount,intended_for)" +
                "values (?,?,?,?,?)";

        System.out.println(bankDepositSettings.toString());
        jdbcTemplate.update(sql,
                bankDepositSettings.getMinDepositTime(),
                bankDepositSettings.getName(),
                bankDepositSettings.getMinPercentageRate(),
                bankDepositSettings.getMinAmount(),
                bankDepositSettings.getIntendedFor().name().toLowerCase()
        );
    }

    @Override
    public void deleteDepositVariant(int depositVariantId) {
        String sql = "delete from bank_deposit_settings where id=?";
        jdbcTemplate.update(sql, depositVariantId);
    }

    @Override
    public void insertTransferSettings(BankTransferSettings bankTransferSettings) {
        String sql = "insert into bank_transfer_fee_settings(transfer_express," +
                " transfer_fast_one_min," +
                " transfer_normal_two_min, discount_for_entrepreneur_in_percentage)" +
                " values (?,?,?,?)";

        jdbcTemplate.update(sql,
                bankTransferSettings.getTransferExpress(),
                bankTransferSettings.getTransferFast(),
                bankTransferSettings.getTransferNormal(),
                bankTransferSettings.getEntrepreneurDiscount()

        );
    }
}
