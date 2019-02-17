package id.finix.services.user;

public class UpdateAccountRequest {

    private String name;
    private String address;
    private String province;
    private String city;
    private String district;
    private String subdistrict;
    private String zipcode;
    private String gender;
    private String identityNo;
    private String email;
    private String mobileNumber;
    private String birthDate;
    private String referralCode;
    private String h2hAccount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getH2hAccount() {
        return h2hAccount;
    }

    public void setH2hAccount(String h2hAccount) {
        this.h2hAccount = h2hAccount;
    }

    @Override
    public String toString() {
        return "UpdateAccountRequest{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", subdistrict='" + subdistrict + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", gender='" + gender + '\'' +
                ", identityNo='" + identityNo + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", referralCode='" + referralCode + '\'' +
                ", h2hAccount='" + h2hAccount + '\'' +
                '}';
    }
}
