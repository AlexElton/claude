package edu.ntnu.idi.bidata.dao;

import edu.ntnu.idi.bidata.data.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import static edu.ntnu.idi.bidata.dao.DBConnectionProvider.close;

/**
 * Data access object for User
 */
public class UserDAO {
  private DBConnectionProvider connectionProvider;

  public UserDAO(DBConnectionProvider connectionProvider) {
    this.connectionProvider = connectionProvider;
  }

  /**
   * Returns a List of all registered users
   * @return List of User
   */
  public List<User> getUsers() {
    List<User> users = new ArrayList<>();
    PreparedStatement preparedStatement = null;
    Connection connection = null;
    ResultSet resultSet = null;
    try {
      connection = connectionProvider.getConnection();
      preparedStatement = connection.prepareStatement("SELECT * FROM user");
      resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        User user = extractUserFromResultSet(resultSet);
        users.add(user);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close(connection, preparedStatement, resultSet);
    }
    return users;
  }

  /**
   * Returns a User object for given email
   * @param email Email as String
   * @return requested user object if found, null if not found
   */
  public User getUserByEmail(String email) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    User user = null;
    try {
      connection = connectionProvider.getConnection();
      preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE email=?");
      preparedStatement.setString(1, email);
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        user = extractUserFromResultSet(resultSet);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close(connection, preparedStatement, resultSet);
    }
    return user;
  }

  /**
   * Returns a User object for given userId
   * @param userId userId as int
   * @return requested user object if found, null if not found
   */
  public User getUserById(int userId) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    User user = null;
    try {
      connection = connectionProvider.getConnection();
      preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE id=?");
      preparedStatement.setInt(1, userId);
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        user = extractUserFromResultSet(resultSet);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close(connection, preparedStatement, resultSet);
    }
    return user;
  }

  /**
   * Helper method to extract a User from a ResultSet
   * @param resultSet ResultSet with the user data
   * @return User object
   */
  private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
    User user = new User();
    user.setUserId(resultSet.getInt("id"));
    user.setEmail(resultSet.getString("email"));
    user.setPassword(resultSet.getString("password"));

    // New fields based on the updated schema
    user.setFullName(resultSet.getString("full_name"));

    // Handle date conversion
    Date dobDate = resultSet.getDate("dob");
    if (dobDate != null) {
      user.setDateOfBirth(dobDate.toLocalDate());
    }

    user.setHouseholdId(resultSet.getString("household_id"));
    user.setPhoneNumber(resultSet.getInt("phone_number"));

    return user;
  }

  public User loginUser(String email, String password) {
    User existingUser = getUserByEmail(email);
    if (existingUser != null && checkPassword(password, existingUser.getPassword())) {
      return existingUser;
    }

    return null; // Login failed
  }


  /**
   * Registers a new user in the database.
   * @param user User object with all required fields.
   * @return User object with generated ID if successful, null if user already exists or failed.
   */
  public User addUser(User user) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    // Check if user already exists by email
    if (getUserByEmail(user.getEmail()) != null) {
      // User with this email already exists
      return null;
    }

    try {
      connection = connectionProvider.getConnection();

      String sql = "INSERT INTO user (email, password, full_name, dob, household_id, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
      preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      preparedStatement.setString(1, user.getEmail());
      preparedStatement.setString(2, hashPassword(user.getPassword())); // Ensure secure password hashing
      preparedStatement.setString(3, user.getFullName());

      if (user.getDateOfBirth() != null) {
        preparedStatement.setDate(4, Date.valueOf(user.getDateOfBirth()));
      } else {
        preparedStatement.setNull(4, Types.DATE);
      }

      preparedStatement.setString(5, user.getHouseholdId());

      if (user.getPhoneNumber() > 0) {
        preparedStatement.setInt(6, user.getPhoneNumber());
      } else {
        preparedStatement.setNull(6, Types.INTEGER);
      }

      int rowsAffected = preparedStatement.executeUpdate();

      if (rowsAffected > 0) {
        resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
          user.setUserId(resultSet.getInt(1));
          return user;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close(connection, preparedStatement, resultSet);
    }

    return null;
  }


  /**
   * Method to hash a password
   * @param password password to be hashed
   * @return hashedPassword
   */
  private String hashPassword(String password) {
    // In a real application, you should use a proper hashing algorithm
    // such as BCrypt, PBKDF2, or Argon2
    // This is just a placeholder
    return password; // Implement actual hashing
  }

  /**
   * Check if provided password matches the stored hashed password
   * @param providedPassword The password to check
   * @param storedPassword The stored hashed password
   * @return true if passwords match, false otherwise
   */
  private boolean checkPassword(String providedPassword, String storedPassword) {
    // In a real application, you would use the same hashing algorithm
    // to compare the provided password with the stored one
    // This is just a placeholder
    return providedPassword.equals(storedPassword); // Implement actual password checking
  }

  /**
   * Updates a user's information
   * @param user User object with updated information
   * @return true if update successful, false otherwise
   */
  public boolean updateUser(User user) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = connectionProvider.getConnection();
      String sql = "UPDATE user SET email=?, full_name=?, dob=?, household_id=?, phone_number=? WHERE id=?";
      preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setString(1, user.getEmail());
      preparedStatement.setString(2, user.getFullName());

      if (user.getDateOfBirth() != null) {
        preparedStatement.setDate(3, Date.valueOf(user.getDateOfBirth()));
      } else {
        preparedStatement.setNull(3, Types.DATE);
      }

      preparedStatement.setString(4, user.getHouseholdId());

      if (user.getPhoneNumber() > 0) {
        preparedStatement.setInt(5, user.getPhoneNumber());
      } else {
        preparedStatement.setNull(5, Types.INTEGER);
      }

      preparedStatement.setInt(6, user.getUserId());

      int rowsAffected = preparedStatement.executeUpdate();
      return rowsAffected > 0;

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    } finally {
      close(connection, preparedStatement, null);
    }
  }

  /**
   * Updates a user's password
   * @param userId ID of the user
   * @param newPassword New password to set
   * @return true if password update successful, false otherwise
   */
  public boolean updatePassword(int userId, String newPassword) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = connectionProvider.getConnection();
      String sql = "UPDATE user SET password=? WHERE id=?";
      preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setString(1, hashPassword(newPassword));
      preparedStatement.setInt(2, userId);

      int rowsAffected = preparedStatement.executeUpdate();
      return rowsAffected > 0;

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    } finally {
      close(connection, preparedStatement, null);
    }
  }
}