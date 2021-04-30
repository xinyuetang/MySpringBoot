package com.fudanuniversity.MySpringBoot.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class BulletinSocketController {
    @MessageMapping("/server")
    @SendTo("/topic/bulletin")
    public String notification() {
        System.out.println("revoked here");
        return "Bulletin";
    }
}
