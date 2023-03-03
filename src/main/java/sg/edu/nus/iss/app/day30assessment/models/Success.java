package sg.edu.nus.iss.app.day30assessment.models;


public class Success {
    
    private String TransactionalId;

    private final TransferDetails transferDetails;

    public String getTransactionalId() {
        return TransactionalId;
    }

    public void setTransactionalId(String transactionalId) {
        TransactionalId = transactionalId;
    }

    public TransferDetails getTransferDetails() {
        return transferDetails;
    }

    public Success(TransferDetails transferDetails) {
        this.transferDetails = transferDetails;
    }
    
    
}
