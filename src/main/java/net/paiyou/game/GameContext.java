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

    public PokerTable getTable() {
        return table;
    }

    public PlayerLocation getZhuangLocation() {
        return PlayerLocation.EAST;
    }

    public void setZhuangLocation(PlayerLocation zhuang) {

    }
}
