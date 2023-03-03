package sg.edu.nus.iss.app.day30assessment.models;


public class Success {
    
    private String transactionalId;
    private String date;
    private final TransferDetails transferDetails;

    public String getTransactionalId() {
        return transactionalId;
    }

    public void setTransactionalId(String transactionalId) {
        transactionalId = transactionalId;
    }

    public TransferDetails getTransferDetails() {
        return transferDetails;
    }

    public Success(TransferDetails transferDetails) {
        this.transferDetails = transferDetails;
    }
    
    
}
