package id.finix.services.topup;

public class TopupRequest {
    private String bankAccountId;
    private Integer amount;
    private String description;

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TopupRequest{" +
                "bankAccountId='" + bankAccountId + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
