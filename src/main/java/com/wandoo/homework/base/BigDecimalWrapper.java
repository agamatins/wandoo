package com.wandoo.homework.base;

import java.math.BigDecimal;

public final class BigDecimalWrapper {

    private final BigDecimal bigDecimal;

    BigDecimalWrapper(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public boolean eq(BigDecimal decimal) {
        return bigDecimal.compareTo(decimal) == 0;
    }

    public boolean gt(BigDecimal decimal) {
        return bigDecimal.compareTo(decimal) > 0;
    }

    public boolean gte(BigDecimal decimal) {
        return bigDecimal.compareTo(decimal) >= 0;
    }

    public boolean lt(BigDecimal decimal) {
        return bigDecimal.compareTo(decimal) < 0;
    }

    public boolean lte(BigDecimal decimal) {
        return bigDecimal.compareTo(decimal) <= 0;
    }

    public boolean betweenIncluding(BigDecimal left, BigDecimal right) {
        return bigDecimal.compareTo(left) >= 0 && bigDecimal.compareTo(right) <= 0;
    }
    public boolean betweenExcluding(BigDecimal left, BigDecimal right) {
        return bigDecimal.compareTo(left) > 0 && bigDecimal.compareTo(right) < 0;
    }

    public boolean gtZero() {
        return bigDecimal.compareTo(BigDecimal.ZERO) > 0;
    }
}
