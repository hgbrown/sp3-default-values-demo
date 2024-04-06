# ConstructorBinding and default values

In Spring Boot, the `@ConstructorBinding` annotation is part of the Spring Framework that supports configuration properties binding. 
This annotation is particularly useful when you want to bind your configuration properties to an immutable class using a constructor. 
Traditionally, Spring Boot binds configuration properties to beans through setter methods or directly to fields. 
However, with `@ConstructorBinding`, you can ensure that your configuration properties are immutable once set, enhancing the safety and integrity of your application configuration.

When you use `@ConstructorBinding`, you declare that the binding should happen through the constructor of your class. 
This means that Spring Boot will use the constructor parameters to pass the configuration properties directly, rather than setting them via setters or field injection. 
This approach is well-suited for creating immutable configuration property classes.

Kotlin's data classes are a perfect fit for immutable configuration properties, as they inherently support immutability and provide a concise syntax for declaring classes that mainly hold data. 
When combined with `@ConstructorBinding`, you can define configuration properties in a type-safe and immutable manner, which is highly beneficial for the robustness and clarity of your application configuration.

Kotlin's data classes also allow you to easily specify default values for your properties. 
By providing default values, you allow these properties to be optional in your configuration files (`application.properties` or `application.yml`). 
If a property is omitted in the configuration, Spring Boot will use the default value specified in your data class. 
This feature is particularly useful for creating flexible and resilient configuration setups where not all properties are mandatory, or sensible defaults are desired.

However, there is a fly in the ointment when you want to use the `@ConstructorBinding` annotation with default values.
For example, consider the following Kotlin data class:

```kotlin
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "greeting")
data class GreetingConfig @ConstructorBinding constructor(
    val salutation: String,
)

```

This works perfectly well when specifying the property:

```properties
greeting.salutation=Hi
```

However, simply adding a default value to the property and removing the configuration value:

```kotlin
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "greeting")
data class GreetingConfig @ConstructorBinding constructor(
    val salutation: String = "Hello",
)

```

```properties
#greeting.salutation=Hi
```

will not work:

```log
Exception in thread "main" java.lang.IllegalStateException: dev.hbrown.demo.greeting.GreetingConfig declares @ConstructorBinding on a no-args constructor
	at org.springframework.util.Assert.state(Assert.java:97)
	at org.springframework.boot.context.properties.bind.DefaultBindConstructorProvider$Constructors.getConstructorBindingAnnotated(DefaultBindConstructorProvider.java:168)
	at org.springframework.boot.context.properties.bind.DefaultBindConstructorProvider$Constructors.getConstructors(DefaultBindConstructorProvider.java:107)
	at org.springframework.boot.context.properties.bind.DefaultBindConstructorProvider.getBindConstructor(DefaultBindConstructorProvider.java:55)
    ...

```

## Solution 1: Removing @ConstructorBinding

The simplest solution is to remove the `@ConstructorBinding` annotation:

```kotlin
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "greeting")
data class GreetingConfig (
    val salutation: String = "Hello",
)

```

This will once again cause the application to start up and use the specified default value.

## Solution 2: Use @DefaultValue annotation

An alternate approach is to use Spring's `@DefaultValue` annotation instead of specifying the default value on the property itself.

```kotlin
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.boot.context.properties.bind.DefaultValue

@ConfigurationProperties(prefix = "greeting")
data class GreetingConfig @ConstructorBinding constructor(
    @param:DefaultValue("Yo")
    val salutation: String,
)

```

This approach can work even for more complicated bindings such as binding to a `List<String>`:

```kotlin
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.boot.context.properties.bind.DefaultValue

@ConfigurationProperties(prefix = "greeting")
data class GreetingConfig @ConstructorBinding constructor(
    @param:DefaultValue("Yo")
    val salutation: String,

    @param:DefaultValue(
        value = ["Rachel", "Monica", "Phoebe", "Joey", "Ross", "Chandler"]
    )
    val names: List<String>,
)

```
