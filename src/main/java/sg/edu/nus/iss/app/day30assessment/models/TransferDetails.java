package sg.edu.nus.iss.app.day30assessment.models;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDetails {
    
    @NotNull(message = "From account cannot be null")
    @Size(min = 10, max = 10, message = "From account must be 10 characters long")
    private String fromAccount;

    @NotNull(message = "To account cannot be null")
    @Size(min = 10, max = 10, message = "To account must be 10 characters long")
    private String toAccount;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "10.00", message = "Transfer amount must be at least $10")
    private BigDecimal transferAmount;

    @Size(max = 255, message = "Comments cannot exceed 255 characters")
    private String comments;

}
