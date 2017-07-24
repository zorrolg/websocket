package net.paiyou.controller;

import io.netty.channel.Channel;
import org.jerry.framework.net.protocol.Response;
import org.jerry.framework.net.router.annotation.Action;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Action(id = 1000)
    public void m8001(int actorId, Channel channel, Object data, Response response) {

    }
}
