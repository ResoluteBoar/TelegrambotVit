package com.telegram.vitbot.config;



import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static com.telegram.vitbot.secret.SecretKeys.TELEGRAM_BOT_KEY;

@Configuration
@PropertySource("application.properties")
public class BotConfig {

    public String botName = "VIT13";

    public String token = TELEGRAM_BOT_KEY;

    public String getBotName() {
        return botName;
    }

    public String getToken() {
        return token;
    }
}
