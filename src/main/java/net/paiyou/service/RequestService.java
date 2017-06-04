package net.paiyou.service;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;
import com.alibaba.fastjson.JSONObject;

import net.paiyou.entity.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RequestService {

    private static Logger LOGGER = LoggerFactory.getLogger(RequestService.class);
    /**
     * 根据客户端的请求生成 Player
     *
     * @param request 例如 {id:1;rid:21;token:'43606811c7305ccc6abb2be116579bfd'}
     * @return
     */
    public static Player clientRegister(String request) {
        String res = new String(Base64.decodeBase64(request));
        LOGGER.info(res);
        JSONObject json = JSON.parseObject(res);
        Player client = new Player();

        if (!json.containsKey("rid")) {
            return client;
        }

        try {
            client.setRoomId(json.getIntValue("rid"));
        } catch (Exception e) {
            e.printStackTrace();
            return client;
        }

        if (!json.containsKey("id") || !json.containsKey("token")) {
            return client;
        }

        Long id;
        String token;

        try {
            id = json.getLong("id");
            token = json.getString("token");
        } catch (Exception e) {
            e.printStackTrace();
            return client;
        }

        if (!checkToken(id, token)) {
            return client;
        }

        client.setId(id);

        return client;
    }

    /**
     * 从 redis 里根据 id 获取 token 与之对比
     *
     * @param id
     * @param token
     * @return
     */
    private static boolean checkToken(Long id, String token) {
        return true;
    }
}
