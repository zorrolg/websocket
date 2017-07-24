package net.paiyou.game;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelId;
import io.netty.channel.group.*;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.ImmediateEventExecutor;
import net.paiyou.service.protocol.Response;
import org.jerry.framework.net.helper.BufferFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelGroupHolder {
	protected static final Logger LOGGER = LoggerFactory.getLogger(ChannelGroupHolder.class);
	private static ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

	public static AttributeKey<Integer> ACTOR_ID = AttributeKey.valueOf("ACTOR_ID");

	public static void push(ChannelId id, Response message) {
		push(id, message, null);
	}

	public static void push(ChannelId id, Response message, ChannelFutureListener channelFutureListener) {
		Channel channel = channelGroup.find(id);
		ChannelFuture f = channel.writeAndFlush(toWebSocketFrame(message));
		f.addListener(logListener(message));
		if (channelFutureListener != null){
			f.addListener(channelFutureListener);
		}

	}

	private static ChannelId findChannelId(int actorId){
		for (Channel channel : channelGroup) {
			if (channel.attr(ACTOR_ID)!=null && channel.attr(ACTOR_ID).get() == actorId){
				return channel.id();
			}
		}
		return null;
	}

	public static void push(int actorId, Response message) {
		ChannelId id = findChannelId(actorId);
		if (id != null) {
			push(id, message);
		}
	}

	public static void broadcast(Response message, ChannelMatcher channelMatcher) {
		ChannelGroupFuture f = channelGroup.writeAndFlush(toWebSocketFrame(message), channelMatcher);
		f.addListener(groupLogListener(message));
	}

    public static void broadcast(Response message) {
		ChannelMatcher channels = ChannelMatchers.all();
		ChannelGroupFuture f = channelGroup.writeAndFlush(toWebSocketFrame(message), channels);
		f.addListener(groupLogListener(message));
	}

	public static BinaryWebSocketFrame toWebSocketFrame(Response response) {
		ByteBuf buffer = BufferFactory.getIoBuffer();
		buffer.writeShort(response.getStatusCode());
		buffer.writeShort(response.getData() == null ? 0 : response.getData().length);
		buffer.writeByte(0);
		buffer.writeInt((int) response.getIndex());
		buffer.writeShort(response.getCmd());
		if (response.getData() != null) {
			buffer.writeBytes(response.getData());
		}
		return new BinaryWebSocketFrame(buffer);
	}

	public static void add(Channel channel) {
		channelGroup.add(channel);
	}

	public static int getActorId(Channel channel) {
		return channel.attr(ACTOR_ID).get();
	}

	public static Channel getChannel(int actorId){
		ChannelId channelId = findChannelId(actorId);
		if (channelId == null){
			return null;
		}
		return channelGroup.find(channelId);

	}

	public static void remove(Channel channel) {
		channelGroup.remove(channel);
	}

    private static ChannelFutureListener logListener(Response message) {
        return new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                int len = 0;
                if (message.getData() != null) {
                    len = message.getData().length;
                }
                LOGGER.debug("message push, cmd:{},length:{}", message.getCmd(), len);
            }
        };
    }

    private static ChannelGroupFutureListener groupLogListener(Response message) {
        return new ChannelGroupFutureListener() {

            @Override
            public void operationComplete(ChannelGroupFuture future) throws Exception {
                int len = 0;
                if (message.getData() != null) {
                    len = message.getData().length;
                }
                LOGGER.debug("message push, cmd:{},length:{}", message.getCmd(), len);
            }
        };
    }
}
