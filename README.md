# Beancount Antlr Parser

A tool to parse input to beancount directives.

## Getting Started

To use this tool, you can add to your `pom.xml` or `build.gradle.kts`.

```xml
<dependency>
  <groupId>com.qqviaja.tools</groupId>
  <artifactId>beancount-antlr-parser</artifactId>
  <version>1.1</version>
</dependency>
```

```kotlin
dependencies {
    implementation("com.qqviaja.tools:beancount-antlr-parser:1.1")
}
```

### Usage

Use [`BeancountAntlrParser#parser`](https://github.com/kimichen13/beancount-antlr-parser/blob/ea1703f3ca55347140a883710524d654093ac5bc/src/main/java/com/qqviaja/tools/beancount/BeancountAntlrParser.java#L26) to parse input to [`Expense`](https://github.com/kimichen13/beancount-antlr-parser/blob/ea1703f3ca55347140a883710524d654093ac5bc/src/main/java/com/qqviaja/tools/beancount/entity/Expense.java#L20) entity.

If any exception, it will return `null`.

## Built With

* [Antlr4](https://github.com/antlr/antlr4) - ANother Tool for Language Recognition
* [Gradle](https://gradle.org/) - Build tool

## Authors

* **Kimi Chen** - *Initial work* - [KimiChen](https://github.com/kimichen13/)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
