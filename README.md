# Chess bot tournament Kotlin API
Kotlin bindings for Shiro's Chess bot API for the Neurocord Chess bot tournament

## Usage
- include the built jar in your project, either the built jar or via jitpack

```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://www.jitpack.io") }
}
dependencies {
    implementation("com.github.Superbox2147:chess-api-kt:<latest tag>")
}
```
or via maven:
pom.xml
```xml

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://www.jitpack.io</url>
    </repository>
</repositories>
```
dependency:
```xml

<dependency>
    <groupId>com.github.Superbox2147</groupId>
    <artifactId>chess-api-kt</artifactId>
    <version>latest tag</version>
</dependency>
```
- add `--enable-native-access=ALL-UNNAMED` to your application's  jvm arguments. This is required so java can load the native binary that is a part of this library.
 with gradle:
```kotlin
application {
    mainClass.set("main class")
    applicationDefaultJvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
}
```

To use it with cutechess, wrap your application in a shell script and pick that script as the engine.
Gradle's application plugin does this for you.

## Example
Simple bot that makes random moves, same as the one found in the original repository
```kt
fun main() {
    for (i in 0..<500) {
        val board = ChessApi.getBoard()
        val moves = ChessApi.getLegalMoves(board)
        ChessApi.chessPush(moves.random())
        ChessApi.chessDone()
    }
}
```