package net.paiyou.service;

import com.google.common.util.concurrent.AbstractIdleService;
import java.util.Map;
import net.paiyou.entity.model.Player;
import net.paiyou.service.protocol.Response;
import net.paiyou.service.router.WebSocketRouter;
import org.jerry.framework.util.StaticSpringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
public class MessageService extends AbstractIdleService {

    public static Response sendMessage(Player client, String message) {
//        Response res = new Response();
//        res.getData().put("id", client.getId());
//        res.getData().put("message", message);
//        res.getData().put("ts", System.currentTimeMillis());// 返回毫秒数
//        return res;
        return null;
    }


    @Override
    protected void startUp() throws Exception {

        WebSocketRouter webSocketRouter = StaticSpringUtils.getApplicationContext().getBean(WebSocketRouter.class);
        Map<String, Object> controllers = StaticSpringUtils.getApplicationContext().getBeansWithAnnotation(Controller.class);
        for (Object routerHandler : controllers.values()) {
            webSocketRouter.register(routerHandler);
        }
    }

    @Override
    protected void shutDown() throws Exception {

    }
}
