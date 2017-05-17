package com.wandoo.homework.base;

import java.math.BigDecimal;

public final class AppDefaults {
    //default values
    public static final int NAME_MAX_LENGTH = 16;
    public static final BigDecimal MIN_MAIN_AMOUNT = BigDecimal.ONE;
    public static final BigDecimal MAX_MAIN_AMOUNT = new BigDecimal(1000);
    public static final BigDecimal MIN_INTEREST_RATE = BigDecimal.ZERO;
    public static final BigDecimal MAX_INTEREST_RATE = new BigDecimal(100);
    public static final BigDecimal MIN_INTEREST_AMOUNT = BigDecimal.ZERO;
    public static final BigDecimal MAX_INTEREST_AMOUNT = new BigDecimal(1000);

    //validation messages
    private static final String DEFAULT_AMOUNTS_INCORRECT_FORMAT_MESSAGE = "cannot be less than %s or more than %s";
    public static final String CANNOT_BE_EMPTY = "cannot be empty";
    public static final String INCORRECT_FORMAT = "incorrect format";
    public static final String TOO_LONG = String.format("too long: max %s symbols", NAME_MAX_LENGTH);
    public static final String MAIN_AMOUNT_INCORRECT_FORMAT = String.format(DEFAULT_AMOUNTS_INCORRECT_FORMAT_MESSAGE, MIN_MAIN_AMOUNT, MAX_MAIN_AMOUNT);
    public static final String INTEREST_RATE_INCORRECT_FORMAT = String.format(DEFAULT_AMOUNTS_INCORRECT_FORMAT_MESSAGE, MIN_INTEREST_RATE, MAX_INTEREST_RATE);
    public static final String INTEREST_AMOUNT_INCORRECT_FORMAT = String.format(DEFAULT_AMOUNTS_INCORRECT_FORMAT_MESSAGE, MIN_INTEREST_AMOUNT, MAX_INTEREST_AMOUNT);
    public static final String CUSTOMER_ALREADY_REGISTERED_WITH_EMAIL = "Customer already exist with this email";
    public static final String CANNOT_FIND_CUSTOMER_ID = "Customer with given id cannot be found";
    public static final String CANNOT_FIND_LOAN_ID = "Loan with given id cannot be found";
    public static final String LOAN_NOT_INVESTABLE = "Cannot invest into loan - no open amount";
    public static final String LOAN_ID_ALREADY_EXIST = "Loan with given id already imported";
    public static final String PAYMENT_ID_ALREADY_EXIST = "Payment with given id already imported";
    public static final String NOT_FOUND = "Not found";

}
