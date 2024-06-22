package utils;

public enum DAOAction {
    GET(0),
    GETALL(1),
    ADD(2), 
    DELETE(3);

    @SuppressWarnings("unused")
    private final int val;

    DAOAction(int val) {
        this.val = val;
    }
}
