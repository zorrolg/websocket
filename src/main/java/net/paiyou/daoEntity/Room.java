package net.paiyou.daoEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jerry.framework.datacenter.annotation.Column;
import org.jerry.framework.datacenter.annotation.DBQueueType;
import org.jerry.framework.datacenter.annotation.Table;

@Table(name = "room", type = DBQueueType.IMPORTANT)
public class Room {

    private static Map<Integer, Actor> atomic = new ConcurrentHashMap<>();

    @Column(pk = true)
    private Integer actorId;
    /**
     * 渠道id
     */
    @Column
    private Integer creater;



}
