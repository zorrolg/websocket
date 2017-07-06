package net.paiyou.entity.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 桌上一个玩家的信息，包括玩家对象、牌，以及其他信息。
 */
public class PlayerInfo implements Cloneable {
	/**
	 * 玩家。
	 */
	private Player player = null;

	/**
	 * 手中的牌。
	 */
	protected Set<Card> aliveCards = new HashSet<>();
	/**
	 * 最后打出的牌。
	 */
	private Set<Card> lastDrawedCard = null;
	/**
	 * 已经打出的牌。
	 */
	private List<Card> discardedCards = new ArrayList<>();
	/**
	 * 是否出完手中的牌。
	 */
	private boolean isWin = false;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Set<Card> getAliveCards() {
		return aliveCards;
	}

	public Set<Card> getLastDrawedCard() {
		return lastDrawedCard;
	}

	public void setLastDrawedCard(Set<Card> lastDrawedCard) {
		this.lastDrawedCard = lastDrawedCard;
	}

	public List<Card> getDiscardedCards() {
		return discardedCards;
	}

	public void setDiscardedCards(List<Card> discardedCards) {
		this.discardedCards = discardedCards;
	}

	public boolean isWin() {
		return isWin;
	}

	public void setWin(boolean isTing) {
		this.isWin = isTing;
	}

	/**
	 * 清空玩家的牌，回到初始状态。
	 */
	public void clear() {
		aliveCards.clear();
		lastDrawedCard = null;
		discardedCards.clear();
		isWin = false;
	}

	public PlayerInfo clone() {
		PlayerInfo c;
		try {
			c = (PlayerInfo) super.clone();
		} catch (CloneNotSupportedException e) {
			// 不可能，因为PlayerInfo已经实现了Cloneable
			throw new RuntimeException(e);
		}
		// deep copy
		c.aliveCards = new HashSet<>(aliveCards);
		c.discardedCards = new ArrayList<>(discardedCards);
		return c;
	}

	private PlayerView otherPlayerView;

	/**
	 * 获取其他玩家的视图。
	 */
	public PlayerView getOtherPlayerView() {
		if (otherPlayerView == null) { // 不需要加锁，因为多创建了也没事
			otherPlayerView = new PlayerView();
		}
		return otherPlayerView;
	}

	/**
	 * 一个位置的玩家的视图。需要限制一些权限。
	 */
	public class PlayerView {

		/**
		 * 返回玩家名称。
		 */
		public String getPlayerName() {
			Player player = getPlayer();
			return player != null ? getPlayer().getId() + "" : null;
		}

		/**
		 * 返回手中的牌数。
		 */
		public int getAliveTileSize() {
			return getAliveCards().size();
		}

		public List<Card> getDiscardedTiles() {
			return discardedCards;
		}

		public boolean isWin() {
			return isWin;
		}

	}

}
