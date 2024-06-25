package models;

import java.util.Optional;

public record CrudResult (
        boolean success,
        Optional<String> errorMessage) {

    public CrudResult(boolean success) {
        this(success, Optional.empty());
    }
}
