package net.paiyou.strategy;


import net.paiyou.action.Action;
import net.paiyou.action.ActionType;
import net.paiyou.entity.enums.PlayerLocation;
import net.paiyou.entity.enums.PlayerLocation.Relation;
import net.paiyou.entity.model.Card;
import net.paiyou.entity.model.PokerTable;
import net.paiyou.game.ActionAndLocation;
import net.paiyou.game.GameContext;

import java.util.*;
import java.util.stream.Stream;

/**
 * 基本的常用的规则。继承此类，实现抽象方法，可以实现一种特定的游戏规则，包括坐庄规则、和牌限制、和牌类型、番种定义等。
 */
public abstract class AbstractGameStrategy implements GameStrategy {

	/**
	 * {@inheritDoc}<br>
	 * 桌上所有位置都有玩家就返回true。
	 */
	@Override
	public boolean checkReady(PokerTable table) {
		return Stream.of(PlayerLocation.values())
				.map(table::getPlayerByLocation).allMatch(Objects::nonNull);
	}

	@Override
	public List<Card> getAllTiles() {
		return Card.all();
	}

	/**
	 * {@inheritDoc}<br>
	 * 在一局开始之前设置庄家位置。
	 */
	@Override
	public void readyContext(GameContext context) {
		context.setZhuangLocation(nextZhuangLocation(context));
	}

	/**
	 * 在一局开始之前，根据context返回此局的庄家位置。
	 */
	protected abstract PlayerLocation nextZhuangLocation(GameContext context);

	@Override
	public Action getDealAction(GameContext context) {
		return new Action(DEAL);
	}

	private static final Set<ActionType> ALL_ACTION_TYPES = new HashSet<ActionType>(
			Arrays.asList(StandardActionType.values()));

	@Override
	public Set<? extends ActionType> getAllActionTypesInGame() {
		return ALL_ACTION_TYPES;
	}

	@Override
	public Set<? extends ActionType> getAllActionTypesInTing() {
		return EnumSet.of(DRAW, DISCARD, BUHUA, DRAW_BOTTOM, WIN);
	}

	/**
	 * 动作类型优先级倒序，先低后高，不在列表里的为最低。<br>
	 * 进行比较时反过来用index比较，不在列表里的为-1。
	 */
	private static final List<ActionType> ACTION_TYPE_PRIORITY_LIST = Arrays
			.asList(CHI, PENG, ZHIGANG, WIN);

	/**
	 * 和>杠>碰>吃>其他，相同的比较与上次动作的玩家位置关系。
	 */
	@Override
	public Comparator<ActionTypeAndLocation> getActionPriorityComparator() {
		Comparator<ActionTypeAndLocation> c = Comparator.comparing(
				atl -> ACTION_TYPE_PRIORITY_LIST.indexOf(atl.getActionType()));
		c = c.reversed();
		c = c.thenComparing(a -> {
			PlayerLocation lastLocation = a.getContext()
					.getLastActionLocation();
			return lastLocation == null ? PlayerLocation.Relation.SELF
					: lastLocation.getRelationOf(a.getLocation());
		});
		return c;
	}

	@Override
	public Action getPlayerDefaultAction(GameContext context,
			PlayerLocation location, Set<ActionType> choises) {
		if (choises.contains(DRAW))
			return new Action(DRAW);
		if (choises.contains(DRAW_BOTTOM))
			return new Action(DRAW_BOTTOM);
		if (choises.contains(DISCARD)) {
			Tile tileToDiscard;
			Action lastAction = context.getLastAction();
			if (context.getLastActionLocation() == location
					&& DRAW.matchBy(lastAction.getType())) {
				tileToDiscard = lastAction.getTile();
			} else {
				tileToDiscard = context.getPlayerInfoByLocation(location)
						.getAliveTiles().iterator().next();
			}
			return new Action(DISCARD, tileToDiscard);
		}
		return null;
	}

	@Override
	public ActionAndLocation getDefaultAction(GameContext context,
											  Map<PlayerLocation, Set<ActionType>> choises) {
		if (context.getTable().getTileWallSize() == 0)
			return new ActionAndLocation(new Action(LIUJU), null);
		else
			return null;
	}

	@Override
	public boolean tryEndGame(GameContext context) {
		return context.getGameResult() != null;
	}

}
