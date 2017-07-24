package net.paiyou.service.router;

import com.google.protobuf.Parser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import java.lang.reflect.Method;
import net.paiyou.dto.StatusCode;
import net.paiyou.game.ChannelGroupHolder;
import net.paiyou.service.protocol.Request;
import net.paiyou.service.protocol.Response;
import org.springframework.stereotype.Component;

@Component
public class WebSocketRouterImpl extends WebSocketRouter {

	@Override
	public Object forward(Channel channel, Request dataPacket) {
		int cmd = dataPacket.getCmd();
		LOGGER.debug("message recive, cmd:{},length:{}", cmd,dataPacket.getData() == null ? 0 :dataPacket.getData().length);
		Object handler = handlerMap.get(cmd);
		Method method = mathodMap.get(cmd);
		final Response response = new Response(dataPacket.getIndex(),dataPacket.getCmd(),null,(short)0);
		BinaryWebSocketFrame binaryWebSocketFrame = null;
		if (handler == null || method == null) {
			response.setStatusCode(StatusCode.OPERTATION_ERROR);
			binaryWebSocketFrame =	ChannelGroupHolder.toWebSocketFrame(response);
			channel.write(binaryWebSocketFrame);
			return null;
		}

        int actorId = channel.attr(ChannelGroupHolder.ACTOR_ID).get();
        if (checkMap.get(cmd)){
			if (actorId == 0){
				response.setStatusCode(StatusCode.OPERTATION_ERROR);
				binaryWebSocketFrame =	ChannelGroupHolder.toWebSocketFrame(response);
				channel.write(binaryWebSocketFrame);
				return null;
			}
		}

		try {
			Parser<?> parser =  findMessage(cmd);
			Object result = null;
			if (parser == null){
				result = method.invoke(handler,actorId, channel, dataPacket.getData(), response);
			} else {
				Object message = parser.parseFrom(dataPacket.getData());
				result = method.invoke(handler,actorId, channel, message, response);
			}

			binaryWebSocketFrame = 	ChannelGroupHolder.toWebSocketFrame(response);
			ChannelFuture f = channel.writeAndFlush(binaryWebSocketFrame);
			f.addListener(logFuture(response));
			ChannelFutureListener future = null;

			if (result instanceof ChannelFutureListener){
				future = (ChannelFutureListener) result;
			}
			if (future != null) {
				f.addListener(future);
			}

		} catch (Exception exception) {
			LOGGER.error("{}",exception);
		}
		return null;
	}

    private ChannelFutureListener logFuture(Response response) {
        return new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                int len = 0;
                if (response.getData() != null){
                    len = response.getData().length;
                }
                LOGGER.debug("message send, cmd:{},length:{}", response.getCmd(),len);

            }
        };
    }

}
