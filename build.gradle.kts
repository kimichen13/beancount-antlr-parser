import java.net.URI

plugins {
    java
    groovy
    antlr
    `maven-publish`
    signing
}

group = "com.qqviaja.tools"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    antlr("org.antlr:antlr4:4.11.1")
    implementation("org.antlr:antlr4-runtime:4.11.1")

    implementation("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    testImplementation("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")


    testImplementation("org.spockframework:spock-core:2.0-groovy-3.0")
    testImplementation("org.codehaus.groovy:groovy-all:3.0.14")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

java {
    withJavadocJar()
    withSourcesJar()
}

artifacts {
    archives(tasks.named("javadocJar"))
    archives(tasks.named("sourcesJar"))
}

tasks.named("sourcesJar").configure { dependsOn("generateGrammarSource") }

publishing {

    repositories {
        maven {
            val releaseRepo = URI("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotRepo = URI("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (!(version as String).endsWith("SNAPSHOT")) releaseRepo else snapshotRepo
            credentials {
                username = properties["nexusUsername"].toString()
                password = properties["nexusPassword"].toString()
            }
        }
    }

    publications {
        create<MavenPublication>("Sonatype") {
            groupId = group.toString()
            artifactId = rootProject.name
            version = version

            from(components["java"])

            pom {
                groupId = group.toString()
                name.set("Beancount Antlr Parser")
                description.set("A tool to parse input to beancount directives.")
                url.set("https://github.com/kimichen13/beancount-antlr-parser")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/kimichen13/beancount-antlr-parser/blob/main/LICENSE.md")
                    }
                }
                developers {
                    developer {
                        id.set("kimicubao")
                        name.set("Kimi Chen")
                        email.set("kimichen13@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git@github.com:kimichen13/beancount-antlr-parser.git")
                    developerConnection.set("scm:git:ssh://github.com:kimichen13/beancount-antlr-parser.git")
                    url.set("https://github.com/kimichen13/beancount-antlr-parser")
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["Sonatype"])
}
