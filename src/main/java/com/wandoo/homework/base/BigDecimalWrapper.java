package com.wandoo.homework.base;

import java.math.BigDecimal;

public final class BigDecimalWrapper {

    private static final int ZERO = 0;
    private final BigDecimal bigDecimal;

    BigDecimalWrapper(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public boolean eq(BigDecimal decimal) {
        return bigDecimal.compareTo(decimal) == ZERO;
    }

    public boolean gt(BigDecimal decimal) {
        return bigDecimal.compareTo(decimal) > ZERO;
    }

    public boolean gte(BigDecimal decimal) {
        return bigDecimal.compareTo(decimal) >= ZERO;
    }

    public boolean lt(BigDecimal decimal) {
        return bigDecimal.compareTo(decimal) < ZERO;
    }

    public boolean lte(BigDecimal decimal) {
        return bigDecimal.compareTo(decimal) <= ZERO;
    }

    public boolean betweenIncluding(BigDecimal left, BigDecimal right) {
        return bigDecimal.compareTo(left) >= ZERO && bigDecimal.compareTo(right) <= ZERO;
    }
    public boolean betweenExcluding(BigDecimal left, BigDecimal right) {
        return bigDecimal.compareTo(left) > ZERO && bigDecimal.compareTo(right) < ZERO;
    }

}
