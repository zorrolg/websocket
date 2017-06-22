package net.paiyou.game;

import net.paiyou.entity.model.PokerTable;
import net.paiyou.strategy.GameStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017/6/16.
 */
public class GameContext {
    private static final Logger logger = LoggerFactory.getLogger(GameContext.class);

    private PokerTable tabel;

    private GameStrategy gameStrategy;

    private TimeLimitStrategy timeLimitStrategy;
}
