package com.akki.dto;

public class Thing {
    private int field;

    public Thing() {
    }

    public Thing(int field) {
        this.field = field;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "Thing{" +
                "field=" + field +
                '}';
    }
}
