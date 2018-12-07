package id.finix.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "biller")
public class Biller {

    @Id
    @Column(name = "biller_id")
    private String id;

    @Column(name = "subcategory")
    private int subcategory;

    @Column(name = "description")
    private String description;

    @Column(name = "check_credit")
    private String processingCodes;

    @Column(name = "biller_code")
    private String billerCode;

    @Column(name = "status")
    private int status;

    @Column(name = "icon")
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(int subcategory) {
        this.subcategory = subcategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProcessingCodes() {
        return processingCodes;
    }

    public void setProcessingCodes(String processingCodes) {
        this.processingCodes = processingCodes;
    }

    public String getBillerCode() {
        return billerCode;
    }

    public void setBillerCode(String billerCode) {
        this.billerCode = billerCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
