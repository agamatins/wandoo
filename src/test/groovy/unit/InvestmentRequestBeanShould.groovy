package unit

import com.wandoo.homework.base.AppDefaults
import com.wandoo.homework.requestbeans.InvestmentRequestBean
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class InvestmentRequestBeanShould extends Specification{

    def "request bean validation should contain all errors"() {
        given:
        def investmentRequestBean = new InvestmentRequestBean()
        investmentRequestBean.loanId = loanId
        investmentRequestBean.customerId = customerId
        investmentRequestBean.amount = amount
        expect:
        investmentRequestBean.validate().size() == errors
        where:
        loanId          | customerId        | amount            | errors
        1               | 1                 | 100               | 0
        1               | null              | 100               | 1
        1               | null              | null              | 2
        null            | null              | null              | 3
        1               | 1                 | 10000             | 1
    }

    def "loanId validation should"() {
        given:
        def validationOutput = getValidationMessage(loanId, 1, 100)

        expect:
        validationOutput[0] == errorMessage
        validationOutput[1] == field

        where:
        loanId  | errorMessage                | field
        1       | ""                          | ""
        null    | AppDefaults.CANNOT_BE_EMPTY | "loanId"
    }

    def "clientId validation should"() {
        given:
        def validationOutput = getValidationMessage(1, clientId, 100)

        expect:
        validationOutput[0] == errorMessage
        validationOutput[1] == field

        where:
        clientId    | errorMessage                | field
        1           | ""                          | ""
        null        | AppDefaults.CANNOT_BE_EMPTY | "customerId"
    }

    def "amount validation should"() {
        given:
        def validationOutput = getValidationMessage(1, 1, amount)

        expect:
        validationOutput[0] == errorMessage
        validationOutput[1] == field

        where:
        amount    | errorMessage                                | field
        100       | ""                                          | ""
        2         | ""                                          | ""
        1         | ""                                          | ""
        null      | AppDefaults.CANNOT_BE_EMPTY                 | "amount"
        0         | AppDefaults.MAIN_AMOUNT_INCORRECT_FORMAT    | "amount"
        -1        | AppDefaults.MAIN_AMOUNT_INCORRECT_FORMAT    | "amount"
        1001      | AppDefaults.MAIN_AMOUNT_INCORRECT_FORMAT    | "amount"
    }

    def getValidationMessage(Long loanId, Long customerId, BigDecimal amount) {
        def investmentRequestBean = new InvestmentRequestBean()
        investmentRequestBean.loanId = loanId
        investmentRequestBean.customerId = customerId
        investmentRequestBean.amount = amount
        def errors = investmentRequestBean.validate()
        def errorMessage = errors.isEmpty() ? "" : errors.get(0).message
        def field = errors.isEmpty() ? "" : errors.get(0).field
        new Tuple(errorMessage, field)
    }
}

