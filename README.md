# javafx-calculator
Calculator based on JavaFX, JUnit, Spock, Kotlin

Несколько вечеров за которые я написал калькулятор на Джаве с тестами на Груви а потом переписал на Котлин.
Это не туториал! Здесь все по настоящему - часть я знаю, часть изучаю сразу же. На ваших глазах появляются и фиксятся баги, гуглится и копипастится с СтекОверфлоу.
Это может быть вам полезно для того чтобы ощутить эвристики которые помогают мне. В обычных туториалах всё хорошо и сразу получается, но как только начинающий программист сталкивается с ошибкой то не знает как дальше двигаться. 
Скучно, занудно - но именно так примерно мы и программируем целыми днями.

https://www.youtube.com/playlist?list=PLgYibJocLH9LyI_A8QJgbMq7npg_blXAy

![](screenshot.png)

* [Main.kt](./src/main/kotlin/com/example/javafx/calculator/Main.kt) main Application class
* [calculator.fxml](./src/main/resources/calculator.fxml) contains window layout
* [Calculator.kt](./src/main/kotlin/com/example/javafx/calculator/Calculator.kt) contains logic 
* [CalculatorSpec.groovy](./src/test/groovy/com/example/javafx/calculator/CalculatorSpec.groovy) contains test\specification
