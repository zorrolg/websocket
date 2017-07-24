package net.paiyou.entity.model;

import com.google.common.collect.Lists;
import java.util.List;
import net.paiyou.entity.enums.CardWeight;
import net.paiyou.entity.enums.Color;
import net.paiyou.entity.interfaces.ICard;
import net.paiyou.entity.interfaces.IWeight;

/**
 * Created by 72717 on 2017/6/3.
 */
public class Card implements IWeight, ICard {

    private CardWeight weight;

    private Color color;

    /**
     *
     */
    private byte number;

    @Override
    public byte weight() {
        return weight.weight();
    }

    @Override
    public byte code() {
        return 0;
    }

    public static List<Card> all;

    public static byte pokerGroup = 2;


    public Card(CardWeight order, Color color, int group) {
        byte weightCode = (byte)(order.getCode() & 0x01111000);
        byte colorCode = (byte)(color.weight() & 0x00000110);
        byte last = (byte)(group & 0x00000001);
        this.number = (byte)(weightCode & colorCode & last);

    }
    public static List<Card> all() {
        all = Lists.newArrayListWithCapacity(144);
        for (CardWeight weight : CardWeight.values()) {
            for (Color color : Color.values()) {
                Card card = new Card(weight, color, 0);
                all.add(card);
            }
//            byte min = type.getMin();
//            byte max = type.getMax();
//            for (byte code = min; code <= max;) {
//                Tile tile = new Tile(code, type);
//                int subType = code / 4;
//                // 如果是字花段，0x1F代表已经到花牌
//                if (code <= 0x1F) {
//                    code++;
//                    if (type.getClass() != TileType.ZI_HUA.getClass()) {
//                        NumberRank rank = NumberRank.valueOf(subType);
//                        tile.rank = rank;
//                    } else {
//                        ZiRank rank = ZiRank.valueOf(subType);
//                        tile.rank = rank;
//                    }
//                } else {
//                    code = (byte) (code + 0x4);
//                    HuaRank rank = HuaRank.valueOf(subType);
//                    tile.rank = rank;
//                }
//                all.add(tile);
//            }
        }
        return all;
    }

    public static void main(String[] args) {

    }
}
