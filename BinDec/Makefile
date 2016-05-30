sourcePackage = \
		BDD

sourcefiles = \
		src/BDD.java \
		src/BoolExpression.java \
		src/Tester.java \
		src/parser/Token.java \
		src/parser/Lexer.java \
		src/parser/Expressions/ExpressionTree.java \
		src/parser/Expressions/OrExpression.java \
		src/parser/Parser.java

classfiles = $(sourcefiles:.java=.class)

all: $(classfiles)
%.class: %.java
	javac -d . -classpath . $<

clean:
		rm -rf ./cs166/
		rm -f ./src/*.class
		rm -f ./src/**/*.class
		rm -f ./src/**/**/*.class
