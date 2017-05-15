package unit

import com.wandoo.homework.requestbeans.CustomerRequestBean
import spock.lang.Specification

class CustomerRequestBeanShould extends Specification{
    def "request bean validation should"() {
        given:
            def customerRequesBean = new CustomerRequestBean()
            customerRequesBean.email = email
            customerRequesBean.firstName = firstName
            customerRequesBean.lastName = lastName
        expect:
            customerRequesBean.validate().size() == errors
        where:
            email           | firstName         | lastName          | errors
            "aaa@bbb.ccc"   | "Aaaa"            | "Bbb"             | 0
            "aaa@bbb.ccc"   | ""                | "Bbb"             | 1
            "aaa@bbb.ccc"   | ""                | ""                | 2
            ""              | ""                | ""                | 3
            ""              | "Aaa"             | "Bbb"             | 1
            "Aaa"           | "Aaa"             | "Bbb"             | 1
        }

//    def "first name validation should"() {
//        given:
//        def customerRequesBean = new CustomerRequestBean()
//        customerRequesBean.email = "valid@email.com"
//        customerRequesBean.firstName = firstName
//        customerRequesBean.lastName = "Valid Last Name"
//        expect:
//        customerRequesBean.validate().size() == errors
//
//        where:
//        firstName       |
//    }
}
