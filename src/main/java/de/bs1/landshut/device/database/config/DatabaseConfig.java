package de.bs1.landshut.device.database.config;

import org.jetbrains.annotations.NotNull;

public class DatabaseConfig {

    private final boolean enabled;

    private final String jdbcUrl;

    private final String user;

    private final String password;

    public DatabaseConfig(boolean enabled, String jdbcUrl, String user, String password) {
        this.enabled = enabled;
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
    }

    public DatabaseConfig() {
        this.enabled = false;
        this.jdbcUrl = "jdbc:mysql://localhost:3306/devicemanager";
        this.user = "user";
        this.password = "password";
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    @NotNull
    public String getJdbcUrl() {
        return jdbcUrl;
    }

    @NotNull
    public String getUser() {
        return user;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

}