import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    application
}

group = "me.jmam"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-reflect
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.6.21")
    compileOnly("org.jetbrains.kotlin:kotlin-reflect:1.6.21")
    implementation("org.jdatepicker:jdatepicker:1.3.4")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}
