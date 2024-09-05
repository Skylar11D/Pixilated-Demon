plugins {
    id("java")
}

group = "xyz.sk1.minecraft.pxdemo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("net.minestom:minestom-snapshots:8f46913486")
}

tasks.test {
    useJUnitPlatform()
}