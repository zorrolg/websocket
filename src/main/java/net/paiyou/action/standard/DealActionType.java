package net.paiyou.action.standard;

import net.paiyou.action.Action;
import net.paiyou.action.ActionType;
import net.paiyou.action.exception.IllegalActionException;
import net.paiyou.entity.enums.PlayerLocation;
import net.paiyou.entity.enums.PlayerLocation.Relation;
import net.paiyou.entity.model.Card;
import net.paiyou.entity.model.PlayerInfo;
import net.paiyou.entity.model.PokerTable;
import net.paiyou.game.GameContext;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;


/**
 * 动作类型“发牌”。<br>
 * 发牌动作不由玩家执行，只实现doAction方法。
 */
public class DealActionType implements ActionType {

    @Override
    public boolean canDo(GameContext context, PlayerLocation location) {
        // 不作为常规动作
        return false;
    }

    @Override
    public boolean canPass(GameContext context, PlayerLocation location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Set<Card>> getLegalActionTiles(GameContext context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isLegalAction(GameContext context, PlayerLocation location, Action action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void doAction(GameContext context, PlayerLocation location, Action action) throws IllegalActionException {
        PokerTable table = context.getTable();
        PlayerLocation zhuang = context.getZhuangLocation();
        for (int i = 0; i < 4; i++) {
            int drawCount = i < 3 ? 4 : 1;
            Stream.of(Relation.values()).map(zhuang::getLocationOf)
                    .map(context::getPlayerInfoByLocation)
                    .map(PlayerInfo::getAliveTiles)
                    .forEach(aliveTiles -> aliveTiles
                            .addAll(table.draw(drawCount)));
        }
        context.getPlayerInfoByLocation(zhuang).getAliveTiles()
                .addAll(table.draw(1));
    }

}
