package com.qqviaja.tools.antlr

import com.qqviaja.tools.beancount.BeancountAntlrParser
import com.qqviaja.tools.beancount.entity.Expense
import spock.lang.Specification

import java.time.LocalDate

/**
 *
 * <p>Create on 2023/1/1.</p>
 * @author Kimi Chen
 */
class BeancountBotParserTest extends Specification {

    def "test beancount bot"() {

        expect:
        result == BeancountAntlrParser.parser(input)

        where:
        input                                                                                                   | result
        "2022-12-14 \"JD\" \"Cloth\" 790.00 CNY N0923 ~ Expenses:Subscription ~ Expenses:Interest:POS"          | null
        "2022-12-14 \"JD\" \"Cloth\" 790.00 CNY N0923 ~ Expenses:Subscription"                                  | Expense.builder().date(LocalDate.of(2022, 12, 14)).payee("\"JD\"").narration("\"Cloth\"").currency("CNY").total(new BigDecimal("790.00")).actual(new BigDecimal("790.00")).fromAccount("N0923").toAccount("Expenses:Subscription").discountAccount(null).build()
        "2022/12/14 \"JD\" \"Cloth\" 790.00 ~ 783.22 CNY N0923 ~ Expenses:Subscription ~ Expenses:Interest:POS" | Expense.builder().date(LocalDate.of(2022, 12, 14)).payee("\"JD\"").narration("\"Cloth\"").currency("CNY").total(new BigDecimal("790.00")).actual(new BigDecimal("783.22")).fromAccount("N0923").toAccount("Expenses:Subscription").discountAccount("Expenses:Interest:POS").build()
        "20221214 \"JD\" \"Cloth\" 790.00 N0923 ~ Expenses:Subscription"                                        | Expense.builder().date(LocalDate.of(2022, 12, 14)).payee("\"JD\"").narration("\"Cloth\"").currency("CNY").total(new BigDecimal("790.00")).actual(new BigDecimal("790.00")).fromAccount("N0923").toAccount("Expenses:Subscription").discountAccount(null).build()
        "\"JD\" \"Cloth\" 790.00 ~ 783.22 USD WeChat ~ Expenses:Subscription ~ Expenses:Interest:POS"           | Expense.builder().date(LocalDate.now()).payee("\"JD\"").narration("\"Cloth\"").currency("USD").total(new BigDecimal("790.00")).actual(new BigDecimal("783.22")).fromAccount("WeChat").toAccount("Expenses:Subscription").discountAccount("Expenses:Interest:POS").build()

    }
}
