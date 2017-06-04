package net.paiyou.entity.enums;

import net.paiyou.entity.interfaces.IWeight;

/**
 * Created by 72717 on 2017/6/3.
 */
public enum CardWeight implements IWeight {

    C2(0),
    A(14),
    K(13),
    Q(12),
    J(15),
    C10(10),
    C9(10),
    C8(10),
    C7(10),
    C6(10),
    C5(10),
    C4(),
    C3(3);

    private byte code;

    private CardWeight(int input) {
        this.code = (byte) input;
    }
}
