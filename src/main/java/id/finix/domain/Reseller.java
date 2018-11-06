package id.finix.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rs")
public class Reseller {
    @Id
    private String id;

    @JsonIgnore
    @Column(name = "pin")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "subdealer_id")
    private String subdealerId;

    @Column(name = "balance", updatable = false)
    private Double balance;

    @Column(name = "max_real_liabilities", updatable = false)
    private Double trxFee;

    @Column(name = "status")
    private int status;

    @Column(name = "liabilities", updatable = false)
    private Double liabilities;

    @Column(name = "unique_code")
    private String uniqueCode;

    @Column(name = "gender")
    private String gender;

    @Column(name = "points", updatable = false)
    private int points;

    @Column(name = "upline")
    private String upline;

    @Column(name = "use_fingerprint")
    private String useFingerprint;

    @Column(name = "email")
    private String email;

    @Column(name = "birth_day")
    private String birthDate;

    @Column(name = "paypal_trusted")
    private String verified;

    @Column(name = "facebook_info")
    private String facebookInfo;

    @Column(name = "datetime_join")
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinedDate;

    @Column(name = "channel_hp")
    private String mobileNumber;

    @Column(name = "channel_ym")
    private String telegramUsername;

    @Column(name = "pics")
    private String profilePicture;

    @Column(name = "identity")
    private String identityNumber;

    @Column(name = "identity_pics")
    private String identityPhoto;

    @Column(name = "province")
    private String province;

    @Column(name = "location")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "regional_id")
    private String zipCode;

    @Column(name = "district")
    private String district;

    @Column(name = "subdistrict")
    private String subdistrict;

    @Column(name = "user")
    private String user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Double getLiabilities() {
        return liabilities;
    }

    public void setLiabilities(Double liabilities) {
        this.liabilities = liabilities;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Double getTrxFee() {
        return trxFee;
    }

    public void setTrxFee(Double trxFee) {
        this.trxFee = trxFee;
    }

    public String getSubdealerId() {
        return subdealerId;
    }

    public void setSubdealerId(String subdealerId) {
        this.subdealerId = subdealerId;
    }

    public String getUpline() {
        return upline;
    }

    public void setUpline(String upline) {
        this.upline = upline;
    }

    public String getUseFingerprint() {
        return useFingerprint;
    }

    public void setUseFingerprint(String useFingerprint) {
        this.useFingerprint = useFingerprint;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getFacebookInfo() {
        return facebookInfo;
    }

    public void setFacebookInfo(String facebookInfo) {
        this.facebookInfo = facebookInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getTelegramUsername() {
        return telegramUsername;
    }

    public void setTelegramUsername(String telegramUsername) {
        this.telegramUsername = telegramUsername;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getIdentityPhoto() {
        return identityPhoto;
    }

    public void setIdentityPhoto(String identityPhoto) {
        this.identityPhoto = identityPhoto;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubdistrict() {
        return subdistrict;
    }

    public void setSubdistrict(String subdistrict) {
        this.subdistrict = subdistrict;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
