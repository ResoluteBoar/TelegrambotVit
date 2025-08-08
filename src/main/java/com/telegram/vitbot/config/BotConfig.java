package com.telegram.vitbot.config;



import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class BotConfig {

    public String botName = "VIT13";

    public String token = "8340926563:AAH7-b_OwwQ_yPbOSTYSMptjdiZSWzqjmro";

    public String getBotName() {
        return botName;
    }

    public String getToken() {
        return token;
    }
}
