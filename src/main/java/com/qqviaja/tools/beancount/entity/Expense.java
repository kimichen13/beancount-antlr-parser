package com.qqviaja.tools.beancount.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>Create on 2023/1/1.</p>
 *
 * @author Kimi Chen
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Expense {
    private LocalDate date;
    private String payee;
    private String narration;
    private BigDecimal total;
    private BigDecimal actual;
    private String currency;
    private String fromAccount;
    private String toAccount;
    private String discountAccount;
}
