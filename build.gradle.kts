import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.1.9.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
//	kotlin("jvm") version "1.2.71"
	kotlin("jvm") version "1.3.20"
//	kotlin("plugin.spring") version "1.2.71"
	kotlin("plugin.spring") version "1.3.20"
}

group = "ru.tisov.denis"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-activemq")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")
//	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//	implementation("com.fasterxml.jackson.core:jackson-databind")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	// https://mvnrepository.com/artifact/io.rest-assured/rest-assured
	testImplementation("io.rest-assured:spring-mock-mvc:3.3.0")


}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
