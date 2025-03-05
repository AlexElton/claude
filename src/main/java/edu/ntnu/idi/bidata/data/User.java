package edu.ntnu.idi.bidata.data;

import java.time.LocalDate;

/**
 * User entity class representing a user in the system
 */
public class User {
  private int userId;
  private String email;
  private String password;
  private String fullName;
  private LocalDate dateOfBirth;
  private String householdId;
  private int phoneNumber;
  private byte[] salt; // For backward compatibility with your original code

  // Constructors
  public User() {
    // Default constructor
  }

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public User(String email, String password, String fullName, LocalDate dateOfBirth,
      String householdId, int phoneNumber) {
    this.email = email;
    this.password = password;
    this.fullName = fullName;
    this.dateOfBirth = dateOfBirth;
    this.householdId = householdId;
    this.phoneNumber = phoneNumber;
  }

  // Getters and Setters
  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
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

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getHouseholdId() {
    return householdId;
  }

  public void setHouseholdId(String householdId) {
    this.householdId = householdId;
  }

  public int getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(int phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public byte[] getSalt() {
    return salt;
  }

  public void setSalt(byte[] salt) {
    this.salt = salt;
  }

  @Override
  public String toString() {
    return "User{" +
        "userId=" + userId +
        ", email='" + email + '\'' +
        ", fullName='" + fullName + '\'' +
        ", dateOfBirth=" + dateOfBirth +
        ", householdId='" + householdId + '\'' +
        ", phoneNumber=" + phoneNumber +
        '}';
  }
}