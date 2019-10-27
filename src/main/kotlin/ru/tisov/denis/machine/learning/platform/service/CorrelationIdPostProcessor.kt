package ru.tisov.denis.machine.learning.platform.service

import org.apache.activemq.command.ActiveMQQueue
import org.springframework.jms.core.MessagePostProcessor
import javax.jms.Message

class CorrelationIdPostProcessor(val correlationId: String, val responseQueue: String) : MessagePostProcessor {
    override fun postProcessMessage(message: Message): Message {
        message.jmsCorrelationID = correlationId
        message.jmsReplyTo = ActiveMQQueue(responseQueue)
        return message
    }
}
