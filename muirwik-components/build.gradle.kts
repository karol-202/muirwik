import com.jfrog.bintray.gradle.BintrayExtension
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import java.io.FileInputStream
import java.util.*

version = "0.4.0-SNAPSHOT"
description = "Muirwik Components - a Material UI React wrapper written in Kotlin"

buildscript {
    var kotlinVersion: String by extra
    kotlinVersion = "1.3.41"

    repositories {
        jcenter()
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
    }

    dependencies {
        classpath(kotlin("gradle-plugin", kotlinVersion))
    }
}

apply {
    plugin("kotlin2js")
}

plugins {
    java // Not sure why this is needed, but it makes the dependencies below work.
    id("com.moowork.node") version "1.2.0"
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.4"
}

val kotlinVersion: String by extra

repositories {
    jcenter()
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-dev") }
    maven { setUrl("http://dl.bintray.com/kotlin/kotlin-js-wrappers") }
}

dependencies {
    val kotlinJsVersion = "pre.80-kotlin-$kotlinVersion"
    val kotlinReactVersion = "16.8.6-$kotlinJsVersion"

    implementation(kotlin("stdlib-js", kotlinVersion))
    implementation("org.jetbrains", "kotlin-react", kotlinReactVersion)
    implementation("org.jetbrains", "kotlin-react-dom", kotlinReactVersion)
    implementation("org.jetbrains", "kotlin-styled", "1.0.0-$kotlinJsVersion")
}


val compileKotlin2Js: Kotlin2JsCompile by tasks
compileKotlin2Js.kotlinOptions {
    sourceMap = true
    metaInfo = true
    outputFile = "${project.buildDir.path}/js/muirwik-components.js"
    main = "call"
    moduleKind = "commonjs"
}

// TODO: Look at javadoc/kdoc/dokka
//val dokka: DokkaTask by tasks
//tasks.register<Jar>("KDocJar") {
//    from(tasks.javadoc)
//    classifier = "javadoc"
//    from(tasks.dokka)
//}

tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}

val publicationName = "mavenJava"
publishing {
    repositories {
        mavenLocal()
    }

    publications {
        create<MavenPublication>(publicationName) {
            from(components["java"])
//            artifact(tasks["KDocJar"])
            artifact(tasks["sourcesJar"])

            pom {
                name.set("Muirwik Components")
                description.set(project.description)
                url.set("https://github.com/cfnz/muirwik")
                licenses {
                    license {
                        name.set("Mozilla Public License 2.0")
                    }
                }
                scm {
                    connection.set("https://github.com/cfnz/muirwik.git")
                    url.set("https://github.com/cfnz/muirwik")
                }
            }
        }
    }
}

bintray {
    // Bintray keys are kept in a local, non version controlled, properties file
    if (project.file("local.properties").exists()) {
        val properties = Properties()
        properties.load(FileInputStream(project.file("local.properties")))
        fun findProperty(propertyName: String) = properties[propertyName] as String?
    
        user = findProperty("bintray.user")
        key = findProperty("bintray.apikey")
        publish = true
        override = true
    
        pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
            // Mandatory fields
            repo = project.parent?.name
            name = "${project.group}:${project.name}"
            setLicenses("MPL-2.0")
            vcsUrl = "https://github.com/cfnz/muirwik"
    
            // Some optional fields
            description = project.description
            desc = description
            websiteUrl = "https://github.com/cfnz/muirwik"
            issueTrackerUrl = "https://github.com/cfnz/muirwik/issues"
            githubRepo = "https://github.com/cfnz/muirwik"
            setLabels("kotlin", "material-ui", "react")
        })
        setPublications(publicationName)
    }
}

