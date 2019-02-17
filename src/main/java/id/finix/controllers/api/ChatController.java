package id.finix.controllers.api;

import id.finix.Application;
import id.finix.controllers.Response;
import id.finix.domain.ChatMessage;
import id.finix.services.chat.ChatMessageRequest;
import id.finix.services.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(Application.API_PATH + "/chat")
public class ChatController extends BaseController{

    @Autowired
    ChatService chatService;

    @RequestMapping(value = "/fcmRead/{id}", method = RequestMethod.GET)
    public ResponseEntity<Response> getChatMessages(@RequestHeader(Application.AUTH) String token,
                                                    @PathVariable("id") String id) {
        String userId = getUserId(token);
        if (userId==null){
            return FORBIDDEN;
        }
        try {
            List<ChatMessage> list = chatService.getChatMessages(id);
            return getHttpStatus(new Response(list));
        } catch (Exception e) {
            e.printStackTrace();
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/fcmSend", method = RequestMethod.POST)
    public ResponseEntity<Response> sendMessage(@RequestHeader(Application.AUTH) String token,
                                                @RequestBody ChatMessageRequest request) {
        String userId = getUserId(token);
        if (userId==null){
            return FORBIDDEN;
        }
        try {
            ChatMessage message = chatService.sendMessage(request, userId);
            return getHttpStatus(new Response(message));
        } catch (Exception e) {
            e.printStackTrace();
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/fcmUpload/{id}", method = RequestMethod.POST)
    public ResponseEntity<Response> putMessage(@RequestHeader(Application.AUTH) String token,
                                               @PathVariable("id") String id,
                                               @RequestParam("attachment") MultipartFile attachment) {
        String userId = getUserId(token);
        if (userId==null){
            return FORBIDDEN;
        }
        try {
            ChatMessage message = chatService.uploadFile(id, userId, attachment);
            return getHttpStatus(new Response(message));
        } catch (Exception e) {
            e.printStackTrace();
            return getHttpStatus(new Response(e.getMessage()));
        }
    }
}
