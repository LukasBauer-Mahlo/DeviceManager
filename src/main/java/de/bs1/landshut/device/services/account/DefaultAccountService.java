package de.bs1.landshut.device.services.account;

import de.bs1.landshut.device.database.DatabaseDriver;
import de.bs1.landshut.device.services.account.object.Account;
import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DefaultAccountService implements AccountService {

  private final DatabaseDriver databaseDriver;

  public DefaultAccountService(DatabaseDriver databaseDriver) {
    this.databaseDriver = databaseDriver;

    this.databaseDriver.executeUpdate("CREATE TABLE IF NOT EXISTS `accounts` ("
        + "    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"
        + "    userName VARCHAR(32) UNIQUE KEY NOT NULL,"
        + "    firstName VARCHAR(32) NOT NULL,"
        + "    lastName VARCHAR(32) NOT NULL,"
        + "    password VARCHAR(256) NOT NULL,"
        + "    administrator BOOLEAN NOT NULL,"
        + "    disabled BOOLEAN DEFAULT false NOT NULL"
        + ");");
  }

  @Override
  public @NotNull Optional<Account> getAccount(@NotNull String name) {
    return Optional.ofNullable(
        this.databaseDriver.executeQuery("SELECT * FROM `accounts` WHERE `userName` = ?;", statement -> {
          statement.setString(1, name);
        }, resultSet -> {
          if (!resultSet.next()) {
            return null;
          }

          return this.fromResultSet(resultSet);
        })
    );
  }

  @Override
  public @NotNull Optional<Account> getAccount(int id) {
    return Optional.ofNullable(
        this.databaseDriver.executeQuery("SELECT * FROM `accounts` WHERE `id` = ?;", statement -> {
          statement.setInt(1, id);
        }, resultSet -> {
          if (!resultSet.next()) {
            return null;
          }

          return this.fromResultSet(resultSet);
        })
    );
  }

  @Override
  public @NotNull Account createAccount(@NotNull String userName, @NotNull String firstName, @NotNull String lastName, @NotNull String password, boolean administrator) {
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    int generatedId = this.databaseDriver.executeUpdateWithKeys("INSERT INTO `accounts` (`userName`, `firstName`, `lastName`, `password`, `administrator`) VALUES (?, ?, ?, ?, ?);", statement -> {
      statement.setString(1, userName);
      statement.setString(2, firstName);
      statement.setString(3, lastName);
      statement.setString(4, hashedPassword);
      statement.setBoolean(5, administrator);
    }, resultSet -> {
      if (!resultSet.next()) {
        throw new RuntimeException("Unable to generate accountId for account with name " + userName);
      }

      return resultSet.getInt(1);
    });

    return new Account(generatedId, userName, firstName, lastName, hashedPassword, true, administrator);
  }

  @Override
  public void updateAccount(@NotNull Account account) {
    this.databaseDriver.executeUpdate("UPDATE `accounts` SET `firstName` = ?, `lastName` = ?, `password` = ?, `disabled` = ?, `administrator` = ? WHERE `id` = ?;", statement -> {
      statement.setString(1, account.getFirstName());
      statement.setString(2, account.getLastName());
      statement.setString(3, account.getPassword());
      statement.setBoolean(4, account.isDisabled());
      statement.setBoolean(5, account.isAdministrator());
      statement.setInt(6, account.getId());
    });
  }

  @Override
  public @NotNull List<Account> getAccounts() {
    return this.databaseDriver.executeQuery("SELECT * FROM `accounts`;", resultSet -> {
      List<Account> accounts = new ArrayList<>();
      while (resultSet.next()) {
        accounts.add(this.fromResultSet(resultSet));
      }

      return accounts;
    });
  }

  @NotNull
  private Account fromResultSet(@NotNull ResultSet resultSet) throws SQLException {
    return new Account(
        resultSet.getInt("id"),
        resultSet.getString("username"),
        resultSet.getString("firstName"),
        resultSet.getString("lastName"),
        resultSet.getString("password"),
        resultSet.getBoolean("disabled"),
        resultSet.getBoolean("administrator")
    );
  }

}