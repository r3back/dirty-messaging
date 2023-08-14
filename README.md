# Dirty Messaging
Easy to use messaging queue library, it's build under the Pub/Sub Design Pattern.

[![Java CI with Gradle](https://github.com/r3back/dirty-messaging/actions/workflows/gradle.yml/badge.svg)](https://github.com/r3back/dirty-messaging/actions/workflows/gradle.yml)
[![](https://jitpack.io/v/r3back/dirty-messaging.svg)](https://jitpack.io/#r3back/dirty-messaging)

## Dependency Usage

### Maven

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

```xml
<dependency>
    <groupId>com.github.r3back</groupId>
    <artifactId>dirty-messaging</artifactId>
    <version>LATEST</version>
</dependency>
```

### Gradle

```groovy
repositories {
    maven { 
        url 'https://jitpack.io' 
    }
}
```

```groovy
dependencies {
    compileOnly 'com.github.r3back:dirty-messaging:LATEST'
}
```

## Building
Dirty-Messaging uses Gradle to handle dependencies & building.

## Used Tools
* [MessagePack](https://github.com/msgpack/msgpack-java) used to binary serialize messages.
* [LettuceCore](https://github.com/lettuce-io/lettuce-core) to handle Redis Messages.
* [AMQP](https://github.com/rabbitmq/rabbitmq-java-client) to handle RabbitMQ Messages.

## Messaging Client
Redis or RabbitMQ Don't fit on you? no problem, create your own client:

```java
import com.qualityplus.dirtymessaging.api.sub.DirtySubscriber;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;

/**
 * Client interface
 */
public interface DirtyClient {
    /**
     * Method to Publish messages to all
     * client subscribers.
     *
     * @param message {@link MessagePackSerializable}
     */
    public void publish(final MessagePackSerializable message);

    /**
     * Add subscriber to handle specific message
     * classes.
     *
     * @param clazz      Message Class
     * @param subscriber {@link DirtySubscriber} handler for message class
     * @param <T>        Generic type that extends from {@link MessagePackSerializable}
     */
    public <T extends MessagePackSerializable> void addSubscriber(final Class<T> clazz,
                                                                  final DirtySubscriber<T> subscriber);
}
```

## Messaging Subscriber
Example of a custom subscriber used to handle DirtyMessage.class:

```java
import com.qualityplus.dirtymessaging.api.sub.DirtySubscriber;
import com.qualityplus.dirtymessaging.core.message.DirtyMessage;

/**
 * Example subscriber that print the string field from received message
 */
public final class PrintSubscriber implements DirtySubscriber<DirtyMessage> {
    /**
     * Handles a message when is received
     *
     * @param message {@link DirtyMessage} received message
     */
    @Override
    public void accept(final DirtyMessage message) {
        System.out.println(message.getSomeString());
    }

    @Override
    public boolean isOneTime() {
        return false;
    }
}
```

## Custom Message
Example message of custom message class using @DirtyField annotation:

```java
import com.qualityplus.dirtymessaging.api.serialization.annotation.AnnotationMessageSerializer;
import com.qualityplus.dirtymessaging.api.serialization.annotation.DirtyField;

public final class DirtyMessage implements AnnotationMessageSerializer {
    @DirtyField
    private final Integer someInt;
    @DirtyField
    private final String someString;
    @DirtyField
    private final byte[] someBytes;

    public DirtyMessage(int someInt, String someString, byte[] someBytes) {
        this.someInt = someInt;
        this.someString = someString;
        this.someBytes = someBytes;
    }
}
```

## Fully Working Example with redis
Easy Messaging Client usage with redis:

```java
import com.qualityplus.dirtymessaging.DirtyClient;
import com.qualityplus.dirtymessaging.api.credentials.Credentials;
import com.qualityplus.dirtymessaging.core.builder.DirtyClientBuilder;
import com.qualityplus.dirtymessaging.core.credentials.DirtyCredentials;
import com.qualityplus.dirtymessaging.core.sub.PrintSubscriber;
import com.qualityplus.dirtymessaging.core.message.DirtyMessage;

public final class DirtyCore {
    private static final String REDIS_URI = "redis://user:password@host:port";
    private static final String PREFIX = "REDIS_PREFIX";

    private void clientCreationExample(){
        final DirtyCredentials credentials = DirtyCredentials.builder()
                .uri(REDIS_URI)
                .prefix(PREFIX)
                .type(Credentials.MessagingType.REDIS)
                .build();

        final DirtyClient client = new DirtyClientBuilder()
                .withCredentials(credentials)
                .withSubscriber(DirtyMessage.class, new PrintSubscriber())
                .create();

        final DirtyMessage message = DirtyMessage.builder()
                .someInt(1)
                .someString("someString")
                .someBytes(new byte[]{1, 2, 3})
                .build();

        client.publish(message);
    }
}
```

