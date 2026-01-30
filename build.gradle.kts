plugins {
    `java-platform`
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

group = "io.github.kamar4k"
version = "1.1.0-${System.getenv("GITHUB_RUN_NUMBER") ?: "local"}"

javaPlatform {
    allowDependencies()
}

dependencies {
    // Импортируем внешние BOM
    api(platform("org.springframework.boot:spring-boot-dependencies:3.5.9"))
    api(platform("org.springframework.cloud:spring-cloud-dependencies:2025.0.0"))

    // Определяем версии для всех зависимостей
    constraints {
        // Kotlin
        api("org.jetbrains.kotlin:kotlin-stdlib:2.0.21")
        api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.0.21")
        api("org.jetbrains.kotlin:kotlin-reflect:2.0.21")
        api("org.jetbrains.kotlin:kotlin-test:2.0.21")
        api("org.jetbrains.kotlin:kotlin-test-junit5:2.0.21")

        // Jackson
        api("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.2")
        api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.2")

        // Spring Boot Starters
        api("org.springframework.boot:spring-boot-starter-data-jpa")
        api("org.springframework.boot:spring-boot-starter-web")
        api("org.springframework.boot:spring-boot-starter-security")
        api("org.springframework.boot:spring-boot-starter-validation")
        api("org.springframework.boot:spring-boot-starter-aop")
        api("org.springframework.boot:spring-boot-starter-actuator")
        api("org.springframework.boot:spring-boot-starter-test")

        // Spring Security
        api("org.springframework.security:spring-security-config:6.5.9")
        api("org.springframework.security:spring-security-web:6.5.9")
        api("org.springframework.security:spring-security-test:6.5.9")

        // Spring Cloud
        api("org.springframework.cloud:spring-cloud-starter-openfeign:4.2.0")
        api("org.springframework.cloud:spring-cloud-starter-contract-stub-runner:4.2.0")

        // Database
        api("org.postgresql:postgresql:42.7.3")
        api("com.h2database:h2:2.3.232")
        api("org.liquibase:liquibase-core:5.0.1")

        // Telegram
        api("org.telegram:telegrambots:6.9.7.1")

        // Arrow
        api("io.arrow-kt:arrow-core:2.0.0")

        // MapStruct
        api("org.mapstruct:mapstruct:1.6.3")
        runtime("org.mapstruct:mapstruct-processor:1.6.3")

        // Logbook
        api("org.zalando:logbook-spring-boot-starter:3.7.2")
        api("org.zalando:logbook-openfeign:3.7.2")
        api("org.zalando:logbook-json:3.7.2")

        // Logging
        api("io.github.microutils:kotlin-logging-jvm:3.0.5")

        // Testing
        api("io.mockk:mockk:1.14.7")
        api("io.mockk:mockk-agent-jvm:1.14.7")
        api("com.ninja-squad:springmockk:4.0.2")
        api("org.junit.platform:junit-platform-launcher:1.11.4")
        api("org.junit.jupiter:junit-jupiter:5.11.4")
        api("org.assertj:assertj-core:3.26.3")
        api("com.tngtech.archunit:archunit-junit5:1.3.0")

        // Utils
        api("org.apache.commons:commons-lang3:3.17.0")
        api("commons-io:commons-io:2.16.1")
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["javaPlatform"])
            artifactId = "helper-bom"

            pom {
                name.set("Helper Platform BOM")
                description.set("BOM for Hepler microservices")
                url.set("https://github.com/kamar4k/helper-bom")
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/kamar4k/helper-bom")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}