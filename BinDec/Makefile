sourcePackage = \
		BDD

sourcefiles = \
		src/BDD/parser/Token.java \
		src/BDD/parser/Lexer.java \
		src/BDD/parser/Expressions/ExpressionTree.java \
		src/BDD/parser/Expressions/OrExpression.java \
		src/BDD/parser/Expressions/AndExpression.java \
		src/BDD/parser/Expressions/NotExpression.java \
		src/BDD/parser/Expressions/VariableExpression.java \
		src/BDD/parser/Expressions/LiteralExpression.java \
		src/BDD/parser/Parser.java \
		src/BDD/BoolExpression.java \
		src/BDD/graphWriter/GraphWriter.java \
		src/BDD/BDD.java \
		src/BDD/Tester.java

classfiles = $(sourcefiles:.java=.class)

all: $(classfiles)
%.class: %.java
	javac -g -d . -classpath . $<

clean:
		rm -rf ./BDD/
		rm -f ./src/**/*.class
		rm -f ./src/**/**/*.class
		rm -f ./src/**/**/**/*.class
