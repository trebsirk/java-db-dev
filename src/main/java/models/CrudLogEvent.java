package models;

public record CrudLogEvent(
        long timestamp,
        String functionName,
        boolean success,
        String msg,
        long executionTimeMs) {

        public CrudLogEvent withSuccess(boolean b) {
                return new CrudLogEvent(timestamp, functionName, b, msg, executionTimeMs);
        }

        public CrudLogEvent withExecutionTimeMs(long t) {
                return new CrudLogEvent(timestamp, functionName, success, msg, t);
        }

        public CrudLogEvent withMsg(String m) {
                return new CrudLogEvent(timestamp, functionName, success, m, executionTimeMs);
        }
    
}
