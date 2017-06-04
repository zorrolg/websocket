package net.paiyou.entity.model;

public class Player {
	
	/**
	 * 房间id
	 * 大厅roomId为0
	 */
	private int roomId;

	/**
	 * 玩家id
	 */
	private Long playerId;


	
	public boolean isInRoom() {
		if (this.roomId > 0) {
			return true;
		}
		return false;
	}
	
	
	public Player() {
		playerId = 0L;
		roomId = 0;
	}

	public Long getId() {
		return playerId;
	}

	public void setId(Long id) {
		this.playerId = id;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
}
