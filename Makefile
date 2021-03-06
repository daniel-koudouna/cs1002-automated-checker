.PHONY: jar

jar: $(wildcard src/cs1002/*.java)
	cd src && \
	javac -cp ../lib/junit.jar cs1002/**/*.java -d ../build
	cd build && \
	rm -rf testing.jar && \
	jar cf testing.jar .
	mkdir -p dist
	cp lib/junit.jar build/testing.jar dist/

compile: jar
	rm -rf temp
	mkdir temp
	cd src && \
	javac -cp ../build/testing.jar:../lib/junit.jar App.java AppTests.java -d ../temp

test: compile
	java -cp temp:lib/junit.jar:dist/testing.jar org.junit.runner.JUnitCore AppTests

run: compile
	java -cp temp:lib/junit.jar:dist/testing.jar AppTests

fancy: compile
	java -cp temp:lib/junit.jar:dist/testing.jar AppTests --color --overflow

terse: compile
	java -cp temp:lib/junit.jar:dist/testing.jar AppTests --color --terse

full: compile
	java -cp temp:lib/junit.jar:dist/testing.jar AppTests --full --fail-fast


clean:
	rm -rf temp
	rm -rf build
	rm -rf dist
