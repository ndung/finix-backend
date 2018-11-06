package id.finix.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rs_acc_log_temp")
public class Deposit {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "reseller_id")
    private String resellerId;

    @Column(name = "date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    @Column(name = "debit")
    private Double debit;

    @Column(name = "credit")
    private Double credit;

    @Column(name = "description")
    private String description;

    @Column(name = "user_deposit")
    private String userDeposit;

    @Column(name = "stan")
    private String stan;

    @Column(name = "flag")
    private int flag;

    @Column(name = "status")
    private int status;

    @Column(name = "proof")
    private String proof;

    @Column(name = "payment_option")
    private String paymentOption;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResellerId() {
        return resellerId;
    }

    public void setResellerId(String resellerId) {
        this.resellerId = resellerId;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserDeposit() {
        return userDeposit;
    }

    public void setUserDeposit(String userDeposit) {
        this.userDeposit = userDeposit;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }
}
