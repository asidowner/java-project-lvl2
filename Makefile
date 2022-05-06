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

build:
	./gradlew clean build

clean:
	./gradlew clean

.PHONY: build