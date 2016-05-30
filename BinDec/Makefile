sourcePackage = \
		BDD

sourcefiles = \
		src/BDD.java

classfiles = $(sourcefiles:.java=.class)

all: $(classfiles)
%.class: %.java
		javac -d . -classpath . $<

clean:
		rm -f ./src/*.class
		rm -rf ./cs166/**/*.class
