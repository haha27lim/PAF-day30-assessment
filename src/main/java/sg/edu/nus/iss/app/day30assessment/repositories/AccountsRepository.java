package sg.edu.nus.iss.app.day30assessment.repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.app.day30assessment.models.Account;


import static sg.edu.nus.iss.app.day30assessment.repositories.Queries.*;


@Repository
public class AccountsRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Account> findAll() {
        List<Account> resultList = new ArrayList<Account>();
        resultList = jdbcTemplate.query(SQL_SELECT_ALL_ACCOUNTS, BeanPropertyRowMapper.newInstance(Account.class));
        return resultList;
    }


    public Account findByAccountId(String accountId) {
        return jdbcTemplate.queryForObject(SQL_SELECT_ACCOUNTS_BY_ID, BeanPropertyRowMapper.newInstance(Account.class), accountId);
    }

  
    public void update(Account account) {
        jdbcTemplate.update(SQL_UPDATE_ACCOUNTS_BY_ID, account.getName(), account.getBalance(), account.getAccountId());
    }

    // May throw unchecked exception DataAccessException
    public boolean withdraw(String accountId, BigDecimal balance) {
        final int rowCount= jdbcTemplate.update(
            "update account set balance = balance - ? where account_id= ?",
            balance, accountId);
        return rowCount> 0;
        }

    public boolean deposit(String accountId, BigDecimal balance) {
        final int rowCount= jdbcTemplate.update(
           "update account set balance = balance + ? where account_id= ?",
            balance, accountId);
        return rowCount> 0;
    }

    public Optional<BigDecimal> getBalance(String accountId) {
        final String sql = "SELECT balance FROM account WHERE account_id = ?";
        try {
            BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, accountId);
            return Optional.ofNullable(balance);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }


}
