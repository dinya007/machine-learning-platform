package ru.tisov.denis.machine.learning.platform.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.JmsListenerConfigurer
import org.springframework.jms.config.JmsListenerEndpointRegistrar
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.converter.MessageConverter
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory

/**
 * https://stackoverflow.com/questions/45526371/caused-by-org-springframework-jms-support-converter-messageconversionexception
 */
@Configuration
class JmsListenerConfig(val objectMapper: ObjectMapper) : JmsListenerConfigurer {

    @Bean
    fun handlerMethodFactory(): DefaultMessageHandlerMethodFactory {
        val factory = DefaultMessageHandlerMethodFactory()
        factory.setMessageConverter(messageConverter())
        return factory
    }

    @Bean
    fun messageConverter(): MessageConverter {
        val mappingJackson2MessageConverter = MappingJackson2MessageConverter()
        mappingJackson2MessageConverter.objectMapper = objectMapper
        return mappingJackson2MessageConverter
    }

    override fun configureJmsListeners(registrar: JmsListenerEndpointRegistrar) {
        registrar.messageHandlerMethodFactory = handlerMethodFactory()
    }
}