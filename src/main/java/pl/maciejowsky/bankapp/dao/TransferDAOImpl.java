package pl.maciejowsky.bankapp.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pl.maciejowsky.bankapp.mappers.ContactMapper;
import pl.maciejowsky.bankapp.mappers.TransferMapper;
import pl.maciejowsky.bankapp.model.Contact;
import pl.maciejowsky.bankapp.model.FormTransfer;
import pl.maciejowsky.bankapp.model.Transfer;

import java.sql.Timestamp;
import java.util.List;

@Component
public class TransferDAOImpl implements TransferDAO {
    private final JdbcTemplate jdbcTemplate;

    public TransferDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer getTransferInformation(int transferId) {
        Transfer transfer;
        try {
            String sql = "SELECT * FROM transfers WHERE id = ?";
            transfer = jdbcTemplate.queryForObject(sql, new TransferMapper(), transferId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return transfer;
    }

    @Override
    public List<Transfer> findTransfersForUser(String accountNumber) {
        String sql = "SELECT * FROM transfers WHERE from_account=? OR to_account = ? ORDER BY date_of_receiving DESC";
        return jdbcTemplate.query(sql, new TransferMapper(), accountNumber,accountNumber);

    }

    @Override
    public void saveTransfer(FormTransfer transfer) {
        String sql = "INSERT INTO transfers(from_account,to_account,amount, date_of_execution,date_of_receiving,transfer_type) " +
                " VALUES(?,?,?,?,?,?)";
        Timestamp receiveAt = Timestamp.valueOf(transfer.getReceiveAt());
        Timestamp sentAt = Timestamp.valueOf(transfer.getSentAt());

        jdbcTemplate.update(sql,
                transfer.getFromAccount(),
                transfer.getToAccount(),
                transfer.getAmount(),
                sentAt,
                receiveAt,
                transfer.getTransferType().name().toLowerCase()
        );
    }


    @Override
    public List<Contact> findUserContacts(int userId) {
        List<Contact> contactsList;
        try {
            String sql = "SELECT name, account_number, fk_user_contact_owner_id FROM contacts WHERE fk_user_contact_owner_id=? ";
            contactsList = jdbcTemplate.query(sql, new ContactMapper(), userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return contactsList;
    }

    @Override
    public void saveContact(Contact contact) {
        String sql = "INSERT INTO contacts(name, account_number,fk_user_contact_owner_id) " +
                " VALUES(?,?,?)";
        jdbcTemplate.update(sql,
                contact.getName(),
                contact.getAccountNumber(),
                contact.getContactOwnerId()
        );
    }
}
