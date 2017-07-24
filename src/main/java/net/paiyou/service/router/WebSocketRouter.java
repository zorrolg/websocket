package net.paiyou.service.router;

import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.common.util.concurrent.AbstractIdleService;
import com.google.protobuf.Parser;
import io.netty.channel.Channel;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import net.paiyou.proto.User;
import net.paiyou.service.protocol.Request;
import org.jerry.framework.net.router.Router;
import org.jerry.framework.net.router.annotation.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mx on 2017/6/2.
 */
public abstract class WebSocketRouter extends AbstractIdleService implements Router<Request> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WebSocketRouter.class);
    protected Map<Integer, Method> mathodMap = new HashMap<>();
    protected Map<Integer, Object> handlerMap = new HashMap<>();
    protected Map<Integer, Boolean> checkMap = new HashMap<>();

    private Map<Integer,Parser<?>> messageMap = new HashMap<>();

    public void register(Object handler) {
        if (handler != null) {
            Method[] methods = handler.getClass().getMethods();
            for (Method m : methods) {
                Action c = m.getAnnotation(Action.class);
                if (c == null) continue;
                if (mathodMap.containsKey(c.id())) {
                    throw new RuntimeException(String.format("cmd:[%d] duplicated key", c.id()));
                }
                mathodMap.put(c.id(), m);
                checkMap.put(c.id(), c.check());
                handlerMap.put(c.id(), handler);
            }

        }
    }

    /**
     * 路由转发
     */
    public abstract Object forward(Channel channel, Request request);

    @Override
    protected void startUp() throws Exception {
        registMessage(1000, User.c2s_login_1000.parser());
        registMessage(1001, User.c2s_relogin_1001.parser());
    }

    @Override
    protected void shutDown() throws Exception {

    }

    public void registMessage(Integer key, Parser<?> value){
        if (messageMap.containsKey(key)){
            throw new RuntimeException("duplicate message key:" + key);
        }
        messageMap.put(key, value);
    }

    public Parser<?> findMessage(Integer key){
        return messageMap.get(key);
    }
}
