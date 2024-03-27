package com.first.demo;

import java.time.LocalDateTime;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.first.demo.hashtable.CalendarRequest;
import com.first.demo.hashtable.CalendarRequestService;
import com.first.demo.hashtable.Ht;

@SpringBootApplication
public class DemoApplication {

	@Value("spring.mail.host")
	public String host;
	@Value("spring.mail.username")
	public String userName;
	@Value("spring.mail.password")
	public String pwd;
	@Value("spring.mail.port")
	public String port;
	@Value("spring.mail.smtp.starttls.enable")
	public String enable;
	@Value("spring.mail.smtp.auth")
	public String auth;
	
	
	
// spring.mail.host=smtp-mail.outlook.com
// spring.mail.port=587
// spring.mail.username=tanmaymishra21111998@outlook.com
// spring.mail.password=gokvsabjriuyfjfq
// spring.mail.smtp.auth=true
// spring.mail.smtp.starttls.enable=true
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("ok");
		Ht hashtableExec = new Ht();

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setUsername("tanmaymishra21111998@outlook.com");
		mailSender.setPassword("gokvsabjriuyfjfq");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp-mail.outlook.com");
		properties.put("mail.smtp.port", "587");
		mailSender.setJavaMailProperties(properties);
		
		CalendarRequestService c = new CalendarRequestService(mailSender);
		c.sendCalendarInvite("tanmay.mishra@crisil.com", new CalendarRequest.Builder()
				.withOrganizerName("MSD")
				.withSubject("Test calendar invite 17-05 11:30AM")
				.withBody("Please test this Body for the email.\nOk thanks.")
				.withToEmail(new String[] {"tanmaymishra21111998@outlook.com"})
				.withMeetingStartTime(LocalDateTime.now())
				.withMeetingEndTime(LocalDateTime.now().plusHours(1))
				.build());
	}
}
		// CalendarRequestService calendarRequestService = new CalendarRequestService(mailSender);
		// calendarRequestService.sendCalendarInvite(
		// 		"xxx@example.com",
		// 		new CalendarRequest.Builder()
		// 				.withSubject("Test Meeting")
		// 				.withBody("This is a test event")
		// 				.withToEmail("yyy@example.com")
		// 				.withMeetingStartTime(LocalDateTime.now())
		// 				.withMeetingEndTime(LocalDateTime.now().plusHours(1))
		// 				.build()
		// );

