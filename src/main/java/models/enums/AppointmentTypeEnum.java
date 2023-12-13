package models.enums;

public enum AppointmentTypeEnum {
    ENTRADA("ENTRADA"),
    SAIDA("SAIDA");

    private final String value;


    AppointmentTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
