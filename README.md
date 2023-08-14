# Overview
This library helps in communication across multiple JVMs.
It works by serializing messages using MessagePack and sending them to specific channels.
Deserialization happens automatically when a message is received on channel. After deserialization, all handlers for message's class are triggered. If there's no such class in classloader, message is getting skipped.
In order to get this library working properly, you need to have exactly the same path to class in both classloaders of JVMs you want to communicate between.
<br><br>
This library utilizes:
* LettuceCore to manage Redis Messages.
* AMQP for RabbitMQ Messages.

# Messages
Example message of custom class to deserialize using @DirtyField annotation:

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

# Message Handler
Example of a custom handler used to handle DirtyMessage.class:

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

# Messaging Client
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

# Fully Working Example with redis
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

