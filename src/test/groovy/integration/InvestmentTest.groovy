package integration

import com.wandoo.homework.Application
import com.wandoo.homework.base.AppDefaults
import helpers.TestBaseSpec
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import wrappers.CustomerApiWrapper
import wrappers.LoanApiWrapper
import wrappers.PaymentApiWrapper

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InvestmentTest extends TestBaseSpec {

    @LocalServerPort
    int randomServerPort;

    def customerApiWrapper
    def loanApiWrapper
    def paymentApiWrapper

    def setup() {
        customerApiWrapper = new CustomerApiWrapper(randomServerPort)
        paymentApiWrapper = new PaymentApiWrapper(randomServerPort)
        loanApiWrapper = new LoanApiWrapper(randomServerPort)
    }

    def "can fully buy investable loan: unpaid loan"() {
        when:
        registerCustomerAndGetResponse(customerApiWrapper)
        importLoanAndGetResponse(loanApiWrapper)
        def investmentResponse = investIntoLoanAndGetResponse(customerApiWrapper, LOAN_ID, LOAN_MAIN_AMOUNT)

        then:
        investmentResponse.errors.isEmpty()

        when:
        def customerResponse = customerApiWrapper.getLastRegisteredCustomer()

        then:
        customerResponse.body.investments.size() == 1
        customerResponse.body.investments.get(0).loanId == LOAN_ID
        customerResponse.body.investments.get(0).amount == LOAN_MAIN_AMOUNT

        when:
        def listInvestableLoansResponse = loanApiWrapper.listInvestableLoans()

        then:
        listInvestableLoansResponse.body.size() == 0

        when:
        def loanResponseBean = loanApiWrapper.getLoanWithId(LOAN_ID)

        then:
        loanResponseBean.body.allowedInvestmentAmount == new BigDecimal(0)
        !loanResponseBean.body.investable
    }

    def "can fully buy investable loan: partially paid loan"() {
        when:
        registerCustomerAndGetResponse(customerApiWrapper)
        importLoanAndGetResponse(loanApiWrapper)
        importPaymentAndGetResponse(paymentApiWrapper, 1, LOAN_MAIN_AMOUNT-200)
        def investmentResponse = investIntoLoanAndGetResponse(customerApiWrapper, LOAN_ID, 200)

        then:
        investmentResponse.errors.isEmpty()

        when:
        def customerResponse = customerApiWrapper.getLastRegisteredCustomer()

        then:
        customerResponse.body.investments.size() == 1
        customerResponse.body.investments.get(0).loanId == LOAN_ID
        customerResponse.body.investments.get(0).amount == new BigDecimal(200)

        when:
        def listInvestableLoansResponse = loanApiWrapper.listInvestableLoans()

        then:
        listInvestableLoansResponse.body.size() == 0

        when:
        def loanResponseBean = loanApiWrapper.getLoanWithId(LOAN_ID)

        then:
        loanResponseBean.body.allowedInvestmentAmount == new BigDecimal(0)
        !loanResponseBean.body.investable
    }

    def "can fully buy investable loan: partially invested loan"() {
        when:
        registerCustomerAndGetResponse(customerApiWrapper)
        importLoanAndGetResponse(loanApiWrapper)
        investIntoLoanAndGetResponse(customerApiWrapper, LOAN_ID, LOAN_MAIN_AMOUNT-200)
        def investmentResponse = investIntoLoanAndGetResponse(customerApiWrapper, LOAN_ID, 200)

        then:
        investmentResponse.errors.isEmpty()

        when:
        def customerResponse = customerApiWrapper.getLastRegisteredCustomer()

        then:
        customerResponse.body.investments.size() == 2
        customerResponse.body.investments.get(0).loanId == LOAN_ID
        customerResponse.body.investments.get(0).amount == LOAN_MAIN_AMOUNT-200
        customerResponse.body.investments.get(1).loanId == LOAN_ID
        customerResponse.body.investments.get(1).amount == new BigDecimal(200)

        when:
        def listInvestableLoansResponse = loanApiWrapper.listInvestableLoans()

        then:
        listInvestableLoansResponse.body.size() == 0

        when:
        def loanResponseBean = loanApiWrapper.getLoanWithId(LOAN_ID)

        then:
        loanResponseBean.body.allowedInvestmentAmount == new BigDecimal(0)
        !loanResponseBean.body.investable
    }

    def "can fully buy investable loan: loan partially paid and invested"() {
        when:
        registerCustomerAndGetResponse(customerApiWrapper)
        importLoanAndGetResponse(loanApiWrapper)
        importPaymentAndGetResponse(paymentApiWrapper, 1, 100)
        investIntoLoanAndGetResponse(customerApiWrapper, LOAN_ID, 250)
        def investmentResponse = investIntoLoanAndGetResponse(customerApiWrapper, LOAN_ID, 150)

        then:
        investmentResponse.errors.isEmpty()

        when:
        def customerResponse = customerApiWrapper.getLastRegisteredCustomer()

        then:
        customerResponse.body.investments.size() == 2
        customerResponse.body.investments.get(0).loanId == LOAN_ID
        customerResponse.body.investments.get(0).amount == new BigDecimal(250)
        customerResponse.body.investments.get(1).loanId == LOAN_ID
        customerResponse.body.investments.get(1).amount == new BigDecimal(150)

        when:
        def listInvestableLoansResponse = loanApiWrapper.listInvestableLoans()

        then:
        listInvestableLoansResponse.body.size() == 0

        when:
        def loanResponseBean = loanApiWrapper.getLoanWithId(LOAN_ID)

        then:
        loanResponseBean.body.allowedInvestmentAmount == new BigDecimal(0)
        !loanResponseBean.body.investable
    }

    def "can fully buy investable loan: investment amount is more than open amount"() {
        when:
        registerCustomerAndGetResponse(customerApiWrapper)
        importLoanAndGetResponse(loanApiWrapper)
        def investmentResponse = investIntoLoanAndGetResponse(customerApiWrapper, LOAN_ID, LOAN_MAIN_AMOUNT+100)

        then:
        investmentResponse.errors.isEmpty()

        when:
        def customerResponse = customerApiWrapper.getLastRegisteredCustomer()

        then:
        customerResponse.body.investments.size() == 1
        customerResponse.body.investments.get(0).loanId == LOAN_ID
        customerResponse.body.investments.get(0).amount == LOAN_MAIN_AMOUNT

        when:
        def listInvestableLoansResponse = loanApiWrapper.listInvestableLoans()

        then:
        listInvestableLoansResponse.body.size() == 0

        when:
        def loanResponseBean = loanApiWrapper.getLoanWithId(LOAN_ID)

        then:
        loanResponseBean.body.allowedInvestmentAmount == new BigDecimal(0)
        !loanResponseBean.body.investable
    }

    def "can partially invest into loan: loan remains investable"() {
        when:
        registerCustomerAndGetResponse(customerApiWrapper)
        importLoanAndGetResponse(loanApiWrapper)
        def investmentResponse = investIntoLoanAndGetResponse(customerApiWrapper, LOAN_ID, 100)

        then:
        investmentResponse.errors.isEmpty()

        when:
        def customerResponse = customerApiWrapper.getLastRegisteredCustomer()

        then:
        customerResponse.body.investments.size() == 1
        customerResponse.body.investments.get(0).loanId == LOAN_ID
        customerResponse.body.investments.get(0).amount == new BigDecimal(100)

        when:
        def listInvestableLoansResponse = loanApiWrapper.listInvestableLoans()

        then:
        listInvestableLoansResponse.body.size() == 1

        when:
        def loanResponseBean = loanApiWrapper.getLoanWithId(LOAN_ID)

        then:
        loanResponseBean.body.allowedInvestmentAmount == new BigDecimal(400)
        loanResponseBean.body.investable
    }

    def "can invest into multiple loans"() {
        when:
        registerCustomerAndGetResponse(customerApiWrapper)
        importLoanWithIdAndAmountAndGetResponse(loanApiWrapper, 1L, 300)
        importLoanWithIdAndAmountAndGetResponse(loanApiWrapper, 2L, 200)
        investIntoLoanAndGetResponse(customerApiWrapper, 1L, 150)
        investIntoLoanAndGetResponse(customerApiWrapper, 2L, 200)
        def customerResponse = customerApiWrapper.getLastRegisteredCustomer()

        then:
        customerResponse.body.investments.size() == 2
        customerResponse.body.investments.get(0).loanId == 1L
        customerResponse.body.investments.get(0).amount == new BigDecimal(150)
        customerResponse.body.investments.get(1).loanId == 2L
        customerResponse.body.investments.get(1).amount == new BigDecimal(200)

        when:
        def listInvestableLoansResponse = loanApiWrapper.listInvestableLoans()

        then:
        listInvestableLoansResponse.body.size() == 1

        when:
        def loanResponseBean1 = loanApiWrapper.getLoanWithId(1L)
        def loanResponseBean2 = loanApiWrapper.getLoanWithId(2L)

        then:
        loanResponseBean1.body.allowedInvestmentAmount == new BigDecimal(150)
        loanResponseBean1.body.investable
        loanResponseBean2.body.allowedInvestmentAmount == new BigDecimal(0)
        !loanResponseBean2.body.investable
    }

    def "cannot invest into unexisting loan"() {
        when:
        registerCustomerAndGetResponse(customerApiWrapper)
        def investmentResponse = investIntoLoanAndGetResponse(customerApiWrapper, LOAN_ID, 150)

        then:
        !investmentResponse.errors.isEmpty()
        investmentResponse.errors.get(0).message == AppDefaults.CANNOT_FIND_LOAN_ID
        investmentResponse.errors.get(0).field == "loanId"
    }

    def "cannot invest for unregistered customer"() {
        when:
        importLoanAndGetResponse(loanApiWrapper)
        def investmentResponse = investIntoLoanWithCustomerIdAndGetResponse(customerApiWrapper, 1L, LOAN_ID, 150)

        then:
        !investmentResponse.errors.isEmpty()
        investmentResponse.errors.get(0).message == AppDefaults.CANNOT_FIND_CUSTOMER_ID
        investmentResponse.errors.get(0).field == "customerId"
    }

    def "cannot invest in uninvestable loan"() {
        when:
        registerCustomerAndGetResponse(customerApiWrapper)
        importLoanAndGetResponse(loanApiWrapper)
        importPaymentAndGetResponse(paymentApiWrapper, 1, LOAN_MAIN_AMOUNT)
        def investmentResponse = investIntoLoanAndGetResponse(customerApiWrapper, LOAN_ID, 150)

        then:
        !investmentResponse.errors.isEmpty()
        investmentResponse.errors.get(0).message == AppDefaults.LOAN_NOT_INVESTABLE
        investmentResponse.errors.get(0).field == "amount"
    }

}
