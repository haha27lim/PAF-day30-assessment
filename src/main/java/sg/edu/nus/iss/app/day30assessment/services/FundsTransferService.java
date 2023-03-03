package sg.edu.nus.iss.app.day30assessment.services;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.app.day30assessment.models.Success;
import sg.edu.nus.iss.app.day30assessment.models.TransferDetails;
import sg.edu.nus.iss.app.day30assessment.repositories.AccountsRepository;

@Service
public class FundsTransferService {
    
    @Autowired
    private AccountsRepository acctRepo;
    
    @Transactional
    public void transfer(String fromAccount, String toAccount, BigDecimal transferAmount) {
        final Optional<BigDecimal> optFrom = acctRepo.getBalance(fromAccount);
        final Optional<BigDecimal> optTo = acctRepo.getBalance(toAccount);

        if (optFrom.isEmpty() || optTo.isEmpty() || optFrom.get().compareTo(transferAmount) < 0) {
            throw new IllegalArgumentException("Incorrect parameters");
        }

        boolean withdrawResult = acctRepo.withdraw(fromAccount, transferAmount);
        boolean depositResult = acctRepo.deposit(toAccount, transferAmount);

        if (!(withdrawResult || depositResult)) {
            throw new DataAccessResourceFailureException("Cannot perform transfer");
        }
    }  
    
    public Success transfersuccess(TransferDetails transferDetails) {
        String transactionId = UUID.randomUUID().toString().substring(0, 8);
        Success success = new Success(transferDetails);
        success.setTransactionalId(transactionId);
        return success;
    }
}
