package integration

import com.wandoo.homework.Application
import com.wandoo.homework.base.AppDefaults
import helpers.TestBaseSpec
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import wrappers.CustomerApiWrapper

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerTest extends TestBaseSpec{

    @LocalServerPort
    int randomServerPort;

    def customerApiWrapper

    def setup() {
        customerApiWrapper = new CustomerApiWrapper(randomServerPort)
    }

    def "customer can register"() {
        when:
        def registerResponse = registerCustomerAndGetResponse(customerApiWrapper)

        then:
        registerResponse.errors.isEmpty()

        when:
        def customer = customerApiWrapper.getLastRegisteredCustomer().body

        then:
        customer != null
        customer.email == EMAIL
        customer.firstName == FIRST_NAME
        customer.lastName == LAST_NAME
    }

    def "cannot register with the same email"() {
        when:
        def registerResponse = registerCustomerAndGetResponse(customerApiWrapper)

        then:
        registerResponse.errors.isEmpty()

        when:
        registerResponse = registerCustomerAndGetResponse(customerApiWrapper)

        then:
        registerResponse.errors.size() == 1
        registerResponse.errors.get(0).message == AppDefaults.CUSTOMER_ALREADY_REGISTERED_WITH_EMAIL
        registerResponse.errors.get(0).field == ""
    }
}
