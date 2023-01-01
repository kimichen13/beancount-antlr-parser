package com.qqviaja.tools.beancount;

import com.qqviaja.tools.antlr.BeancountBotLexer;
import com.qqviaja.tools.antlr.BeancountBotParser;
import com.qqviaja.tools.beancount.entity.Expense;
import com.qqviaja.tools.beancount.listener.BeancountExpenseListener;
import com.qqviaja.tools.beancount.listener.ErrorHandlerListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * <p>Create on 2023/1/1.</p>
 *
 * @author Kimi Chen
 */
public class BeancountAntlrParser {

    public static Expense parser(String input) {
        final BeancountExpenseListener expenseListener = new BeancountExpenseListener();
        try {
            final CharStream charStream = CharStreams.fromString(input);
            final BeancountBotLexer botLexer = new BeancountBotLexer(charStream);
            botLexer.addErrorListener(ErrorHandlerListener.INSTANCE);
            final CommonTokenStream tokenStream = new CommonTokenStream(botLexer);
            final BeancountBotParser parser = new BeancountBotParser(tokenStream);
            parser.addErrorListener(ErrorHandlerListener.INSTANCE);

            final ParseTreeWalker walker = new ParseTreeWalker();


            walker.walk(expenseListener, parser.directive());
        } catch (Exception e) {
            return null;
        }

        return expenseListener.getExpense();
    }

}
