package ru.kerporation.breddit.service

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import ru.kerporation.breddit.dto.NotificationEmail

private val logger = KotlinLogging.logger {}

@Service
class MailService(
	private val mailSender: JavaMailSender,
	private val mailContentBuilder: MailContentBuilder,
	@Value("\${breddit.mail.from}") private val bredditInfoMail: String
) {

	@Async
	fun sendMail(notificationEmail: NotificationEmail) {
		val messagePreparator = MimeMessagePreparator { mimeMessage ->
			MimeMessageHelper(mimeMessage).apply {
				setFrom(bredditInfoMail)
				setTo(notificationEmail.recipient)
				setSubject(notificationEmail.subject)
				setText(mailContentBuilder.build(notificationEmail.body))
			}
		}
		try {
			logger.info("Текст письма: ${notificationEmail.body}")
			mailSender.send(messagePreparator)
			logger.info("Письмо активации отправлено на ${notificationEmail.recipient}")
		} catch (e: MailException) {
			logger.error("Ошибка при отправке сообщения на ${notificationEmail.recipient}, установите правильные credentials mailtrap.io")
		}
	}
}
