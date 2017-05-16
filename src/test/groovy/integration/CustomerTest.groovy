package integration

import com.wandoo.homework.Application
import com.wandoo.homework.base.AppDefaults
import com.wandoo.homework.requestbeans.CustomerRequestBean
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import spock.lang.Unroll
import wrappers.ClientApiWrapper

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerTest extends Specification{

    @LocalServerPort
    int randomServerPort;

    final EMAIL = "some@email.com"
    final FIRST_NAME = "First Name"
    final LAST_NAME = "Last Name"
    def clientApiWrapper
    def customerRequestBean

    def setup() {
        clientApiWrapper = new ClientApiWrapper(randomServerPort)

        customerRequestBean = new CustomerRequestBean()
        customerRequestBean.email = EMAIL
        customerRequestBean.firstName = FIRST_NAME
        customerRequestBean.lastName = LAST_NAME
    }

    def "customer can register"() {
        when:
        def registerResponse = clientApiWrapper.registerCustomer(customerRequestBean)

        then:
        registerResponse.errors.isEmpty()

        when:
        def customer = clientApiWrapper.getLastRegisteredCustomer().body

        then:
        customer != null
        customer.email == EMAIL
        customer.firstName == FIRST_NAME
        customer.lastName == LAST_NAME
    }

    def "cannot register with the same email"() {
        when:
        def registerResponse = clientApiWrapper.registerCustomer(customerRequestBean)

        then:
        registerResponse.errors.isEmpty()

        when:
        registerResponse = clientApiWrapper.registerCustomer(customerRequestBean)

        then:
        registerResponse.errors.size() == 1
        registerResponse.errors.get(0).message == AppDefaults.CUSTOMER_ALREADY_REGISTERED_WITH_EMAIL
        registerResponse.errors.get(0).field == "email"
    }
}
