package unit

import com.wandoo.homework.base.AppDefaults
import com.wandoo.homework.model.requestbeans.RegisterCustomerRequestBean
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class CustomerRequestBeanShould extends Specification{

    def "request bean validation should contain all errors"() {
        given:
            def customerRequesBean = new RegisterCustomerRequestBean()
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
            "Aaa"           | "Aaa"             | "Bbb"             | 1
            "Aaa@"          | "Aaa"             | "Bbb"             | 1
        }

    def "first name validation should"() {
        given:
        def lastName = "Last Name"
        def email = "some@rmail.com"
        def validationOutput = getValidationMessage(firstName, lastName, email)

        expect:
        validationOutput[0] == errorMessage
        validationOutput[1] == field

        where:
        firstName           |   errorMessage                    | field
        "Aaa"               |   ""                              |   ""
        ""                  |   AppDefaults.CANNOT_BE_EMPTY     |   "firstName"
        null                |   AppDefaults.CANNOT_BE_EMPTY     |   "firstName"
        " "                 |   AppDefaults.CANNOT_BE_EMPTY     |   "firstName"
        "."                 |   AppDefaults.INCORRECT_FORMAT    |   "firstName"
        "Aaa12"             |   AppDefaults.INCORRECT_FORMAT    |   "firstName"
        "Aaa%"              |   AppDefaults.INCORRECT_FORMAT    |   "firstName"
        "Aaaaaaaaaaaaaaaaa" |   AppDefaults.TOO_LONG            |   "firstName"
        "Marc Andre"        |   ""                              |   ""
        "Jean-Paul"         |   ""                              |   ""
        "N\'Zakh"           |   ""                              |   ""
    }

    def "last name validation should"() {
        given:
        def firstName = "First Name"
        def email = "some@rmail.com"
        def validationOutput = getValidationMessage(firstName, lastName, email)

        expect:
        validationOutput[0] == errorMessage
        validationOutput[1] == field

        where:
        lastName            |   errorMessage                    | field
        "Aaa"               |   ""                              |   ""
        ""                  |   AppDefaults.CANNOT_BE_EMPTY     |   "lastName"
        null                  |   AppDefaults.CANNOT_BE_EMPTY     |   "lastName"
        " "                 |   AppDefaults.CANNOT_BE_EMPTY     |   "lastName"
        "."                 |   AppDefaults.INCORRECT_FORMAT    |   "lastName"
        "Aaa12"             |   AppDefaults.INCORRECT_FORMAT    |   "lastName"
        "Aaa%"              |   AppDefaults.INCORRECT_FORMAT    |   "lastName"
        "Aaaaaaaaaaaaaaaaa" |   AppDefaults.TOO_LONG            |   "lastName"
        "van Basten"        |   ""                              |   ""
        "Bart-Williams"     |   ""                              |   ""
        "d\'Amici"          |   ""                              |   ""
    }

    def "email validation should"() {
        given:
        def firstName = "First Name"
        def lastName = "Last Name"
        def validationOutput = getValidationMessage(firstName, lastName, email)

        expect:
        validationOutput[0] == errorMessage
        validationOutput[1] == field

        where:
        email                   |   errorMessage                    | field
        "some@email.com"        |   ""                              |   ""
        ""                      |   AppDefaults.CANNOT_BE_EMPTY     |   "email"
        " "                     |   AppDefaults.CANNOT_BE_EMPTY     |   "email"
        "."                     |   AppDefaults.INCORRECT_FORMAT    |   "email"
        "Aaa12@"                |   AppDefaults.INCORRECT_FORMAT    |   "email"
        "Aaa'@mail.com"         |   AppDefaults.INCORRECT_FORMAT    |   "email"
        "@email.com"            |   AppDefaults.INCORRECT_FORMAT    |   "email"
        "some.email@email.com"  |   ""                              |   ""
        "some.email@e.mail.com" |   ""                              |   ""
        "some-email@e-mail.com" |   ""                              |   ""
    }


    def getValidationMessage(String firstName, String lastName, String email) {
        def customerRequestBean = new RegisterCustomerRequestBean()
        customerRequestBean.firstName = firstName
        customerRequestBean.lastName = lastName
        customerRequestBean.email = email
        def errors = customerRequestBean.validate()
        def errorMessage = errors.isEmpty() ? "" : errors.get(0).message
        def field = errors.isEmpty() ? "" : errors.get(0).field
        new Tuple(errorMessage, field)
    }
}
