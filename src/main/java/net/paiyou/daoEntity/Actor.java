package net.paiyou.daoEntity;

import com.google.common.collect.Lists;
import java.util.List;
import org.jerry.framework.datacenter.SingleEntity;
import org.jerry.framework.datacenter.annotation.Column;
import org.jerry.framework.datacenter.annotation.DBQueueType;
import org.jerry.framework.datacenter.annotation.Table;
import org.jerry.framework.util.IdentiyKey;

@Table(name = "actor", type = DBQueueType.IMPORTANT)
public class Actor extends SingleEntity {

    @Column(pk = true)
    private Integer actorId;
    /**
     * 渠道id
     */
    @Column
    private String channel;
    @Column
    private long gold;
    @Column
    private String nickname = EMPTY_STRING;
    @Column
    private String icon = EMPTY_STRING;

    @Override
    public List<IdentiyKey> keyLists() {
        return Lists.newArrayList(findPkId());
    }

    @Override
    public IdentiyKey findPkId() {
        return IdentiyKey.build(actorId);
    }

    @Override
    public void setPkId(IdentiyKey identiyKey) {
        this.actorId = identiyKey.getFirstIntId();
    }

    @Override
    protected void hasReadEvent() {

    }

    @Override
    protected void beforeWritingEvent() {

    }

    @Override
    protected void disposeBlob() {

    }
}
