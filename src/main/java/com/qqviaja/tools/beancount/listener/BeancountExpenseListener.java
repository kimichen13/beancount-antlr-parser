package com.qqviaja.tools.beancount.listener;

import com.qqviaja.tools.antlr.BeancountBotBaseListener;
import com.qqviaja.tools.antlr.BeancountBotParser;
import com.qqviaja.tools.beancount.entity.Expense;
import lombok.Getter;
import org.antlr.v4.runtime.Token;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>Create on 2023/1/1.</p>
 *
 * @author Kimi Chen
 */
@Getter
public class BeancountExpenseListener extends BeancountBotBaseListener {

    private Expense expense;

    @Override
    public void enterExpense(BeancountBotParser.ExpenseContext ctx) {
        final BeancountBotParser.PayeeNarrationContext payeeNarrationContext = ctx.payeeNarration();

        final Optional<BeancountBotParser.AmountContext> amountContextOptional = Optional.ofNullable(ctx.amount());
        final Optional<BeancountBotParser.AccountContext> accountContextOptional = Optional.ofNullable(ctx.account());
        final Optional<BeancountBotParser.ActualAmountContext> actualAmountContextOptional = Optional.ofNullable(ctx.actualAmount());
        final Optional<BeancountBotParser.AccountWithDiscountContext> accountWithDiscountContextOptional = Optional.ofNullable(ctx.accountWithDiscount());

        expense = Expense.builder()
                .date(parseDate(Optional.ofNullable(ctx.date).map(Token::getText).orElse(null)))
                .payee(payeeNarrationContext.payee.getText())
                .narration(payeeNarrationContext.narration.getText())
                .build();

        if (amountContextOptional.isPresent() && accountContextOptional.isPresent()) {
            expense.setTotal(new BigDecimal(amountContextOptional.get().e.getText()));
            expense.setActual(expense.getTotal());
            expense.setCurrency(Optional.ofNullable(amountContextOptional.get().c).map(Token::getText).orElse("CNY"));
            expense.setFromAccount(accountContextOptional.get().FROM_ACCOUNT().getText());
            expense.setToAccount(accountContextOptional.get().ACCOUNT().getText());
            expense.setDiscountAccount(null);
        } else if (actualAmountContextOptional.isPresent() && accountWithDiscountContextOptional.isPresent()) {
            expense.setTotal(new BigDecimal(actualAmountContextOptional.get().e.getText()));
            expense.setActual(new BigDecimal(actualAmountContextOptional.get().actual.getText()));
            expense.setCurrency(Optional.ofNullable(actualAmountContextOptional.get().c).map(Token::getText).orElse("CNY"));
            expense.setFromAccount(accountWithDiscountContextOptional.get().FROM_ACCOUNT().getText());
            expense.setToAccount(accountWithDiscountContextOptional.get().toAccount.getText());
            expense.setDiscountAccount(accountWithDiscountContextOptional.get().discountAccount.getText());
        } else {
            expense = null;
        }
    }

    private static LocalDate parseDate(String dateText) {
        return Objects.nonNull(dateText)
                ? LocalDate.parse(dateText.replaceAll("/", "").replaceAll("-", ""), DateTimeFormatter.ofPattern("yyyyMMdd"))
                : LocalDate.now();
    }

}
