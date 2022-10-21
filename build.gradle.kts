plugins {
    id("java")
}

group = "ru.gamesphere"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
}

allprojects {
    apply(plugin = "java")

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.24")
        annotationProcessor("org.projectlombok:lombok:1.18.24")
        implementation("com.intellij:annotations:12.0")
        implementation("com.google.inject:guice:5.1.0")
        implementation("com.google.code.gson:gson:2.9.1")
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}