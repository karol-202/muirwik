import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

version = "0.2.2"
description = "Muirwik Components - a Material UI React wrapper written in Kotlin"

plugins {
    id("kotlin2js") version "1.3.41"
}

dependencies {
    compile(kotlin("stdlib-js", "1.3.41"))
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
