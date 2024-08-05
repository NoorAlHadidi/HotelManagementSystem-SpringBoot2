package com.brightskies.hotelsystem.enums;

public enum RoomType {
    singleroom("singleroom"),
    doubleroom("doubleroom"),
    familyroom("familyroom"),
    suite("suite");

    private String name;
    RoomType(String name) {
        this.name = name;
    }
}