sourcePackage = \
		BDD

sourcefiles = \
		src/BDD.java \
		src/BoolExpression.java \
		src/Tester.java \
		src/parser/BoolToken.java \
		src/parser/ExprLexer.java \
		src/parser/statements/ExpressionTree.java \
		src/parser/statements/OrStatement.java \
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
