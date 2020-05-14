package com.udelblue.services;

import org.springframework.web.bind.annotation.RequestBody;

public interface MailService {

    Object sendMail(@RequestBody String to, String link);
}