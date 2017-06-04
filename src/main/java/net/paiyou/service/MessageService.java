package net.paiyou.service;

import net.paiyou.dto.Response;
import net.paiyou.entity.model.Player;

public class MessageService {

    public static Response sendMessage(Player client, String message) {
        Response res = new Response();
        res.getData().put("id", client.getId());
        res.getData().put("message", message);
        res.getData().put("ts", System.currentTimeMillis());// 返回毫秒数
        return res;
    }
}
