package models;

public record CrudLogEvent(
        long timestamp,
        String functionName,
        boolean success,
        long executionTimeMs) {
}
