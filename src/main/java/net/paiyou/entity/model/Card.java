package net.paiyou.entity.model;

import net.paiyou.entity.interfaces.IWeight;
import net.paiyou.entity.interfaces.ICard;

/**
 * Created by 72717 on 2017/6/3.
 */
public class Card implements IWeight, ICard {

    @Override
    public byte weight() {
        return 0;
    }

    @Override
    public byte code() {
        return 0;
    }
}
