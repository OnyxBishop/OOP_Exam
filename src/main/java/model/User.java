package model;

import java.util.UUID;

public class User {
    private String login;
    private String passwordHash;
    private UUID walletId;

    public User(String login, String passwordHash, UUID walletId) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.walletId = walletId;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UUID getWalletId() {
        return walletId;
    }
}