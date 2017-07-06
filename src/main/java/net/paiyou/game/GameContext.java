package net.paiyou.game;

import net.paiyou.entity.enums.PlayerLocation;
import net.paiyou.entity.model.PokerTable;
import net.paiyou.strategy.GameStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017/6/16.
 */
public class GameContext {
    private static final Logger logger = LoggerFactory.getLogger(GameContext.class);

    private PokerTable table;

    private GameStrategy gameStrategy;

    private PlayerLocation zhuang;

    public PokerTable getTable() {
        return table;
    }


    public PlayerLocation getZhuangLocation() {
        return zhuang;
    }

    public void setZhuangLocation(PlayerLocation zhuang) {
        this.zhuang = zhuang;
    }
}
