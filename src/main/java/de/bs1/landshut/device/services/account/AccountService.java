package de.bs1.landshut.device.services.account;

import de.bs1.landshut.device.services.account.object.Account;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Optional;

public interface AccountService {

  @NotNull Optional<Account> getAccount(@NotNull String name);

  @NotNull Optional<Account> getAccount(int id);

  @NotNull Account createAccount(@NotNull String userName, @NotNull String firstName, @NotNull String lastName, @NotNull String password, boolean administrator);

  void updateAccount(@NotNull Account account);

  @NotNull List<Account> getAccounts();

}
