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
		plugin("org.jetbrains.kotlin.plugin.spring")
	}

	configure<org.jetbrains.kotlin.allopen.gradle.AllOpenExtension> {
		annotation("jakarta.persistence.Entity")
		annotation("jakarta.persistence.MappedSuperclass")
		annotation("jakarta.persistence.Embeddable")
	}
}