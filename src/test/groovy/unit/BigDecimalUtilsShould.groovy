package unit

import spock.lang.Specification
import static com.wandoo.homework.base.BigDecimalUtils.is

class BigDecimalUtilsShould extends Specification {

    def "test eq"() {
        expect:
        is(left).eq(right) == result
        where:
        left    | right     | result
        100     | 100       | true
        100     | 101       | false
        101     | 100       | false
    }

    def "test gt"() {
        expect:
        is(left).gt(right) == result
        where:
        left    | right     | result
        100     | 100       | false
        100     | 101       | false
        101     | 100       | true
        100.03  | 100.02    | true
    }

    def "test gte"() {
        expect:
        is(left).gte(right) == result
        where:
        left    | right     | result
        100     | 100       | true
        100     | 101       | false
        101     | 100       | true
        100.03  | 100.03    | true
    }

    def "test lt"() {
        expect:
        is(left).lt(right) == result
        where:
        left    | right     | result
        100     | 100       | false
        100     | 101       | true
        101     | 100       | false
        100.03  | 100.04    | true
    }

    def "test lte"() {
        expect:
        is(left).lte(right) == result
        where:
        left    | right     | result
        100     | 100       | true
        100     | 101       | true
        101     | 100       | false
        100.04  | 100.04    | true
    }

    def "test betweenIncluding"() {
        expect:
        is(value).betweenIncluding(left, right) == result
        where:
        value   | left      | right     | result
        5       | 1         | 10        | true
        5       | 5         | 10        | true
        5.02    | 5.01      | 5.03      | true
        5       | 1         | 5         | true
        5       | 1         | 2         | false
        5       | 6         | 10        | false
        5       | 10        | 1         | false
    }
    def "test betweenExcluding"() {
        expect:
        is(value).betweenExcluding(left, right) == result
        where:
        value   | left      | right     | result
        5       | 1         | 10        | true
        5       | 5         | 10        | false
        5.02    | 5.01      | 5.03      | true
        5       | 1         | 5         | false
        5       | 1         | 2         | false
        5       | 6         | 10        | false
        5       | 10        | 1         | false
    }

}
