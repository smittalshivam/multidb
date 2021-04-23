package com.example.multidb2;

public enum DBTypeEnum {
    MAIN("MAIN"), CLIENT_A("CLIENT_A"), CLIENT_B("CLIENT_B");

    private String value;

    private DBTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toValue(DBTypeEnum dbTypeEnum) {
        return dbTypeEnum.getValue();
    }
}