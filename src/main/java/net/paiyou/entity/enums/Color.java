package net.paiyou.entity.enums;

import java.util.Arrays;
import net.paiyou.entity.interfaces.IWeight;

/**
 * Created by 72717 on 2017/6/3.
 */
public enum Color implements IWeight{
    /**
     * 黑桃
     */
    SPADE,
    /**
     * 红桃
     */
    HEART,
    /**
     * 梅花
     */
    CLUB,
    /**
     * 方块
     */
    DIAMOND;

    @Override
    public byte weight() {
        return (byte)this.ordinal();
    }


    public static void  main(String[] args) {
        Color[] values = values();
        for (Color color : values) {
            System.out.println(color.weight());
        }
        System.out.println(Arrays.asList(values));
    }

}
