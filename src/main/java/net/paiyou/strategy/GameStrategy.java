package net.paiyou.strategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.paiyou.action.Action;
import net.paiyou.action.ActionType;
import net.paiyou.entity.model.Card;
import net.paiyou.entity.model.PokerTable;
import net.paiyou.game.GameContext;


/**
 * 游戏策略。即一种游戏规则的定义。
 *
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public interface GameStrategy {

    /**
     * 检查一个扑克桌是否符合条件开始进行一局。
     *
     * @param table
     *            麻将桌
     * @return 如果可以开始，返回true，否则返回false。
     */
    public boolean checkReady(PokerTable table);

    /**
     * 获取全部扑克牌的列表。
     */
    public List<Card> getAllTiles();

    /**
     * 在一局开始之前对上下文进行必要操作。
     */
    public void readyContext(GameContext context);

    /**
     * 根据当前状态获取开局的发牌动作，用于发牌。
     */
    public Action getDealAction(GameContext context);

    /**
     * 返回游戏进行中（发牌后，直到结束）所有动作类型列表。
     */
    public Set<? extends ActionType> getAllActionTypesInGame();

    /**
     * 返回听牌状态中（听牌后，直到结束，听牌的玩家）所有动作类型列表。
     */
    public Set<? extends ActionType> getAllActionTypesInTing();

    /**
     * 获取动作优先级比较器。优先级越高的越小。
     */
    public Comparator<ActionTypeAndLocation> getActionPriorityComparator();

    /**
     * 根据当前状态返回指定玩家超时默认做的动作。
     *
     * @return 默认动作，null表示不做动作
     */
    public Action getPlayerDefaultAction(GameContext context, PlayerLocation location, Set<ActionType> choises);

    /**
     * 根据当前状态返回默认动作。默认动作是所有玩家都没有可选动作或均选择不做动作之后自动执行的动作。
     *
     * @return 默认动作
     */
    public ActionAndLocation getDefaultAction(GameContext context, Map<PlayerLocation, Set<ActionType>> choises);

    /**
     * 获取此策略支持的所有和牌类型。
     */
    public List<? extends WinType> getAllWinTypes();

    /**
     * 判断指定条件下是否可和牌。<br>
     * 默认实现为使用此策略支持的所有和牌类型进行判断，至少有一种和牌类型判断可以和牌则可以和牌。
     */
    public default boolean canWin(WinInfo winInfo) {
        return getAllWinTypes().stream().anyMatch(winType -> winType.match(winInfo));
        // TODO 缓存
    }

    /**
     * 获取此策略支持的所有番种。返回的列表中，被覆盖的番种必须在覆盖它的番种之后。不允许两个番种相互覆盖。
     */
    public List<FanType> getAllFanTypes();

    /**
     * 检查和牌的所有番种和番数。<br>
     * 默认实现为使用此策略支持的所有番种和番数进行统计。
     */
    public default Map<FanType, Integer> getFans(WinInfo winInfo) {
        // 先parse和牌units
        getAllWinTypes().forEach(winType -> winType.parseWinTileUnits(winInfo));
        // 如果没parse出来，说明不和牌，直接返回空map
        if (winInfo.getUnits() == null || winInfo.getUnits().isEmpty())
            return Collections.emptyMap();

        // 算番
        Map<FanType, Integer> attachedFanTypes = new HashMap<>();
        LinkedList<FanType> uncheckedFanTypes = new LinkedList<>(getAllFanTypes());
        Map<FanType, Integer> fans = new HashMap<>();

        FanType crtFanType;
        while ((crtFanType = uncheckedFanTypes.pollFirst()) != null) {
            Integer matchCount;

            matchCount = attachedFanTypes.get(crtFanType);
            if (matchCount == null)
                matchCount = crtFanType.match(winInfo);

            if (matchCount > 0) {
                fans.put(crtFanType, crtFanType.score() * matchCount);
                attachedFanTypes.put(crtFanType, matchCount);
                Set<? extends FanType> covered = crtFanType.covered();
                if (covered != null && !covered.isEmpty())
                    uncheckedFanTypes.removeAll(covered);
            }
        }

        return fans;
    }

    /**
     * 根据当前状态判断游戏是否结束。
     */
    public boolean tryEndGame(GameContext context);
}