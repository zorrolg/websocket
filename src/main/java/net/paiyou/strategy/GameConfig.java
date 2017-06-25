package net.paiyou.strategy;

/**
 * Created by gang on 2017/6/23.
 */
public class GameConfig {

    public enum GameCost {
        SIX_TWO(6,2),
        THREE_ONE(3,1);

        /**一场游戏对应局数消耗的房卡*/
        private byte costRoomCard;
        /**一场游戏有几局*/
        private byte round;

        GameCost(Integer round, Integer cost) {
            this.costRoomCard = cost.byteValue();
            this.round = round.byteValue();
        }
    }

    public GameCost gameCost;
    /**
     * 癞子当几
     */
    public byte laiCardCode = 0;

    public byte jokerNum = 0;

    public boolean create4Other = false;

    public GameConfig(int cost, int laiCardCode, int jokerNum, boolean create4Other) {
        if (cost == 1) {
            gameCost = GameCost.THREE_ONE;
        } else {
            gameCost = GameCost.SIX_TWO;
        }
        this.laiCardCode = (byte)laiCardCode;
        this.jokerNum = (byte)jokerNum;
        this.create4Other = create4Other;
    }
}
