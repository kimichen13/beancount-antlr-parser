grammar BeancountBot;

@header {
package com.qqviaja.tools.antlr;
}

//20221214 "JD" "Cloth" 790.00 ~ 783.22 CNY N0923 ~ Expenses:Subscription ~ Expenses:Interest:POS
directive
   : expense
//   | price
//   | balance
//   | open
//   | close
//   | commodity
//   | pad
//   | document
//   | note
//   | event
//   | query
//   | custom
   ;

expense
   : pn = payeeNarration amount account EOL
   | date = DATE pn = payeeNarration amount account EOL
   | pn = payeeNarration actualAmount accountWithDiscount EOL
   | date = DATE pn = payeeNarration actualAmount accountWithDiscount EOL
   ;

payeeNarration
   : payee = STRING PIPE? narration = STRING
   ;

amount
   : e = expr
   | e = expr c = CURRENCY
   ;

account
   : FROM_ACCOUNT TILDE toAccount = ACCOUNT
   ;

actualAmount
   : e = expr TILDE actual = NUMBER
   | e = expr TILDE actual = NUMBER c = CURRENCY
   ;

accountWithDiscount
   : FROM_ACCOUNT TILDE toAccount = ACCOUNT TILDE discountAccount = ACCOUNT
   ;

FROM_ACCOUNT
   : 'N' [0-9] [0-9] [0-9] [0-9]
   | 'WeChat'
   ;

fragment NAccount
   : [0-9] [0-9] [0-9] [0-9]
   ;

expr
   : c = NUMBER # Constant
   | l = expr PLUS r = expr # Addition
   | l = expr MINUS r = expr # Subtraction
   | l = expr ASTERISK r = expr # Multiplication
   | l = expr SLASH r = expr # Division
   | MINUS e = expr # Negation
   | PLUS e = expr # Plus
   | LPAREN e = expr RPAREN # Parenthesised
   ;
   // Single-character flags & other constants

   // Tokens

TRUE
   : 'TRUE'
   ;

FALSE
   : 'FALSE'
   ;

NULL
   : 'NULL'
   ;

BOOL
   : TRUE
   | FALSE
   ;

INCLUDE
   : 'include'
   ;

PUSHTAG
   : 'pushtag'
   ;

POPTAG
   : 'poptag'
   ;

PUSHMETA
   : 'pushmeta'
   ;

POPMETA
   : 'popmeta'
   ;

OPTION
   : 'option'
   ;

OPTIONS
   : 'options'
   ;

PLUGIN
   : 'plugin'
   ;

TXN
   : 'txn'
   ;

BALANCE
   : 'balance'
   ;

OPEN
   : 'open'
   ;

CLOSE
   : 'close'
   ;

COMMODITY
   : 'commodity'
   ;

PAD
   : 'pad'
   ;

EVENT
   : 'event'
   ;

PRICE
   : 'price'
   ;

NOTE
   : 'note'
   ;

DOCUMENT
   : 'document'
   ;

QUERY
   : 'query'
   ;

CUSTOM
   : 'custom'
   ;

PIPE
   : '|'
   ;

ATAT
   : '@@'
   ;

AT
   : '@'
   ;

LCURLLCURL
   : '{{'
   ;

RCURLRCURL
   : '}}'
   ;

LCURL
   : '{'
   ;

RCURL
   : '}'
   ;

COMMA
   : ','
   ;

TILDE
   : '~'
   ;

HASH
   : '#'
   ;

ASTERISK
   : '*'
   ;

SLASH
   : '/'
   ;

COLON
   : ':'
   ;

PLUS
   : '+'
   ;

MINUS
   : '-'
   ;

LPAREN
   : '('
   ;

RPAREN
   : ')'
   ;

SEMICOLON
   : ';'
   ;

DOT
   : '.'
   ;

FLAG
   : '!'
   | '&'
   | '#'
   | '?'
   | '%'
   ;

DATE
   : [0-9] [0-9] [0-9] [0-9] [0-9] [0-9] [0-9] [0-9]
   | [0-9] [0-9] [0-9] [0-9] '-' [0-9] [0-9] '-' [0-9] [0-9]
   | [0-9] [0-9] [0-9] [0-9] '/' [0-9] [0-9] '/' [0-9] [0-9]
   ;

STRING
   : '"' (~ ["\\] | '\\' .)* '"'
   ;
KEY
   : [a-z] ([A-Za-z0-9] | '-' | '_')+ COLON
   ;
fragment TagOrLinkIdentifier
   : ([A-Za-z0-9] | '-' | '_' | '/' | '.')+
   ;
TAG
   : '#' TagOrLinkIdentifier
   ;
LINK
   : '^' TagOrLinkIdentifier
   ;
fragment CurrencyCharset
   : ([A-Z0-9] | '.' | '_' | '-' | '\'')
   ;

CURRENCY
   : [A-Z] CurrencyCharset* [A-Z0-9]?
   | '/' CurrencyCharset* [A-Z] (CurrencyCharset* [A-Z0-9])?
   ;

fragment AccountType
   : [\p{Lu}\p{Lo}] [\p{L}\p{N}\-]*
   ;

fragment AccountName
   : [\p{Lu}\p{Lo}\p{N}] [\p{L}\p{N}\-]*
   ;

ACCOUNT
   : AccountType (COLON AccountName)+
   ;

NUMBER
   : ([0-9]+ | [0-9] [0-9,]+ [0-9]) (DOT [0-9]*)?
   ;

COMMENT
   : SEMICOLON .*? EOL
   ;
   // Trailing whitespace required for INDENT and DEDENT

EOL
   : ('\r' '\n' | '\n' | '\r') ' '*
   ;

WS
   : [ \t]+ -> skip
   ;