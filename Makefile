.DEFAULT_GOAL := build

install: # установить зависимости
	./gradlew clean install

run-dist:
	./build/install/app/bin/app $(f) $(a) $(b)

help:
	./build/install/app/bin/app -h

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