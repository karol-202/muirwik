allprojects {
    repositories {
        jcenter()
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-dev") }
        maven { setUrl("http://dl.bintray.com/kotlin/kotlin-js-wrappers") }
    }
}
