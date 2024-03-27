package com.first.demo.hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import jakarta.mail.internet.MimeMessage;

@Component
public class OutlookInviteSender {

    private final JavaMailSender mailSender;

    @Autowired
    public OutlookInviteSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOutlookInvite() throws Exception {
        MimeMessage message = mailSender.createMimeMessage();

        // Set the necessary properties of the message, including recipients, subject, and content

        // Example code to set properties:
        // message.setFrom("sender_email_address");
        // message.setRecipients(Message.RecipientType.TO, "recipient_email_address");
        // message.setSubject("Meeting Invitation");
        // message.setText("Please join us for a meeting.");

        // Set the content type to "text/calendar" to create an Outlook invite
        message.setHeader("Content-Class", "urn:content-classes:calendarmessage");
        message.setHeader("Content-Type", "text/calendar; method=REQUEST");

        // Set the invite details, such as the start time, end time, location, and attendees

        // Example code to set invite details:
        // message.setContent("BEGIN:VCALENDAR\n" +
        //        "PRODID:-//Microsoft Corporation//Outlook 10.0 MIMEDIR//EN\n" +
        //        "VERSION:2.0\n" +
        //        "METHOD:REQUEST\n" +
        //        "BEGIN:VEVENT\n" +
        //        "DTSTART:20230517T090000Z\n" +
        //        "DTEND:20230517T100000Z\n" +
        //        "LOCATION:Conference Room\n" +
        //        "UID:040000008200E00074C5B7101A82E00800000000A0E92DCEC536D701000000000000000\n" +
        //        " 010000000ACD7C2D6476C2D4BB3F698FF97D2\n" +
        //        "DTSTAMP:20230516T120102Z\n" +
        //        "DESCRIPTION:Please join us for a meeting.\n" +
        //        "SUMMARY:Meeting Invitation\n" +
        //        "PRIORITY:5\n" +
        //        "CLASS:PUBLIC\n" +
        //        "ORGANIZER:mailto:sender_email_address\n" +
        //        "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:mailto:recipient_email_address\n" +
        //        "END:VEVENT\n" +
        //        "END:VCALENDAR");

        mailSender.send(message);
    }
}
