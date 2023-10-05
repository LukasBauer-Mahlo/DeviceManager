package de.bs1.landshut.device.database;

import com.zaxxer.hikari.HikariDataSource;
import de.bs1.landshut.device.database.function.ThrowableConsumer;
import de.bs1.landshut.device.database.function.ThrowableFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseDriver {

    HikariDataSource getDataSource();

    @NotNull
    Connection getConnection() throws SQLException;

    int executeUpdate(@NotNull String sql);

    int executeUpdate(@NotNull String sql,
                      @Nullable ThrowableConsumer<PreparedStatement, SQLException> modifier);

    <T> T executeUpdateWithKeys(@NotNull String sql,
                                @Nullable ThrowableConsumer<PreparedStatement, SQLException> modifier,
                                @NotNull ThrowableFunction<ResultSet, T, SQLException> resultMapper);

    <T> T executeQuery(@NotNull String sql,
                       @NotNull ThrowableFunction<ResultSet, T, SQLException> resultMapper);

    <T> T executeQuery(@NotNull String sql,
                       @Nullable ThrowableConsumer<PreparedStatement, SQLException> modifier,
                       @NotNull ThrowableFunction<ResultSet, T, SQLException> resultMapper);

    void close();

}
