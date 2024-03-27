package com.first.demo.hashtable;

import java.time.format.DateTimeFormatter;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.activation.DataHandler;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;

public class CalendarRequestService {
    private JavaMailSender mailSender;
 
    public CalendarRequestService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
 
    public void sendCalendarInvite(String fromEmail, CalendarRequest calendarRequest) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        // helper.setFrom(fromEmail);
        helper.setTo(calendarRequest.getToEmail());
        helper.setCc(new String[] {"Zeeshan.Ansari@ext-crisil.com"});
        // helper.setSubject("Meeting Invitation");

        mimeMessage.addHeaderLine("method=REQUEST");
        mimeMessage.addHeaderLine("charset=UTF-8");
        mimeMessage.addHeaderLine("component=VEVENT");
        mimeMessage.setFrom(new InternetAddress(fromEmail));
        // mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(calendarRequest.getToEmail()));
        mimeMessage.setSubject(calendarRequest.getSubject());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
        StringBuilder sendersList = new StringBuilder("");
        for(String sender : calendarRequest.getToEmail()) {
            sendersList.append("ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:" + sender + "\n");
        }
        StringBuilder builder = new StringBuilder();
        builder.append("BEGIN:VCALENDAR\n" +
                "METHOD:REQUEST\n" +
                "PRODID:Microsoft Exchange Server 2010\n" +
                "VERSION:2.0\n" +
                "BEGIN:VTIMEZONE\n" +
                "TZID:Asia/Kolkata\n" +
                "END:VTIMEZONE\n" +
                "BEGIN:VEVENT\n" +
                sendersList +
                "ORGANIZER;CN=" + calendarRequest.getOrganizerName() + ":MAILTO:" + fromEmail + "\n" +
                "DESCRIPTION;LANGUAGE=en-US:" + calendarRequest.getBody() + "\n" +
                "UID:"+calendarRequest.getUid()+"\n" +
                "SUMMARY;LANGUAGE=en-US:" + calendarRequest.getSubject()+ "\n" +
                "DTSTART:" + formatter.format(calendarRequest.getMeetingStartTime()).replace(" ", "T") + "\n" +
                "DTEND:" + formatter.format(calendarRequest.getMeetingEndTime()).replace(" ", "T") + "\n" +
                "CLASS:PUBLIC\n" +
                "PRIORITY:5\n" +
                "DTSTAMP:20200922T105302Z\n" +
                "TRANSP:OPAQUE\n" +
                "STATUS:CONFIRMED\n" +
                "SEQUENCE:$sequenceNumber\n" +
                "LOCATION;LANGUAGE=en-US:Microsoft Teams Meeting\n" +
                "BEGIN:VALARM\n" +
                "DESCRIPTION:REMINDER\n" +
                "TRIGGER;RELATED=START:-PT15M\n" +
                "ACTION:DISPLAY\n" +
                "END:VALARM\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR");
 
        MimeBodyPart messageBodyPart = new MimeBodyPart();
 
        messageBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
        messageBodyPart.setHeader("Content-ID", "calendar_message");
        messageBodyPart.setDataHandler(new DataHandler(
                new ByteArrayDataSource(builder.toString(), "text/calendar;method=REQUEST;name=\"invite.ics\"")));
 
        MimeMultipart multipart = new MimeMultipart();
 
        multipart.addBodyPart(messageBodyPart);
 
        mimeMessage.setContent(multipart);
 
        System.out.println(builder.toString());
 
        mailSender.send(mimeMessage);
        System.out.println("Calendar invite sent");
 
    }
}
