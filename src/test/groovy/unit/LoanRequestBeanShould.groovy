package unit

import com.wandoo.homework.base.AppDefaults
import com.wandoo.homework.requestbeans.LoanRequestBean
import spock.lang.Specification

class LoanRequestBeanShould extends Specification{

    def "request bean validation should contain all errors"() {
        given:
        def loanRequestBean = new LoanRequestBean()
        loanRequestBean.id = id
        loanRequestBean.mainAmount = mainAmount
        loanRequestBean.interestRate = interestRate
        expect:
        loanRequestBean.validate().size() == errors
        where:
        id      |   interestRate      | mainAmount        | errors
        1       |   1                 | 100               | 0
        1       |   null              | 100               | 1
        null    |   null              | 100               | 2
        null    |   null              | null              | 3
        null    |   null              | 0                 | 3
        1       |   1                 | 10000             | 1
    }

    def "id validation should"() {
        given:
        def validationOutput = getValidationMessage(id, 1, 100)

        expect:
        validationOutput[0] == errorMessage
        validationOutput[1] == field

        where:
        id      | errorMessage                | field
        1       | ""                          | ""
        null    | AppDefaults.CANNOT_BE_EMPTY | "id"
    }

    def "main amount validation should"() {
        given:
        def validationOutput = getValidationMessage(1, amount, 1)

        expect:
        validationOutput[0] == errorMessage
        validationOutput[1] == field

        where:
        amount    | errorMessage                                | field
        1000      | ""                                          | ""
        1         | ""                                          | ""
        null      | AppDefaults.CANNOT_BE_EMPTY                 | "mainAmount"
        0         | AppDefaults.MAIN_AMOUNT_INCORRECT_FORMAT    | "mainAmount"
        -1        | AppDefaults.MAIN_AMOUNT_INCORRECT_FORMAT    | "mainAmount"
        1001      | AppDefaults.MAIN_AMOUNT_INCORRECT_FORMAT    | "mainAmount"
    }

    def "interest rate validation should"() {
        given:
        def validationOutput = getValidationMessage(1, 500, amount)

        expect:
        validationOutput[0] == errorMessage
        validationOutput[1] == field

        where:
        amount    | errorMessage                                | field
        100       | ""                                          | ""
        50.22     | ""                                          | ""
        1         | ""                                          | ""
        null      | AppDefaults.CANNOT_BE_EMPTY                 | "interestRate"
        0         | ""                                          | ""
        -1        | AppDefaults.INTEREST_RATE_INCORRECT_FORMAT  | "interestRate"
        101       | AppDefaults.INTEREST_RATE_INCORRECT_FORMAT  | "interestRate"
    }

    def getValidationMessage(Long id, BigDecimal mainAmount, BigDecimal interestRate) {
        def loanRequestBean = new LoanRequestBean()
        loanRequestBean.id = id
        loanRequestBean.mainAmount = mainAmount
        loanRequestBean.interestRate = interestRate
        def errors = loanRequestBean.validate()
        def errorMessage = errors.isEmpty() ? "" : errors.get(0).message
        def field = errors.isEmpty() ? "" : errors.get(0).field
        new Tuple(errorMessage, field)
    }
}

