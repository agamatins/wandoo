package unit

import com.wandoo.homework.base.AppDefaults
import com.wandoo.homework.requestbeans.PaymentRequestBean
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class PaymentRequestBeanShould extends Specification{

    def "request bean validation should contain all errors"() {
        given:
        def paymentRequestBean = new PaymentRequestBean()
        paymentRequestBean.id = id
        paymentRequestBean.loanId = loanId
        paymentRequestBean.mainAmount = mainAmount
        paymentRequestBean.interestAmount = interestAmount
        expect:
        paymentRequestBean.validate().size() == errors
        where:
        id      |   loanId  |   interestAmount      | mainAmount        | errors
        1       |    1      |   1                   | 100               | 0
        1       |    1      |   null                | 100               | 1
        null    |    1      |   null                | 100               | 2
        null    | null      |   null                | null              | 4
        null    |    1      |   null                | 0                 | 3
        1       |    1      |   1                   | 10000             | 1
    }

    def "id validation should"() {
        given:
        def validationOutput = getValidationMessage(id, 1, 100, 1)

        expect:
        validationOutput[0] == errorMessage
        validationOutput[1] == field

        where:
        id      | errorMessage                | field
        1       | ""                          | ""
        null    | AppDefaults.CANNOT_BE_EMPTY | "id"
    }

    def "loanId validation should"() {
        given:
        def validationOutput = getValidationMessage(1, id, 100, 1)

        expect:
        validationOutput[0] == errorMessage
        validationOutput[1] == field

        where:
        id      | errorMessage                | field
        1       | ""                          | ""
        null    | AppDefaults.CANNOT_BE_EMPTY | "loanId"
    }

    def "main amount validation should"() {
        given:
        def validationOutput = getValidationMessage(1, 1, amount, 1)

        expect:
        validationOutput[0] == errorMessage
        validationOutput[1] == field

        where:
        amount    | errorMessage                                | field
        1000      | ""                                          | ""
        50.22     | ""                                          | ""
        1         | ""                                          | ""
        null      | AppDefaults.CANNOT_BE_EMPTY                 | "mainAmount"
        0         | AppDefaults.MAIN_AMOUNT_INCORRECT_FORMAT    | "mainAmount"
        -1        | AppDefaults.MAIN_AMOUNT_INCORRECT_FORMAT    | "mainAmount"
        1001      | AppDefaults.MAIN_AMOUNT_INCORRECT_FORMAT    | "mainAmount"
    }

    def "interest amount validation should"() {
        given:
        def validationOutput = getValidationMessage(1, 1, 500, amount)

        expect:
        validationOutput[0] == errorMessage
        validationOutput[1] == field

        where:
        amount    | errorMessage                                    | field
        1000      | ""                                              | ""
        50.22     | ""                                              | ""
        1         | ""                                              | ""
        null      | AppDefaults.CANNOT_BE_EMPTY                     | "interestAmount"
        0         | ""                                              | ""
        -1        | AppDefaults.INTEREST_AMOUNT_INCORRECT_FORMAT    | "interestAmount"
        1001      | AppDefaults.INTEREST_AMOUNT_INCORRECT_FORMAT    | "interestAmount"
    }

    def getValidationMessage(Long id, Long loanId, BigDecimal mainAmount, BigDecimal interestAmount) {
        def paymentRequestBean = new PaymentRequestBean()
        paymentRequestBean.id = id
        paymentRequestBean.loanId = loanId
        paymentRequestBean.mainAmount = mainAmount
        paymentRequestBean.interestAmount = interestAmount
        def errors = paymentRequestBean.validate()
        def errorMessage = errors.isEmpty() ? "" : errors.get(0).message
        def field = errors.isEmpty() ? "" : errors.get(0).field
        new Tuple(errorMessage, field)
    }
}
