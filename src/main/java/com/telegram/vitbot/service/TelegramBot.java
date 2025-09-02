package com.telegram.vitbot.service;


import com.telegram.vitbot.chatgpt.ChatGPTClient;
import com.telegram.vitbot.config.BotConfig;
import com.telegram.vitbot.fuction.task.Task;
import com.telegram.vitbot.user.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

import static com.telegram.vitbot.secret.SecretKeys.CHAT_GPT_KEY;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public UserService userService = new UserService();
    
    ChatGPTClient gptClient = new ChatGPTClient(CHAT_GPT_KEY);

    public TelegramBot(BotConfig config){ this.config = config;}

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            switch (messageText){
                case "/start":
                    try {
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        break;
                    } catch (TelegramApiException e) {
                        System.out.println("ERROR -> TelegramApi");
                    }
                case "\uD83D\uDCC5":
                    try {
                            TaskFunctionMenu(chatId);
                        break;
                    } catch (TelegramApiException e){
                        System.out.println("ERROR -> TelegramApi");
                    }
                default:
                    try {
                        String response = gptClient.ask(update.getMessage().getText());
                        System.out.println("ChatGPT: " + response);
                        SendMessage message = new SendMessage();
                        message.setChatId(chatId);
                        message.setText(response);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        else if (update.hasCallbackQuery()){
            long chatId = update.getMessage().getChatId();
            try {
                handleCallbackQuery(update.getCallbackQuery(), userService.getUser(chatId), update);
                System.out.println("Start logic");
            } catch (TelegramApiException e) {
                System.out.println("ERROR -> TelegramApi");
            }

        }

    }

    private void startCommandReceived(long chatId, String firstName) throws TelegramApiException{

        String answer;
        if (userService.userMap.get(chatId) == null) {
            answer = "\uD83D\uDE80 VIT13 — твой жесткий и умный ассистент для управления жизнью и успехом.\n" +
                    "Он помогает:\n" +
                    "\uD83D\uDCC5 Планировать день — расставлять приоритеты и не забывать важное.\n" +
                    "\uD83D\uDCAA Следить за здоровьем — вода, сон, тренировки и отдых под контролем.\n" +
                    "\uD83D\uDCB0 Управлять финансами — контроль бюджета, расходы и напоминания по счетам.\n" +
                    "\uD83D\uDCDD Запоминать важное — заметки, идеи, пароли и дела с быстрым доступом.\n" +
                    "\uD83E\uDD16 Анализировать привычки — умные советы и рекомендации на базе ИИ.\n" +
                    "\uD83C\uDFC6 Мотивировать и поддерживать — челленджи, награды и ежедневные напоминания.\n" +
                    "\uD83D\uDCDA Помогать развиваться — подборка курсов, книг и отслеживание прогресса.\n" +
                    "\uD83E\uDD1D Объединять с другими — группы, обмен опытом и поддержка сообщества.\n" +
                    "\uD83E\uDDE0 Персональный коучинг ИИ — адаптация под твой стиль и помощь в дисциплине.";
            User user = new User();
            user.setChatId(chatId);
            user.setFirstName(firstName);

            userService.addUser(chatId,user);
        }
        else{
            answer = "Снова здравствуй, "+ firstName +"! Чем могу быть полезен?";
        }

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(answer);

        List<String> buttonNames1 = Arrays.asList("\uD83D\uDCC5","\uD83D\uDCAA","\uD83D\uDCB0","\uD83D\uDCDD");
        KeyboardRow row1 = new KeyboardRow();
        row1.addAll(buttonNames1);
        List<String> buttonNames2 = Arrays.asList("\uD83E\uDD16","\uD83C\uDFC6","\uD83D\uDCDA","\uD83E\uDD1D","\uD83E\uDDE0");
        KeyboardRow row2 = new KeyboardRow();
        row2.addAll(buttonNames2);
        List<KeyboardRow> keyboardRows = List.of(row1,row2);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(keyboardRows);
        markup.setResizeKeyboard(true);
        message.setReplyMarkup(markup);

        try {
            execute(message);
            System.out.println("Message done to " + firstName);
            System.out.println("Main menu create");
        } catch (TelegramApiException e){
            System.out.println("ERROR -> TelegramApi");
        }

    }

    private void handleCallbackQuery(CallbackQuery callbackQuery, User user, Update update) throws TelegramApiException {
        var data = callbackQuery.getData();
        long chatId = callbackQuery.getFrom().getId();
        switch (data){
            case "add task" -> addTask(user, update);
            case "get task" -> getTask();
            default -> sendMessage(chatId, "Неизвестная команда");
        }
    }

    private void TaskFunctionMenu(long chatId) throws TelegramApiException{
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Добро пожаловать в планировщик дня! Выбери нужную кнопку:");

        var buttonAddTask = InlineKeyboardButton.builder()
                .text("Добавить задачу")
                .callbackData("add task")
                .build();
        var buttonGetTask = InlineKeyboardButton.builder()
                .text("Найти задачу")
                .callbackData("get task")
                .build();

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(buttonAddTask);
        row.add(buttonGetTask);
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        keyboardRows.add(row);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(keyboardRows);
        message.setReplyMarkup(markup);

        try {
            execute(message);
        } catch (TelegramApiException e){
            System.out.println("ERROR -> TelegramApi");
        }

    }

    private void addTask(User user, Update update) throws TelegramApiException{

        Task task = new Task();

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(user.getChatId()));
        message.setText("Напишите имя задачи");
        try {
            execute(message);
        }
        catch (TelegramApiException e){
            System.out.println("ERROR -> TelegramApi");
        }

        task.setTaskName(update.getMessage().getText());
        message.setText("Напишите описание задачи");

        try {
            execute(message);
        }
        catch (TelegramApiException e){
            System.out.println("ERROR -> TelegramApi");
        }

        task.setTaskDescription(update.getMessage().getText());
        message.setText("Напишите дату задачи в формате 01.01.2025");

        try {
            execute(message);
        }
        catch (TelegramApiException e){
            System.out.println("ERROR -> TelegramApi");
        }


        String[] date = update.getMessage().getText().split("\\.");

        Calendar calendar = new GregorianCalendar(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
        user.getUserCalendar().addDay(calendar);
        user.getUserCalendar().getDay(calendar).getTaskService().addTask(task);
    }

    private void getTask(){



    }

    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e){
            System.out.println("ERROR -> TelegramApi");
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken(){;
        return config.getToken();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }
}
