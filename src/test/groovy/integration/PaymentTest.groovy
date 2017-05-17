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
class PaymentTest extends TestBaseSpec{

    @LocalServerPort
    int randomServerPort;

    def paymentApiWrapper
    def loanApiwrapper

    def setup() {
        paymentApiWrapper = new PaymentApiWrapper(randomServerPort)
        loanApiwrapper = new LoanApiWrapper(randomServerPort)
    }

    def "can import payment"() {
        when:
        importLoanAndGetResponse(loanApiwrapper)
        def importPaymentResponse = importPaymentAndGetResponse(paymentApiWrapper, 1, 200)

        then:
        importPaymentResponse.errors.isEmpty()

        when:
        def payment = paymentApiWrapper.getPaymentWithId(1).body

        then:
        payment != null
        payment.loanId == LOAN_ID
        payment.interestAmount == INTEREST_AMOUNT
        payment.mainAmount == new BigDecimal(200)
    }

    def "can import multiple payment for same loan"() {
        when:
        importLoanAndGetResponse(loanApiwrapper)
        importPaymentAndGetResponse(paymentApiWrapper, 1, 200)
        importPaymentAndGetResponse(paymentApiWrapper, 2, 100)
        def payment1 = paymentApiWrapper.getPaymentWithId(1).body
        def payment2 = paymentApiWrapper.getPaymentWithId(2).body

        then:
        payment1 != null
        payment1.loanId == LOAN_ID
        payment1.interestAmount == INTEREST_AMOUNT
        payment1.mainAmount == new BigDecimal(200)

        payment2 != null
        payment2.loanId == LOAN_ID
        payment2.interestAmount == INTEREST_AMOUNT
        payment2.mainAmount == new BigDecimal(100)
    }

    def "cannot import payment for unexisting loan"() {
        when:
        def importPaymentResponse = importPaymentAndGetResponse(paymentApiWrapper, 1, 200)

        then:
        !importPaymentResponse.errors.isEmpty()
        importPaymentResponse.errors.get(0).message == AppDefaults.CANNOT_FIND_LOAN_ID
        importPaymentResponse.errors.get(0).field == "loanId"
    }

    def "cannot import payment with the same id"() {
        when:
        importLoanAndGetResponse(loanApiwrapper)
        importPaymentAndGetResponse(paymentApiWrapper, 1, 200)
        def importPaymentResponse = importPaymentAndGetResponse(paymentApiWrapper, 1, 100)

        then:
        !importPaymentResponse.errors.isEmpty()
        importPaymentResponse.errors.get(0).message == AppDefaults.PAYMENT_ID_ALREADY_EXIST
        importPaymentResponse.errors.get(0).field == "id"
    }

    def "cannot get unexisting payment"() {
        when:
        def paymentResponse = paymentApiWrapper.getPaymentWithId(1)

        then:
        paymentResponse.body == null
        !paymentResponse.errors.isEmpty()
        paymentResponse.errors.get(0).message == AppDefaults.NOT_FOUND
        paymentResponse.errors.get(0).field == "id"
    }
}
