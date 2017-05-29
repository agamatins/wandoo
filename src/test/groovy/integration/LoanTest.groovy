package integration

import com.wandoo.homework.Application
import com.wandoo.homework.base.AppDefaults
import helpers.TestBaseSpec
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import wrappers.LoanApiWrapper
import wrappers.PaymentApiWrapper

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoanTest extends TestBaseSpec{

    @LocalServerPort
    int randomServerPort;

    def paymentApiWrapper
    def loanApiWrapper

    def setup() {
        paymentApiWrapper = new PaymentApiWrapper(randomServerPort)
        loanApiWrapper = new LoanApiWrapper(randomServerPort)
    }

    def "can import loan and it is investable with full amount"() {
        when:
        def importLoanResponse = importLoanAndGetResponse(loanApiWrapper)

        then:
        importLoanResponse.errors.isEmpty()

        when:
        def loan = loanApiWrapper.getLoanWithId(LOAN_ID).body

        then:
        loan != null
        loan.mainAmount == LOAN_MAIN_AMOUNT
        loan.interestRate == LOAN_INTEREST_RATE
        loan.allowedInvestmentAmount == LOAN_MAIN_AMOUNT
        loan.investable
    }

    def "cannot import loan with the same id"() {
        when:
        importLoanAndGetResponse(loanApiWrapper)
        def importLoanResponse = importLoanAndGetResponse(loanApiWrapper)

        then:
        !importLoanResponse.errors.isEmpty()
        importLoanResponse.errors.get(0).message == AppDefaults.LOAN_ID_ALREADY_EXIST
        importLoanResponse.errors.get(0).field == ""

    }

    def "loan becomes not investable after full repayment"() {
        when:
        importLoanAndGetResponse(loanApiWrapper)
        importPaymentAndGetResponse(paymentApiWrapper, 1, LOAN_MAIN_AMOUNT)
        def loan = loanApiWrapper.getLoanWithId(LOAN_ID).body

        then:
        loan != null
        loan.mainAmount == LOAN_MAIN_AMOUNT
        loan.interestRate == LOAN_INTEREST_RATE
        loan.allowedInvestmentAmount == new BigDecimal(0)
        !loan.investable
    }

    def "loan still investable after partial repayment"() {
        when:
        importLoanAndGetResponse(loanApiWrapper)
        importPaymentAndGetResponse(paymentApiWrapper, 1, 200)
        def loan = loanApiWrapper.getLoanWithId(LOAN_ID).body

        then:
        loan != null
        loan.mainAmount == LOAN_MAIN_AMOUNT
        loan.interestRate == LOAN_INTEREST_RATE
        loan.allowedInvestmentAmount == LOAN_MAIN_AMOUNT - 200
        loan.investable
    }

    def "loan becomes not investable after several partial repayments covering main amount"() {
        when:
        importLoanAndGetResponse(loanApiWrapper)
        importPaymentAndGetResponse(paymentApiWrapper, 1, 200)
        def loan = loanApiWrapper.getLoanWithId(LOAN_ID).body

        then:
        loan != null
        loan.mainAmount == LOAN_MAIN_AMOUNT
        loan.interestRate == LOAN_INTEREST_RATE
        loan.allowedInvestmentAmount == LOAN_MAIN_AMOUNT - 200
        loan.investable

        when:
        importPaymentAndGetResponse(paymentApiWrapper, 2, LOAN_MAIN_AMOUNT - 200)
        loan = loanApiWrapper.getLoanWithId(LOAN_ID).body

        then:
        loan != null
        loan.mainAmount == LOAN_MAIN_AMOUNT
        loan.interestRate == LOAN_INTEREST_RATE
        loan.allowedInvestmentAmount == new BigDecimal(0)
        !loan.investable
    }

    def "can list investable loans - all investable"() {
        when:
        importLoanWithIdAndAmountAndGetResponse(loanApiWrapper, 1, 500)
        importLoanWithIdAndAmountAndGetResponse(loanApiWrapper, 2, 250)
        def investableLoansResponse = loanApiWrapper.listInvestableLoans()

        then:
        investableLoansResponse.errors.isEmpty()
        investableLoansResponse.body.size() == 2
    }

    def "can list investable loans: one uninvestable after full repayment"() {
        when:
        importLoanWithIdAndAmountAndGetResponse(loanApiWrapper, 1, 500)
        importLoanWithIdAndAmountAndGetResponse(loanApiWrapper, 2, 250)
        importPaymentAndGetResponse(paymentApiWrapper, 1, 500)
        def investableLoansResponse = loanApiWrapper.listInvestableLoans()

        then:
        investableLoansResponse.errors.isEmpty()
        investableLoansResponse.body.size() == 1
    }

    def "no investable loans to list after all being fully repaid"() {
        when:
        importLoanWithIdAndAmountAndGetResponse(loanApiWrapper, 1, 500)
        importLoanWithIdAndAmountAndGetResponse(loanApiWrapper, 2, 250)
        importPaymentForLoanAndGetResponse(paymentApiWrapper, 1, 1, 500)
        importPaymentForLoanAndGetResponse(paymentApiWrapper, 2, 2, 250)
        def investableLoansResponse = loanApiWrapper.listInvestableLoans()

        then:
        investableLoansResponse.errors.isEmpty()
        investableLoansResponse.body.size() == 0
    }


}
