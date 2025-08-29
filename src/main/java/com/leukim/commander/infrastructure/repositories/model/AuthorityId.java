package com.leukim.commander.infrastructure.repositories.model;

import java.io.Serializable;
import java.util.Objects;

public final class AuthorityId implements Serializable {
    private final String username;
    private final String authority;

    public AuthorityId(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthorityId that = (AuthorityId) o;
        return Objects.equals(username, that.username) && Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, authority);
    }
}
