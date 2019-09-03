package com.library.user.stats.application.enums;

import java.util.HashMap;
import java.util.Map;

public enum Rating {
    ONE (1),
    TWO (2),
    THREE (3),
    FOUR (4),
    FIVE (5);

    private int value;
    private static Map map = new HashMap<>();

    private Rating(int value) {
        this.value = value;
    }

    static {
        for (Rating rating : Rating.values()) {
            map.put(rating.value, rating);
        }
    }

    public static Rating valueOf(int rating) {
        return (Rating) map.get(rating);
    }

    public int getValue() {
        return value;
    }

    /*PageType pageType = PageType.valueOf(2); // pageType = PageType.CODING

    ProductType productType = ProductType.DATABASES;
    int productTypeId = productType.getValue(); // productTypeId = 3*/
}
