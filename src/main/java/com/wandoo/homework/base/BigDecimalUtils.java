package com.wandoo.homework.base;

import java.math.BigDecimal;

public class BigDecimalUtils {

    private BigDecimalUtils() {
    }

    public static BigDecimalWrapper amount(BigDecimal decimal) {

        return new BigDecimalWrapper(decimal);
    }
}
