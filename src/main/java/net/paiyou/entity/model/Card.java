package net.paiyou.entity.model;

import net.paiyou.entity.enums.CardWeight;
import net.paiyou.entity.enums.Color;
import net.paiyou.entity.interfaces.IWeight;
import net.paiyou.entity.interfaces.ICard;

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
}
