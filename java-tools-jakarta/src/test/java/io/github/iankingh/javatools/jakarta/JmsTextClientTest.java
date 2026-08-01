package io.github.iankingh.javatools.jakarta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.JMSRuntimeException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import java.time.Duration;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JmsTextClientTest {
    private ConnectionFactory connectionFactory;
    private JMSContext context;
    private Destination destination;
    private JmsTextClient client;

    @BeforeEach
    void setUp() {
        connectionFactory = mock(ConnectionFactory.class);
        context = mock(JMSContext.class);
        destination = mock(Destination.class);
        when(connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)).thenReturn(context);
        client = new JmsTextClient(connectionFactory);
    }

    @Test
    void sendsTextAndClosesTheContext() {
        JMSProducer producer = mock(JMSProducer.class);
        when(context.createProducer()).thenReturn(producer);

        client.send(destination, "message");

        verify(producer).send(destination, "message");
        verify(context).close();
    }

    @Test
    void receivesTextAndReturnsEmptyOnTimeout() throws Exception {
        JMSConsumer consumer = mock(JMSConsumer.class);
        TextMessage textMessage = mock(TextMessage.class);
        when(context.createConsumer(destination)).thenReturn(consumer);
        when(consumer.receive(1000)).thenReturn(textMessage);
        when(textMessage.getText()).thenReturn("response");

        assertEquals(Optional.of("response"), client.receive(destination, Duration.ofSeconds(1)));

        when(consumer.receive(1000)).thenReturn(null);
        assertEquals(Optional.empty(), client.receive(destination, Duration.ofSeconds(1)));
    }

    @Test
    void rejectsNonTextMessagesAndInvalidArguments() {
        JMSConsumer consumer = mock(JMSConsumer.class);
        when(context.createConsumer(destination)).thenReturn(consumer);
        when(consumer.receiveNoWait()).thenReturn(mock(Message.class));

        assertThrows(JMSRuntimeException.class, () -> client.receive(destination, Duration.ZERO));
        assertThrows(
                IllegalArgumentException.class,
                () -> client.receive(destination, Duration.ofMillis(-1)));
        assertThrows(NullPointerException.class, () -> client.send(null, "message"));
        assertThrows(NullPointerException.class, () -> client.send(destination, null));
    }

    @Test
    void roundsPositiveSubMillisecondTimeoutsUp() throws Exception {
        JMSConsumer consumer = mock(JMSConsumer.class);
        when(context.createConsumer(destination)).thenReturn(consumer);
        when(consumer.receive(1)).thenReturn(null);

        assertEquals(Optional.empty(), client.receive(destination, Duration.ofNanos(1)));
        verify(consumer).receive(1);
    }
}
