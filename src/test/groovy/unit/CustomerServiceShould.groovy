package unit

import com.wandoo.homework.services.CustomerService
import spock.lang.Specification

class CustomerServiceShould extends Specification {

    def "test available investment amount"() {
        given:
        def customerService = new CustomerService()
        expect:
        customerService.getActualInvestmentAmount(requested, maxPossible) == result
        where:
        requested    | maxPossible      | result
        100          | 100              | 100
        100          | 150              | 100
        150          | 100              | 100
        150          | 0                | 0
    }
}
