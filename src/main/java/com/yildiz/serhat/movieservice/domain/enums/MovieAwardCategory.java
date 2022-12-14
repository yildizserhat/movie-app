package com.yildiz.serhat.movieservice.domain.enums;

public enum MovieAwardCategory {

    BEST_PICTURE("Best Picture");

    private final String value;

    MovieAwardCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
