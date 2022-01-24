Pour télécharger:
```
git clone https://bitbucket.org/requirementyogi/coding-interview.git
```

## How to run from command-line

```
git clone https://bitbucket.org/requirementyogi/coding-interview.git
cd coding-interview
npm install
mvn clean install
# Then run Application.java from the IDE, see below
```

## Build the js files
```
# Open another terminal and run
npm run watch
```

## How to run from an IDE

- Open the code in your IDE (whatever you like best: IntelliJ IDEA, Netbeans, Eclipse, etc),
- Run the class named "Application"
- It will display the application at http://localhost:8080

## Links

This application is built on top of Spring Boot, based on this code: https://spring.io/guides/gs/spring-boot/

## Do you need help to install?

You need:

- Git
- The JDK. We use Java 8 or above.
    - If you don't know the difference between the JRE and JDK, the JRE only contains
      the executable 'java' to execute Java programs ; the JDK also contains 'javac', the java compiler.
    - To check which version you have, use `javac -version`.
    - With Java, they've numbered all their versions in 1.x. For example, "1.8" means "Java 8".
    - If you believe you've installed java/javac, but can't execute it, check your PATH variable contains the path to
      the javac program. Setting the path allows you to omit the path when you execute.
- Maven 3.x

To install programs on Linux (Ubuntu/Debian), the easiest is to use "apt-get install ...". On Mac you can use "brew install ..." after you've installed Homebrew. On Windows, I don't know.

# About the coding interview

- To be clear, we'll discuss in French. We just write the code and interact with customers in English.
- Our goal is to see how you think about new code. We will ask you to change things in this program, we want you to explain how you are going to investigate, and discuss as you do it.
- Feel free to ask us questions during the interview, use Google or StackOverflow and configure your IDE. We want you to feel like you are in a work environment.
- Use the IDE you like best.
- Please use the debug mode in your IDE - We will help you to set it up, feel free to ask.

About the files:

- `app.js` is the javascript executed on the page,
- `HelloController.java` is where the REST APIs answer to the browser's requests,
- `Database.java` is where we store data (we pretend to store it).