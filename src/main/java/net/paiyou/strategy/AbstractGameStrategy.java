package net.paiyou.strategy;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.paiyou.action.Action;
import net.paiyou.action.ActionType;
import net.paiyou.entity.enums.PlayerLocation;
import net.paiyou.entity.model.Card;
import net.paiyou.entity.model.PokerTable;
import net.paiyou.game.ActionAndLocation;
import net.paiyou.game.ActionTypeAndLocation;
import net.paiyou.game.GameContext;

public class AbstractGameStrategy implements GameStrategy {

    @Override
    public boolean checkReady(PokerTable table) {
        return false;
    }

    @Override
    public List<Card> getAllTiles() {
        return null;
    }

    @Override
    public void readyContext(GameContext context) {

    }

    @Override
    public Action getDealAction(GameContext context) {
        return null;
    }

    @Override
    public Set<? extends ActionType> getAllActionTypesInGame() {
        return null;
    }

    @Override
    public Set<? extends ActionType> getAllActionTypesInTing() {
        return null;
    }

    @Override
    public Comparator<ActionTypeAndLocation> getActionPriorityComparator() {
        return null;
    }

    @Override
    public Action getPlayerDefaultAction(GameContext context, PlayerLocation location, Set<ActionType> choises) {
        return null;
    }

    @Override
    public ActionAndLocation getDefaultAction(GameContext context, Map<PlayerLocation, Set<ActionType>> choises) {
        return null;
    }

    @Override
    public boolean tryEndGame(GameContext context) {
        return false;
    }
}
