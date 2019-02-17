package id.finix.services.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.finix.component.HttpTransport;
import id.finix.domain.ChatMessage;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import id.finix.domain.TelegramMessage;
import id.finix.repositories.ChatMessageRepository;
import id.finix.repositories.ParameterRepository;
import id.finix.services.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ChatService {

    @Autowired
    ChatMessageRepository chatMessageRepository;

    @Autowired
    ParameterRepository parameterRepository;

    @Autowired
    FileService fileService;

    public List<ChatMessage> getChatMessages(String topic) {
        return chatMessageRepository.getChatMessages(topic, new PageRequest(0, 30));
    }

    public ChatMessage sendMessage(ChatMessageRequest request, String userId) {
        ChatMessage message = saveMessage(request.getTopicId(), userId, request.getMessage());
        return sendMessage(message, (request.getTelegram() != null && request.getTelegram().equalsIgnoreCase("true")));
    }

    private ChatMessage sendMessage(ChatMessage message, boolean shouldSendTelegram) {
        boolean success = false;
        if (message != null) {
            if (message.getTopic().equalsIgnoreCase(message.getUserId())) {
                //TelegramMessage tm = sendTelegramMessages(message.getUserId(), message.getMessage());
                //message.setTelegramId(tm.getId());
                message = chatMessageRepository.save(message);
            } else {
                success = sendFcmMessages(message);
                if (shouldSendTelegram) {
                    String val = parameterRepository.findOne("GCM_TITLE").getValue();
                    sendTelegramMessages(message.getUserId(), message.getMessage() + " #" + val.toUpperCase().replaceAll(" ", "_") + " #" + message.getTopic());
                    //if(message.getTopic().equals("global")){
                    //    List<String> chatIds = resellerDao.getResellerChatIds();
                    //    if(chatIds!=null) {
                    //        sendTelegramChannelGlobal(chatIds, message.getMessage());
                    //    }
                    //}
                }
            }
        }
        //message.setError(!success);
        return message;
    }

    public ChatMessage uploadFile(String topicId, String userId, MultipartFile multipartFile) {
        ChatMessage message = saveMessage(topicId, userId, "");
        String path = fileService.upload("chat_attachment", String.valueOf(message.getId()), multipartFile);
        if (path != null) {
            message.setMessage(path);
            message = chatMessageRepository.save(message);
            return sendMessage(message,true);
        }
        return message;
    }

    public ChatMessage saveMessage(String to, String from, String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUserId(from);
        chatMessage.setTopic(to);
        chatMessage.setMessage(message);
        chatMessage.setCreated(new Date());
        chatMessage.setReadStatus(0);
        return chatMessageRepository.save(chatMessage);
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private boolean sendFcmMessages(ChatMessage message) {
        try {
            String key = parameterRepository.findOne("GCM_KEY_API").getValue();
            String title = parameterRepository.findOne("GCM_TITLE").getValue();
            String url = parameterRepository.findOne("GCM_URL_API").getValue();
            //String packageName = parameterDao.getValue("GCM_PACKAGE_NAME");
            String topic = message.getTopic();
            //if (packageName!=null){
            //    topic = packageName+"_"+topic;
            //}

            String dateCreated = sdf.format(message.getCreated());

            Map<String, Object> msg = new LinkedHashMap<>();
            msg.put("message_id", message.getId());
            msg.put("message", message.getMessage());
            msg.put("created_at", dateCreated);

            Map<String, Object> data3 = new LinkedHashMap<>();
            data3.put("user_id", message.getUserId());
            data3.put("message", msg);
            data3.put("chat_room_id", message.getTopic());

            Map<String, Object> data2 = new LinkedHashMap<>();
            data2.put("title", title);
            data2.put("is_background", false);
            data2.put("flag", 1);
            data2.put("data", data3);

            Map<String, Object> data1 = new LinkedHashMap<>();
            data1.put("to", "/topics/" + topic);
            data1.put("data", data2);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(data1);

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "key=" + key);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.getOutputStream().write(json.getBytes("UTF-8"));
            conn.getInputStream();

            //conn.disconnect();

            String resp = conn.getResponseMessage();

            return (resp != null && !resp.toLowerCase().startsWith("error"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public TelegramMessage sendTelegramMessages(String resellerId, String message) {
        String url = parameterRepository.findOne("TELEGRAM_SERVER_URL").getValue();
        Map<String, String> params = new HashMap<>();
        params.put("userId", resellerId);
        params.put("text", message);

        String resp = HttpTransport.submit(url, params);
        ObjectMapper om = new ObjectMapper();
        try {
            TelegramMessage tm = om.readValue(resp, TelegramMessage.class);
            return tm;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String sendTelegramChannelGlobal(List<String> chatIds, String message) {
        String url = parameterRepository.findOne("URL_TELEGRAM_SENDER").getValue();
        Integer failed = 0;
        Integer success = 0;
        for (String chatId : chatIds) {
            Map<String, String> params = new HashMap<>();
            params.put("chatId", chatId);
            params.put("text", message);
            if (HttpTransport.submit(url.replace("?", ""), params).contains("NOK")) failed++;
            else success++;
        }
        return "{\"send_telegram_h2h_global\":{\"success\":" + success + ",\"failed\":" + failed + "}}";
    }
}
