package com.finicityclient.type.tx_push;

public enum SubscriptionType {
    ACCOUNT("active"),
    TRANSACTION("transaction");

    private final String name;

    SubscriptionType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
