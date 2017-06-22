package net.paiyou.entity.model;

import net.paiyou.entity.enums.PlayerLocation;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 麻将桌。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class PokerTable {
	/**
	 * 牌墙。从0处摸牌。一局开始时如果产生底牌，应该把底牌从开头挪到尾部。
	 */
	private List<Card> allCards;
	/**
	 * 一局开始时的底牌数量。
	 */
    public int initBottomSize;
	/**
	 * 已经从底部摸牌的数量。
	 */
    public int drawedBottomSize;
	/**
	 * 所有玩家信息。
	 */
	private Map<PlayerLocation, PlayerInfo> playerInfos;

	public void init() {
		allCards = new ArrayList<Card>();
		playerInfos = new EnumMap<>(PlayerLocation.class);
		for (PlayerLocation location : PlayerLocation.values()) {
			playerInfos.put(location, new PlayerInfo());
		}
	}

	/**
	 * 初始化，准备开始一局。即清空玩家的牌、洗牌、摆牌墙。
	 */
	public void readyForGame(Collection<Card> allTiles) {
		playerInfos.values().forEach(PlayerInfo::clear);
		allCards.clear();
		allCards.addAll(allTiles);
		Collections.shuffle(allCards);
		initBottomSize = 0;
		drawedBottomSize = 0;
	}

	/**
	 * 返回牌墙中的剩余牌数。
	 */
	public int getTileWallSize() {
		return allCards.size();
	}


	/**
	 * 从牌墙的头部摸指定数量的牌并返回。
	 */
	public List<Card> draw(int count) {
		if (count <= 0 || count > allCards.size())
			return Collections.emptyList();
		List<Card> toBeDrawed = allCards.subList(0, count);
		List<Card> drawed = new ArrayList<>(toBeDrawed);
		toBeDrawed.clear();
		return drawed;
	}

	/**
	 * 从牌墙的底部摸指定数量的牌并返回。
	 */
	public List<Card> drawBottom(int count) {
		if (count <= 0 || count > allCards.size())
			return Collections.emptyList();
		List<Card> toBeDrawed = allCards.subList(allCards.size() - count,
				allCards.size());
		List<Card> drawed = new ArrayList<>(toBeDrawed);
		toBeDrawed.clear();
		drawedBottomSize += drawed.size();
		return drawed;
	}

	public Map<PlayerLocation, PlayerInfo> getPlayerInfos() {
		return playerInfos;
	}

	protected void setPlayerInfos(Map<PlayerLocation, PlayerInfo> playerInfos) {
		this.playerInfos = playerInfos;
	}

	public Player getPlayerByLocation(PlayerLocation location) {
		PlayerInfo info = playerInfos.get(location);
		return info == null ? null : info.getPlayer();
	}

	public void setPlayer(PlayerLocation location, Player player) {
		PlayerInfo playerInfo = playerInfos.get(location);
		if (playerInfo == null) {
			playerInfo = new PlayerInfo();
			playerInfos.put(location, playerInfo);
		}
		playerInfo.setPlayer(player);
	}

	private final Map<PlayerLocation, PlayerView> playerViews = new HashMap<>();

	/**
	 * 获取指定位置的玩家视图。
	 */
	public PlayerView getPlayerView(PlayerLocation location) {
		PlayerView view = playerViews.get(location);
		if (view == null) { // 不需要加锁，因为多创建了也没事
			view = new PlayerView(location);
			playerViews.put(location, view);
		}
		return view;
	}

	/**
	 * 一个位置的玩家的视图。需要限制一些权限。
	 * 
	 * @author blovemaple <blovemaple2010(at)gmail.com>
	 */
	public class PlayerView {

		private final PlayerLocation myLocation;

		private PlayerView(PlayerLocation myLocation) {
			this.myLocation = myLocation;
		}

		/**
		 * 返回此视图的玩家位置。
		 */
		public PlayerLocation getMyLocation() {
			return myLocation;
		}

		/**
		 * 返回指定位置的玩家名称。
		 */
		public String getPlayerName(PlayerLocation location) {
			Player player = getPlayerByLocation(location);
			return player != null ? player.getId() + "" : null;
		}

		/**
		 * 返回牌墙中的剩余牌数。
		 */
		public int getTileWallSize() {
			return PokerTable.this.getTileWallSize();
		}

		/**
		 * 返回此局开始时的底牌数量。
		 */
		public int getInitBottomSize() {
			return PokerTable.this.getInitBottomSize();
		}

		/**
		 * 返回已经从底部摸牌的数量。
		 */
		public int getDrawedBottomSize() {
			return PokerTable.this.drawedBottomSize;
		}

		/**
		 * 返回所有玩家已经打出的牌。
		 */
		public Map<PlayerLocation, PlayerInfo.PlayerView> getPlayerInfoView() {
			return playerInfos.entrySet().stream()
					.collect(Collectors.toMap(entry -> entry.getKey()
							, entry -> entry.getValue().getOtherPlayerView()));
		}

	}

}
