package global.kajisaab.feature.consultancy.usecase.request;

import global.kajisaab.core.usecase.UseCaseRequest;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Serdeable
public class ConsultancyOnboardingUseCaseRequest implements UseCaseRequest {

    @NotBlank(message = "Registered company name is required")
    private String registeredCompanyName;

    @NotBlank(message = "Business name is required")
    private String businessName;

    @NotBlank(message = "Business registration number is required")
    private String businessRegistrationNumber;

    @NotBlank(message = "Business email is required")
    @Email(message = "Please enter a valid Business Email Address")
    private String businessEmail;

    @NotBlank(message = "Business Landline Number")
    private String businessLandlineNumber;

    @NotNull(message = "User registration count is required")
    private Integer userRegistrationCount;

    private String website;

    @NotBlank(message = "Logo is required")
    private String logo;

    private String parentId;

    @NotBlank(message = "Country is required")
    private String country;

    @NotNull(message = "State is required")
    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "Municipality is required")
    private String municipality;

    @NotBlank(message = "Ward is required")
    private String ward;

    private String street;

    @NotBlank(message = "Maker name is required.")
    private String makerName;

    @NotBlank(message = "Maker email is required.")
    @Email(message = "Maker email must be a valid email address.")
    private String makerEmail;

    @NotBlank(message = "Business contact person name is required.")
    private String businessContactPersonName;

    @NotBlank(message = "Business contact person mobile number is required.")
    @Pattern(regexp = "\\d{10}", message = "Business contact person mobile number must be a valid 10-digit number.")
    private String businessContactPersonMobileNumber;

    @NotBlank(message = "Business contact person email is required.")
    @Email(message = "Business contact person email must be a valid email address.")
    private String businessContactPersonEmail;

    @NotBlank(message = "Business contact person landline is required.")
    private String businessContactPersonLandline;

    @NotBlank(message = "Business contact person extension is required.")
    private String businessContactPersonExtension;

    @NotBlank(message = "Emergency contact person name is required.")
    private String emergencyContactPersonName;

    @NotBlank(message = "Emergency contact person email is required.")
    @Email(message = "Emergency contact person email must be a valid email address.")
    private String emergencyContactPersonEmail;

    @NotBlank(message = "Emergency contact person mobile number is required.")
    @Pattern(regexp = "\\d{10}", message = "Emergency contact person mobile number must be a valid 10-digit number.")
    private String emergencyContactPersonMobileNumber;

    @NotBlank(message = "Emergency contact person landline is required.")
    private String emergencyContactPersonLandline;

    @NotBlank(message = "Emergency contact person extension is required.")
    private String emergencyContactPersonExtension;

    public @NotBlank(message = "Registered company name is required") String getRegisteredCompanyName() {
        return registeredCompanyName;
    }

    public void setRegisteredCompanyName(@NotBlank(message = "Registered company name is required") String registeredCompanyName) {
        this.registeredCompanyName = registeredCompanyName;
    }

    public @NotBlank(message = "Business name is required") String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(@NotBlank(message = "Business name is required") String businessName) {
        this.businessName = businessName;
    }

    public @NotBlank(message = "Business registration number is required") String getBusinessRegistrationNumber() {
        return businessRegistrationNumber;
    }

    public void setBusinessRegistrationNumber(@NotBlank(message = "Business registration number is required") String businessRegistrationNumber) {
        this.businessRegistrationNumber = businessRegistrationNumber;
    }

    public @NotBlank(message = "Business email is required") @Email(message = "Please enter a valid Business Email Address") String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(@NotBlank(message = "Business email is required") @Email(message = "Please enter a valid Business Email Address") String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public @NotNull(message = "User registration count is required") Integer getUserRegistrationCount() {
        return userRegistrationCount;
    }

    public void setUserRegistrationCount(@NotNull(message = "User registration count is required") Integer userRegistrationCount) {
        this.userRegistrationCount = userRegistrationCount;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public @NotBlank(message = "Logo is required") String getLogo() {
        return logo;
    }

    public void setLogo(@NotBlank(message = "Logo is required") String logo) {
        this.logo = logo;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public @NotBlank(message = "Country is required") String getCountry() {
        return country;
    }

    public void setCountry(@NotBlank(message = "Country is required") String country) {
        this.country = country;
    }

    public @NotNull(message = "State is required") @NotBlank(message = "State is required") String getState() {
        return state;
    }

    public void setState(@NotNull(message = "State is required") @NotBlank(message = "State is required") String state) {
        this.state = state;
    }

    public @NotBlank(message = "District is required") String getDistrict() {
        return district;
    }

    public void setDistrict(@NotBlank(message = "District is required") String district) {
        this.district = district;
    }

    public @NotBlank(message = "Municipality is required") String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(@NotBlank(message = "Municipality is required") String municipality) {
        this.municipality = municipality;
    }

    public @NotBlank(message = "Ward is required") String getWard() {
        return ward;
    }

    public void setWard(@NotBlank(message = "Ward is required") String ward) {
        this.ward = ward;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public @NotBlank(message = "Maker name is required.") String getMakerName() {
        return makerName;
    }

    public void setMakerName(@NotBlank(message = "Maker name is required.") String makerName) {
        this.makerName = makerName;
    }

    public @NotBlank(message = "Maker email is required.") @Email(message = "Maker email must be a valid email address.") String getMakerEmail() {
        return makerEmail;
    }

    public void setMakerEmail(@NotBlank(message = "Maker email is required.") @Email(message = "Maker email must be a valid email address.") String makerEmail) {
        this.makerEmail = makerEmail;
    }

    public @NotBlank(message = "Business contact person name is required.") String getBusinessContactPersonName() {
        return businessContactPersonName;
    }

    public void setBusinessContactPersonName(@NotBlank(message = "Business contact person name is required.") String businessContactPersonName) {
        this.businessContactPersonName = businessContactPersonName;
    }

    public @NotBlank(message = "Business contact person mobile number is required.") @Pattern(regexp = "\\d{10}", message = "Business contact person mobile number must be a valid 10-digit number.") String getBusinessContactPersonMobileNumber() {
        return businessContactPersonMobileNumber;
    }

    public void setBusinessContactPersonMobileNumber(@NotBlank(message = "Business contact person mobile number is required.") @Pattern(regexp = "\\d{10}", message = "Business contact person mobile number must be a valid 10-digit number.") String businessContactPersonMobileNumber) {
        this.businessContactPersonMobileNumber = businessContactPersonMobileNumber;
    }

    public @NotBlank(message = "Business contact person email is required.") @Email(message = "Business contact person email must be a valid email address.") String getBusinessContactPersonEmail() {
        return businessContactPersonEmail;
    }

    public void setBusinessContactPersonEmail(@NotBlank(message = "Business contact person email is required.") @Email(message = "Business contact person email must be a valid email address.") String businessContactPersonEmail) {
        this.businessContactPersonEmail = businessContactPersonEmail;
    }

    public @NotBlank(message = "Business contact person landline is required.") String getBusinessContactPersonLandline() {
        return businessContactPersonLandline;
    }

    public void setBusinessContactPersonLandline(@NotBlank(message = "Business contact person landline is required.") String businessContactPersonLandline) {
        this.businessContactPersonLandline = businessContactPersonLandline;
    }

    public @NotBlank(message = "Business contact person extension is required.") String getBusinessContactPersonExtension() {
        return businessContactPersonExtension;
    }

    public void setBusinessContactPersonExtension(@NotBlank(message = "Business contact person extension is required.") String businessContactPersonExtension) {
        this.businessContactPersonExtension = businessContactPersonExtension;
    }

    public @NotBlank(message = "Emergency contact person name is required.") String getEmergencyContactPersonName() {
        return emergencyContactPersonName;
    }

    public void setEmergencyContactPersonName(@NotBlank(message = "Emergency contact person name is required.") String emergencyContactPersonName) {
        this.emergencyContactPersonName = emergencyContactPersonName;
    }

    public @NotBlank(message = "Emergency contact person email is required.") @Email(message = "Emergency contact person email must be a valid email address.") String getEmergencyContactPersonEmail() {
        return emergencyContactPersonEmail;
    }

    public void setEmergencyContactPersonEmail(@NotBlank(message = "Emergency contact person email is required.") @Email(message = "Emergency contact person email must be a valid email address.") String emergencyContactPersonEmail) {
        this.emergencyContactPersonEmail = emergencyContactPersonEmail;
    }

    public @NotBlank(message = "Emergency contact person mobile number is required.") @Pattern(regexp = "\\d{10}", message = "Emergency contact person mobile number must be a valid 10-digit number.") String getEmergencyContactPersonMobileNumber() {
        return emergencyContactPersonMobileNumber;
    }

    public void setEmergencyContactPersonMobileNumber(@NotBlank(message = "Emergency contact person mobile number is required.") @Pattern(regexp = "\\d{10}", message = "Emergency contact person mobile number must be a valid 10-digit number.") String emergencyContactPersonMobileNumber) {
        this.emergencyContactPersonMobileNumber = emergencyContactPersonMobileNumber;
    }

    public @NotBlank(message = "Emergency contact person landline is required.") String getEmergencyContactPersonLandline() {
        return emergencyContactPersonLandline;
    }

    public void setEmergencyContactPersonLandline(@NotBlank(message = "Emergency contact person landline is required.") String emergencyContactPersonLandline) {
        this.emergencyContactPersonLandline = emergencyContactPersonLandline;
    }

    public @NotBlank(message = "Emergency contact person extension is required.") String getEmergencyContactPersonExtension() {
        return emergencyContactPersonExtension;
    }

    public void setEmergencyContactPersonExtension(@NotBlank(message = "Emergency contact person extension is required.") String emergencyContactPersonExtension) {
        this.emergencyContactPersonExtension = emergencyContactPersonExtension;
    }

    public @NotBlank(message = "Business Landline Number") String getBusinessLandlineNumber() {
        return businessLandlineNumber;
    }

    public void setBusinessLandlineNumber(@NotBlank(message = "Business Landline Number") String businessLandlineNumber) {
        this.businessLandlineNumber = businessLandlineNumber;
    }
}
