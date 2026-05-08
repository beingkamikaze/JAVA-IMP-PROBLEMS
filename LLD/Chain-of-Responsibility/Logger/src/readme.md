# Logger System Design in Java
### Chain of Responsibility + Enum + Singleton + Facade

This README documents the **complete design journey** of a Logger system, including:
- Why certain design patterns are used
- Why alternatives are rejected
- How the final API looks
- How to explain everything in interviews

---

## 1. Problem Statement

Design a Logger system that:
- Supports multiple log levels (DEBUG, INFO, ERROR, FATAL)
- Allows **multiple loggers** to handle the same request
- Avoids `if-else` / `switch` statements
- Is extensible and clean
- Exposes a simple API like:
  ```java
  logger.info("Application started");
  logger.error("Something failed");
2. Core Observation (Very Important)
   A log request may need to be handled by more than one logger.

Example:

INFO log → DEBUG + INFO

ERROR log → DEBUG + INFO + ERROR

This requirement immediately rules out Strategy and points to
👉 Chain of Responsibility.

3. Why NOT Strategy Pattern?
   Strategy means:
   Choose one behavior

Execute it

Done

Logging needs:
Multiple handlers

Cascading behavior

Client should not choose handlers

Aspect	Strategy	Logger Needs
Number of handlers	One	Many
Cascading	❌	✅
Client control	Required	Forbidden
Conclusion: Strategy is not suitable for log routing.

4. Why Chain of Responsibility?
   Chain of Responsibility allows:

A request to flow through multiple handlers

Each handler to decide independently

Client to remain unaware of routing

Flow:

Client → DEBUG → INFO → ERROR → FATAL
Perfect match for logging.

5. Log Levels Design (Enum-Based)
   ❌ Why not public static final int?
   No type safety

Magic numbers

Easy to misuse

❌ Why not ordinal()?
Fragile

Changing enum order silently breaks logic

✅ Best approach: Enum with explicit priority
public enum LogLevels {
DEBUG(1),
INFO(2),
ERROR(3);

    private final int priority;

    LogLevels(int priority) {
        this.priority = priority;
    }

    public int priority() {
        return priority;
    }
}
Key idea:
Enum constants are objects with data, not just names.

6. Base Chain Class – LogHandler
   This class defines the chain mechanics.

public abstract class LogHandler {

    protected LogLevels logLevel;
    protected LogHandler nextLogHandler;

    public void setNextLogHandler(LogHandler nextLogHandler) {
        this.nextLogHandler = nextLogHandler;
    }

    public void logMessage(LogLevels level, String message) {

        if (this.logLevel.priority() <= level.priority()) {
            write(message);
        }

        if (nextLogHandler != null) {
            nextLogHandler.logMessage(level, message);
        }
    }

    protected abstract void write(String message);
}
Key Interview Insight
Forwarding the request is what makes it a chain.
Without forwarding, it’s NOT Chain of Responsibility.

7. Concrete Loggers
   Each logger:

Sets its own level

Implements write()

DebugLogger
public class DebugLogger extends LogHandler {
public DebugLogger() {
this.logLevel = LogLevels.DEBUG;
}

    @Override
    protected void write(String message) {
        System.out.println("DEBUG: " + message);
    }
}
InfoLogger
public class InfoLogger extends LogHandler {
public InfoLogger() {
this.logLevel = LogLevels.INFO;
}

    @Override
    protected void write(String message) {
        System.out.println("INFO: " + message);
    }
}
ErrorLogger
public class ErrorLogger extends LogHandler {
public ErrorLogger() {
this.logLevel = LogLevels.ERROR;
}

    @Override
    protected void write(String message) {
        System.out.println("ERROR: " + message);
    }
}
8. Why Not Call debug.logMessage() from Client?
   Calling:

debug.logMessage(INFO, "Hello");
works but is bad API design:

Leaks chain internals

Confusing to users

Client depends on a concrete handler

9. Facade: Logger Class
   The client should talk to one abstraction.

10. Why Logger Should Be Singleton
    Logging is:

A cross-cutting concern

Global

Configuration-heavy

Multiple Logger instances = ❌
Single shared Logger = ✅

11. Why Enum-Based Singleton?
    Using enum:

JVM guarantees exactly one instance

Thread-safe by default

Serialization-safe

Reflection-safe

Enum Singleton Rule
Each enum constant is instantiated exactly once by the JVM.

12. Logger (Singleton + Facade)
    public enum Logger {

    INSTANCE;

    private final LogHandler chain;

    Logger() {
    LogHandler debug = new DebugLogger();
    LogHandler info = new InfoLogger();
    LogHandler error = new ErrorLogger();

        debug.setNextLogHandler(info);
        info.setNextLogHandler(error);

        this.chain = debug;
    }

    private void logInternal(LogLevels level, String message) {
    chain.logMessage(level, message);
    }

    // Convenience methods
    public void debug(String message) {
    logInternal(LogLevels.DEBUG, message);
    }

    public void info(String message) {
    logInternal(LogLevels.INFO, message);
    }

    public void error(String message) {
    logInternal(LogLevels.ERROR, message);
    }
    }
    Why INSTANCE;?
    It is the single object

Created once during class loading

Replaces getInstance()

13. Client Code (Final API)
    public class Main {
    public static void main(String[] args) {

        Logger logger = Logger.INSTANCE;

        logger.debug("Debugging started");
        logger.info("Application started");
        logger.error("Something went wrong");
    }
    }
    Clean. Expressive. No leakage.

14. How the Flow Works (Example)
    logger.info("Hello");
    Execution:

Client
↓
Logger (Singleton Facade)
↓
DEBUG (logs)
↓
INFO (logs)
↓
ERROR (skips)
15. Patterns Used (Interview Highlight)
    Pattern	Why
    Chain of Responsibility	Multiple log handlers
    Singleton (enum)	One global logger
    Facade	Hide chain complexity
    Enum	Type-safe log levels
16. Interview One-Liners (Memorize)
    Why Chain of Responsibility?
    “Because a log request may be handled by multiple loggers.”

Why not Strategy?
“Strategy supports only one behavior; logging requires many.”

Why enum Singleton?
“JVM enforces single instance, thread-safe by design.”

Why Facade?
“Client should not know about chain internals.”

17. Mental Model (Final)
    Client → Logger → Chain → Handlers
    Or:

One Logger, many handlers, zero client knowledge

18. Possible Extensions (Follow-ups)
    Add WARN, FATAL

Async logging

File / DB logging

Config-driven chains

isInfoEnabled() methods

19. Final Takeaway
    This design:

Avoids conditionals

Is extensible

Matches real frameworks (Log4j / SLF4J)

Is interview-safe and production-ready