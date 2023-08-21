# Fast Messaging
Easy to use messaging queue library, it's build under the Pub/Sub Design Pattern.

[![Java CI with Gradle](https://github.com/r3back/fast-mq/actions/workflows/gradle.yml/badge.svg)](https://github.com/r3back/fast-mq/actions/workflows/gradle.yml)
[![](https://jitpack.io/v/r3back/fast-mq.svg)](https://jitpack.io/#r3back/fast-mq)

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
    <artifactId>fast-messaging</artifactId>
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
    compileOnly 'com.github.r3back:fast-messaging:LATEST'
}
```

## Building
Fast-Messaging uses Gradle to handle dependencies & building.

## Used Tools
* [MessagePack](https://github.com/msgpack/msgpack-java) used to binary serialize messages.
* [LettuceCore](https://github.com/lettuce-io/lettuce-core) to handle Redis Messages.
* [AMQP](https://github.com/rabbitmq/rabbitmq-java-client) to handle RabbitMQ Messages.

## MQ Client
Redis or RabbitMQ Don't fit on you? no problem, create your own client:

```java
/**
 * MQ Client interface
 */
public interface FastMQClient {
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
     * @param subscriber {@link FastMQSubscriber} handler for message class
     * @param <T>        Generic type that extends from {@link MessagePackSerializable}
     */
    public <T extends MessagePackSerializable> void addSubscriber(final Class<T> clazz,
                                                                  final FastMQSubscriber<T> subscriber);
}

```

## MQ Subscriber
Example of a custom subscriber used to handle FastMQMessage.class:

```java
/**
 * Example subscriber that print the string field from received message
 */
public final class PrintSubscriber implements FastMQSubscriber<FastMQMessage> {
    /**
     * Handles a message when is received
     *
     * @param message {@link FastMQMessage} received message
     */
    @Override
    public void accept(final FastMQMessage message) {
        System.out.println(message.getSomeString());
    }

    /**
     * Retrieves If the subscriber work only 
     * one time
     *
     * @return true if it's one time message
     */
    @Override
    public boolean isOneTime() {
        return false;
    }
}
```

## Custom Message
Example message of custom message class using @FastMQField annotation:

```java
/**
 * Example MQ Message
 */
@Getter
@Builder
public final class FastMQMessage implements AnnotationMessageSerializer {
    @FastMQField
    private final Integer someInt;
    @FastMQField
    private final String someString;
    @FastMQField
    private final byte[] someBytes;

    public FastMQMessage(final int someInt, final String someString, final byte[] someBytes) {
        this.someInt = someInt;
        this.someString = someString;
        this.someBytes = someBytes;
    }
}
```

## Fully Working Example with redis
Easy MQ Client usage with redis:

```java
public final class FastMQCore {
    private static final String REDIS_URI = "redis://user:password@host:port";
    private static final String PREFIX = "REDIS_PREFIX";

    private void clientCreationExample(){
        final FastMQCredentials credentials = FastMQCredentials.builder()
                .uri(REDIS_URI)
                .prefix(PREFIX)
                .type(Credentials.MessagingType.REDIS)
                .build();

        final FastMQClient client = new FastMQClientBuilder()
                .withCredentials(credentials)
                .withSubscriber(FastMQMessage.class, new PrintSubscriber())
                .create();

        final FastMQMessage message = FastMQMessage.builder()
                .someInt(1)
                .someString("someString")
                .someBytes(new byte[]{1, 2, 3})
                .build();

        client.publish(message);
    }
}
```

