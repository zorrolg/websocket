package net.paiyou.entity.enums;

import java.util.Arrays;

import net.paiyou.entity.interfaces.IWeight;

/**
 * Created by 72717 on 2017/6/3.
 */
public enum CardWeight implements IWeight {

    $3(2),
    $4(3),
    $5(4),
    $6(5),
    $7(6),
    $8(7),
    $9(8),
    $10(9),
    J(10),
    Q(11),
    K(12),
    A(13),
    $2(1),
    JOKER(14),
    $JOKER(15),;

    private byte $ode;

    private CardWeight(int input) {
        this.$ode = (byte) input;
    }

    @Override
    public byte weight() {
        return (byte) this.ordinal();
    }

    @Override
    public String toString() {
        return this.name();
    }

    public static void main(String[] args) {
        System.out.println($2.weight());
        System.out.println($5.weight());
        System.out.println($2.compareTo($5));
        System.out.println(Arrays.asList(values()));
    }
}
