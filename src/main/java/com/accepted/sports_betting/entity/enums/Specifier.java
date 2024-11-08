package com.accepted.sports_betting.entity.enums;

public enum Specifier {
    HOME_WIN("1"),
    DRAW("X"),
    AWAY_WIN("2");

    private final String code;

    Specifier(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
