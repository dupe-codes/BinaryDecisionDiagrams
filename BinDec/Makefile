sourcePackage = \
		BDD

sourcefiles = \
		src/cs166/BDD/BDD.java \
		src/cs166/BDD/BoolExpression.java \
		src/cs166/BDD/parser/Token.java \
		src/cs166/BDD/parser/Lexer.java \
		src/cs166/BDD/parser/Expressions/ExpressionTree.java \
		src/cs166/BDD/parser/Expressions/OrExpression.java \
		src/cs166/BDD/parser/Expressions/AndExpression.java \
		src/cs166/BDD/parser/Expressions/NotExpression.java \
		src/cs166/BDD/parser/Expressions/VariableExpression.java \
		src/cs166/BDD/parser/Expressions/LiteralExpression.java \
		src/cs166/BDD/parser/Parser.java \
		src/cs166/BDD/Tester.java

classfiles = $(sourcefiles:.java=.class)

all: $(classfiles)
%.class: %.java
	javac -g -d . -classpath . $<

clean:
		rm -rf ./cs166/
		rm -f ./src/**/**/*.class
		rm -f ./src/**/**/**/*.class
		rm -f ./src/**/**/**/**/*.class
