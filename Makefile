.DEFAULT_GOAL := build

install: # установить зависимости
	./gradlew clean install

run-dist:
	./build/install/java-project-lvl2/bin/java-project-lvl2 $(f) $(a) $(b)

help:
	./build/install/java-project-lvl2/bin/java-project-lvl2 -h

check-updates:
	./gradlew dependencyUpdates

lint:
	./gradlew checkstyleMain

lint-test:
	./gradlew checkstyleTest

build:
	./gradlew clean build

test:
	./gradlew test

clean:
	./gradlew clean

report:
	./gradlew jacocoTestReport

.PHONY: build