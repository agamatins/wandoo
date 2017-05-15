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

    public boolean notEq(BigDecimal decimal) {
        return !eq(decimal);
    }

    public boolean notGt(BigDecimal decimal) {
        return !gt(decimal);
    }

    public boolean notGte(BigDecimal decimal) {
        return !gte(decimal);
    }

    public boolean notLt(BigDecimal decimal) {
        return !lt(decimal);
    }

    public boolean notLte(BigDecimal decimal) {
        return !lte(decimal);
    }
}
