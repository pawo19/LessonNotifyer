import org.gradle.model.internal.core.ModelNodes.withType

plugins {
    id("java")

}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.poi:poi:5.2.3")
    implementation("com.sun.mail:javax.mail:1.6.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks{
    val ENABLE_PREVIEW = "--enable-preview"
   withType<JavaCompile>() {
        options.compilerArgs.add(ENABLE_PREVIEW)
        options.compilerArgs.add("-Xlint:preview")
        options.release.set(17)
    }
    withType<Test>() {
        useJUnitPlatform()
        jvmArgs?.add(ENABLE_PREVIEW)
    }
    withType<JavaExec>() {
        jvmArgs?.add(ENABLE_PREVIEW)
    }

}