package models;

import java.sql.Timestamp;

public record User(Integer id, String username, String password_hash, Timestamp created_at) {}
