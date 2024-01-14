package com.project.astro.astrology.payload.request;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public class SignupRequest {

  @Size(max = 20)
  private String firstName;

  @Size(max = 20)
  private String lastName;

  private String gender;

  @NotBlank
  @Size(max = 20)
  private String username;

  @Size(max = 50)
  @Email
  private String email;

  @Size(max = 40)
  private String password;

  private Long mobile;

  private String dob;

  private String birthTime;

  private String birthPlace;

  private String state;

  private String city;

  private String qualification;

  private String experience;

  private String astrologerService;

  private String fee;

  @JsonProperty
  private Boolean isAstrologer;

  @JsonProperty
  private Boolean isAdmin;

  @JsonProperty
  private Boolean isApproved;

  @JsonProperty
  private Boolean isMobileVerified;

  public Boolean getMobileVerified() {
    return isMobileVerified;
  }

  public void setMobileVerified(Boolean mobileVerified) {
    isMobileVerified = mobileVerified;
  }

  private Set<String> role;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Long getMobile() {
    return mobile;
  }

  public void setMobile(Long mobile) {
    this.mobile = mobile;
  }

  public String getDob() {
    return dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public String getBirthTime() {
    return birthTime;
  }

  public void setBirthTime(String birthTime) {
    this.birthTime = birthTime;
  }

  public String getBirthPlace() {
    return birthPlace;
  }

  public void setBirthPlace(String birthPlace) {
    this.birthPlace = birthPlace;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getQualification() {
    return qualification;
  }

  public void setQualification(String qualification) {
    this.qualification = qualification;
  }

  public String getExperience() {
    return experience;
  }

  public void setExperience(String experience) {
    this.experience = experience;
  }

  public String getAstrologerService() {
    return astrologerService;
  }

  public void setAstrologerService(String astrologerService) {
    this.astrologerService = astrologerService;
  }

  public String getFee() {
    return fee;
  }

  public void setFee(String fee) {
    this.fee = fee;
  }

  public Boolean getAstrologer() {
    return isAstrologer;
  }

  public void setAstrologer(Boolean astrologer) {
    isAstrologer = astrologer;
  }

  public Boolean getAdmin() {
    return isAdmin;
  }

  public void setAdmin(Boolean admin) {
    isAdmin = admin;
  }

  public Boolean getApproved() {
    return isApproved;
  }

  public void setApproved(Boolean approved) {
    isApproved = approved;
  }

  public Set<String> getRole() {
    return role;
  }

  public void setRole(Set<String> role) {
    this.role = role;
  }
}
