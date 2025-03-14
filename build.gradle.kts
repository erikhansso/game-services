plugins {
	kotlin("jvm") version "1.9.25" apply false
	kotlin("plugin.spring") version "1.9.25" apply false
	kotlin("plugin.jpa") version "1.9.25" apply false
	id("org.springframework.boot") version "3.4.3" apply false
	id("io.spring.dependency-management") version "1.1.7" apply false
}

allprojects {
	group = "com.dharmit"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply {
		plugin("org.jetbrains.kotlin.jvm")
		plugin("org.jetbrains.kotlin.plugin.spring")
		plugin("org.jetbrains.kotlin.plugin.jpa")
		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")
	}

	configure<JavaPluginExtension> {
		toolchain {
			languageVersion.set(JavaLanguageVersion.of(21))
		}
	}

	dependencies {
		"implementation"("org.springframework.boot:spring-boot-starter-actuator")
		"implementation"("org.springframework.boot:spring-boot-starter-data-jpa")
		"implementation"("org.springframework.boot:spring-boot-starter-security")
		"implementation"("org.springframework.boot:spring-boot-starter-web")
		"implementation"("com.fasterxml.jackson.module:jackson-module-kotlin")
		"implementation"("org.jetbrains.kotlin:kotlin-reflect")
		"runtimeOnly"("org.postgresql:postgresql")
		"testImplementation"("org.springframework.boot:spring-boot-starter-test")
		"testImplementation"("org.jetbrains.kotlin:kotlin-test-junit5")
		"testImplementation"("org.springframework.security:spring-security-test")
		"testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
	}

	tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "21"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	configure<org.jetbrains.kotlin.allopen.gradle.AllOpenExtension> {
		annotation("jakarta.persistence.Entity")
		annotation("jakarta.persistence.MappedSuperclass")
		annotation("jakarta.persistence.Embeddable")
	}
}