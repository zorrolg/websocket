package net.paiyou.entity.interfaces;

import net.paiyou.entity.model.Player;

public interface IMahjongRoom {
	
	/**
	 * 验证房间号是否存在或者正确
	 * @param player
	 * @param key
	 * @return
	 */
	boolean authenticationRoomKey(Player player, int key);
	/**
	 * 用户进入或者创建房间
	 * @param newComer
	 * @return
	 */
	boolean enterRoom(Player newComer);
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	boolean exitRoom(Player player);
}
