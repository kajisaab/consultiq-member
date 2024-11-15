package global.kajisaab.feature.consultancy.entity;

import global.kajisaab.common.db.DbEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultancy")
public class ConsultancyEntity extends DbEntity {

    private String consultancyCode;

    private String registeredCompanyName;

    private String businessName;

    private String businessRegistrationNumber;

    private String businessEmail;

    private String businessLandlineNumber;

    private Integer userRegistrationCount; // Number of users registered under this consultancy

    private String status;

    private String website;

    private String logo;

    private String country;

    private String state;

    private String district;

    private String municipality;

    private String ward;

    private String street;

    private LocalDateTime enrolledOn;

    private String parentId;

    private String makerName;

    private String makerEmail;

    private String businessContactPersonName;

    private String businessContactPersonMobileNumber;

    private String businessContactPersonEmail;

    private String businessContactPersonLandline;

    private String businessContactPersonExtension;

    private String emergencyContactPersonName;

    private String emergencyContactPersonEmail;

    private String emergencyContactPersonMobileNumber;

    private String emergencyContactPersonLandline;

    private String emergencyContactPersonExtension;

    public String getConsultancyCode() {
        return consultancyCode;
    }

    public void setConsultancyCode(String consultancyCode) {
        this.consultancyCode = consultancyCode;
    }

    public String getRegisteredCompanyName() {
        return registeredCompanyName;
    }

    public void setRegisteredCompanyName(String registeredCompanyName) {
        this.registeredCompanyName = registeredCompanyName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessRegistrationNumber() {
        return businessRegistrationNumber;
    }

    public void setBusinessRegistrationNumber(String businessRegistrationNumber) {
        this.businessRegistrationNumber = businessRegistrationNumber;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getBusinessLandlineNumber() {
        return businessLandlineNumber;
    }

    public void setBusinessLandlineNumber(String businessLandlineNumber) {
        this.businessLandlineNumber = businessLandlineNumber;
    }

    public Integer getUserRegistrationCount() {
        return userRegistrationCount;
    }

    public void setUserRegistrationCount(Integer userRegistrationCount) {
        this.userRegistrationCount = userRegistrationCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public LocalDateTime getEnrolledOn() {
        return enrolledOn;
    }

    public void setEnrolledOn(LocalDateTime enrolledOn) {
        this.enrolledOn = enrolledOn;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }

    public String getMakerEmail() {
        return makerEmail;
    }

    public void setMakerEmail(String makerEmail) {
        this.makerEmail = makerEmail;
    }

    public String getBusinessContactPersonName() {
        return businessContactPersonName;
    }

    public void setBusinessContactPersonName(String businessContactPersonName) {
        this.businessContactPersonName = businessContactPersonName;
    }

    public String getBusinessContactPersonMobileNumber() {
        return businessContactPersonMobileNumber;
    }

    public void setBusinessContactPersonMobileNumber(String businessContactPersonMobileNumber) {
        this.businessContactPersonMobileNumber = businessContactPersonMobileNumber;
    }

    public String getBusinessContactPersonEmail() {
        return businessContactPersonEmail;
    }

    public void setBusinessContactPersonEmail(String businessContactPersonEmail) {
        this.businessContactPersonEmail = businessContactPersonEmail;
    }

    public String getBusinessContactPersonLandline() {
        return businessContactPersonLandline;
    }

    public void setBusinessContactPersonLandline(String businessContactPersonLandline) {
        this.businessContactPersonLandline = businessContactPersonLandline;
    }

    public String getBusinessContactPersonExtension() {
        return businessContactPersonExtension;
    }

    public void setBusinessContactPersonExtension(String businessContactPersonExtension) {
        this.businessContactPersonExtension = businessContactPersonExtension;
    }

    public String getEmergencyContactPersonName() {
        return emergencyContactPersonName;
    }

    public void setEmergencyContactPersonName(String emergencyContactPersonName) {
        this.emergencyContactPersonName = emergencyContactPersonName;
    }

    public String getEmergencyContactPersonEmail() {
        return emergencyContactPersonEmail;
    }

    public void setEmergencyContactPersonEmail(String emergencyContactPersonEmail) {
        this.emergencyContactPersonEmail = emergencyContactPersonEmail;
    }

    public String getEmergencyContactPersonMobileNumber() {
        return emergencyContactPersonMobileNumber;
    }

    public void setEmergencyContactPersonMobileNumber(String emergencyContactPersonMobileNumber) {
        this.emergencyContactPersonMobileNumber = emergencyContactPersonMobileNumber;
    }

    public String getEmergencyContactPersonLandline() {
        return emergencyContactPersonLandline;
    }

    public void setEmergencyContactPersonLandline(String emergencyContactPersonLandline) {
        this.emergencyContactPersonLandline = emergencyContactPersonLandline;
    }

    public String getEmergencyContactPersonExtension() {
        return emergencyContactPersonExtension;
    }

    public void setEmergencyContactPersonExtension(String emergencyContactPersonExtension) {
        this.emergencyContactPersonExtension = emergencyContactPersonExtension;
    }
}
