package io.github.iankingh.javatools.jakarta;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.JMSRuntimeException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

/** Small JMS text-message client that scopes every context and consumer. */
public final class JmsTextClient {
    private final ConnectionFactory connectionFactory;

    /** Creates a client backed by the supplied JMS connection factory. */
    public JmsTextClient(ConnectionFactory connectionFactory) {
        this.connectionFactory = Objects.requireNonNull(connectionFactory, "connectionFactory");
    }

    /** Sends a text message with an auto-acknowledged JMS context. */
    public void send(Destination destination, String body) {
        Objects.requireNonNull(destination, "destination");
        Objects.requireNonNull(body, "body");
        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
            context.createProducer().send(destination, body);
        }
    }

    /** Receives one text message, returning empty on timeout. */
    public Optional<String> receive(Destination destination, Duration timeout) throws JMSException {
        Objects.requireNonNull(destination, "destination");
        Objects.requireNonNull(timeout, "timeout");
        if (timeout.isNegative()) {
            throw new IllegalArgumentException("timeout must not be negative");
        }
        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE);
                JMSConsumer consumer = context.createConsumer(destination)) {
            Message message =
                    timeout.isZero()
                            ? consumer.receiveNoWait()
                            : consumer.receive(Math.max(1, timeout.toMillis()));
            if (message == null) {
                return Optional.empty();
            }
            if (!(message instanceof TextMessage textMessage)) {
                throw new JMSRuntimeException("Received a non-text JMS message");
            }
            return Optional.ofNullable(textMessage.getText());
        }
    }
}
