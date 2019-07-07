import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

version = "0.2.2"
description = "Muirwik Components - a Material UI React wrapper written in Kotlin"

buildscript {
    var kotlinVersion: String by extra
    kotlinVersion = "1.3.41"

    repositories {
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
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
}

val kotlinVersion: String by extra

dependencies {
    compile(kotlin("stdlib-js", kotlinVersion))
    compile("org.jetbrains", "kotlin-react", "16.6.0-pre.67-kotlin-1.3.20")
    compile("org.jetbrains", "kotlin-react-dom", "16.6.0-pre.67-kotlin-1.3.20")
    compile("org.jetbrains", "kotlin-styled", "1.0.0-pre.67-kotlin-1.3.20")
}

val compileKotlin2Js: Kotlin2JsCompile by tasks
compileKotlin2Js.kotlinOptions {
    sourceMap = true
    metaInfo = true
    outputFile = "${project.buildDir.path}/js/muirwik-components.js"
    main = "call"
    moduleKind = "commonjs"
}
