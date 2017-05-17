package helpers

import com.wandoo.homework.requestbeans.RegisterCustomerRequestBean
import com.wandoo.homework.requestbeans.InvestmentRequestBean
import com.wandoo.homework.requestbeans.ImportLoanRequestBean
import com.wandoo.homework.requestbeans.ImportPaymentRequestBean
import spock.lang.Specification
import wrappers.CustomerApiWrapper
import wrappers.LoanApiWrapper
import wrappers.PaymentApiWrapper

class TestBaseSpec extends Specification {
    //customer defaults
    final EMAIL = "some@email.com"
    final FIRST_NAME = "First Name"
    final LAST_NAME = "Last Name"

    //payment defaults
    final INTEREST_AMOUNT = new BigDecimal(20)

    //loan defaults
    final LOAN_ID = 1L
    final LOAN_MAIN_AMOUNT = new BigDecimal(500)
    final LOAN_INTEREST_RATE = new BigDecimal(5.5)

    def registerCustomerAndGetResponse(CustomerApiWrapper customerApiWrapper) {
        def customerRequestBean = new RegisterCustomerRequestBean()
        customerRequestBean.email = EMAIL
        customerRequestBean.firstName = FIRST_NAME
        customerRequestBean.lastName = LAST_NAME

        customerApiWrapper.registerCustomer(customerRequestBean)
    }

    def importLoanAndGetResponse(LoanApiWrapper loanApiWrapper) {
        def loanRequestBean = new ImportLoanRequestBean()
        loanRequestBean.setId(LOAN_ID)
        loanRequestBean.setMainAmount(LOAN_MAIN_AMOUNT)
        loanRequestBean.setInterestRate(LOAN_INTEREST_RATE)

        loanApiWrapper.importLoan(loanRequestBean)
    }

    def importLoanWithIdAndAmountAndGetResponse(LoanApiWrapper loanApiWrapper, Long id, BigDecimal mainAmount) {
        def loanRequestBean = new ImportLoanRequestBean()
        loanRequestBean.setId(id)
        loanRequestBean.setMainAmount(mainAmount)
        loanRequestBean.setInterestRate(LOAN_INTEREST_RATE)

        loanApiWrapper.importLoan(loanRequestBean)
    }

    def importPaymentAndGetResponse(PaymentApiWrapper paymentApiWrapper, Long id, BigDecimal mainAmount) {
        def paymentRequestBean = new ImportPaymentRequestBean()
        paymentRequestBean.setId(id)
        paymentRequestBean.setLoanId(LOAN_ID)
        paymentRequestBean.setMainAmount(mainAmount)
        paymentRequestBean.setInterestAmount(INTEREST_AMOUNT)

        paymentApiWrapper.importPayment(paymentRequestBean)
    }

    def importPaymentForLoanAndGetResponse(PaymentApiWrapper paymentApiWrapper, Long id, Long loanId, BigDecimal mainAmount) {
        def paymentRequestBean = new ImportPaymentRequestBean()
        paymentRequestBean.setId(id)
        paymentRequestBean.setLoanId(loanId)
        paymentRequestBean.setMainAmount(mainAmount)
        paymentRequestBean.setInterestAmount(INTEREST_AMOUNT)

        paymentApiWrapper.importPayment(paymentRequestBean)
    }

    def investIntoLoanAndGetResponse(CustomerApiWrapper customerApiWrapper, Long loanId, BigDecimal amount) {
        def lastCustomerResponse = customerApiWrapper.getLastRegisteredCustomer()
        if (lastCustomerResponse.body == null){
            return lastCustomerResponse
        }

        def investmentRequestBean = new InvestmentRequestBean()
        investmentRequestBean.setLoanId(loanId)
        investmentRequestBean.setCustomerId(lastCustomerResponse.body.id)
        investmentRequestBean.setAmount(amount)

        customerApiWrapper.invest(investmentRequestBean)
    }

    def investIntoLoanWithCustomerIdAndGetResponse(CustomerApiWrapper customerApiWrapper, Long customerId, Long loanId, BigDecimal amount) {
        def investmentRequestBean = new InvestmentRequestBean()
        investmentRequestBean.setLoanId(loanId)
        investmentRequestBean.setCustomerId(customerId)
        investmentRequestBean.setAmount(amount)

        customerApiWrapper.invest(investmentRequestBean)
    }

}
