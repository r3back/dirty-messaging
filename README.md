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
import com.qualityplus.dirtymessaging.api.handler.MessageHandler;
import com.qualityplus.dirtymessaging.example.message.DirtyMessage;

public final class DirtyMessageHandler implements MessageHandler<DirtyMessage> {
    @Override
    public void accept(DirtyMessage message) {
        /**
         * Do Whatever with received message
         */
    }

    @Override
    public boolean isOneTime() {
        return false;
    }
}
```

# Messaging Service
Redis or RabbitMQ Don't fit on you? no problem, create your own service:

```java
import com.qualityplus.dirtymessaging.api.handler.MessageHandler;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;

public interface DirtyService { 
    
    void publish(MessagePackSerializable message);

    <T extends MessagePackSerializable> void addHandler(Class<T> clazz, MessageHandler<T> consumer);
    
}
```

# Fully Working Example with redis
Easy Messaging Service usage with redis:

```java
import com.qualityplus.dirtymessaging.DirtyService;
import com.qualityplus.dirtymessaging.api.credentials.DirtyCredentials.MessagingType;
import com.qualityplus.dirtymessaging.base.service.factory.RedisMessagingServiceFactory;
import com.qualityplus.dirtymessaging.core.credentials.MessagingCredentials;
import com.qualityplus.dirtymessaging.example.handler.DirtyMessageHandler;
import com.qualityplus.dirtymessaging.example.message.DirtyMessage;

public final class DirtyExample {
    private static final String REDIS_URI = "redis://user:password@host:port";
    private static final String PREFIX = "REDIS_PREFIX";

    public void dirtyExampleMethod(){
        MessagingCredentials credentials = MessagingCredentials.builder()
                .uri(REDIS_URI)
                .prefix(PREFIX)
                .type(MessagingType.REDIS)
                .build();

        DirtyService service = RedisMessagingServiceFactory
                .withCredentials(credentials)
                .withHandler(DirtyMessage.class, new DirtyMessageHandler())
                .create();

        DirtyMessage message = DirtyMessage.builder()
                .someInt(1)
                .someString("someString")
                .someBytes(new byte[]{1, 2, 3})
                .build();

        service.publish(message);
    }
}
```

