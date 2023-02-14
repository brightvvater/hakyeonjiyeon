package project.hakyeonjiyeon.domain;


public enum PayMethod {

    CARD("신용카드"), ACCOUNT_TRANSFER("계좌이체"), CASH("현금");

    private final String description;

    PayMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
