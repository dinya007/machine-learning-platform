package ru.tisov.denis.machine.learning.platform.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.jms.support.converter.MessageConverter
import org.springframework.messaging.converter.MessageConversionException
import org.springframework.stereotype.Component
import javax.jms.JMSException
import javax.jms.Session
import javax.jms.TextMessage

/**
 * https://stackoverflow.com/questions/45526371/caused-by-org-springframework-jms-support-converter-messageconversionexception
 */
@Component
class JsonMessageConverter(private val mapper: ObjectMapper) : MessageConverter {

    /**
     * Converts message to JSON. Used mostly by [org.springframework.jms.core.JmsTemplate]
     */
    @Throws(JMSException::class, MessageConversionException::class)
    override fun toMessage(`object`: Any, session: Session): javax.jms.Message {
        try {
            val message = session.createTextMessage()
            message.text = mapper.writeValueAsString(`object`)
            return message
        } catch (e: Exception) {
            throw MessageConversionException("Message cannot be parsed. ", e)
        }
    }

    /**
     * Extracts JSON payload for further processing by JacksonMapper.
     */
    @Throws(JMSException::class, MessageConversionException::class)
    override fun fromMessage(message: javax.jms.Message): Any {
        return (message as TextMessage).text
    }
}