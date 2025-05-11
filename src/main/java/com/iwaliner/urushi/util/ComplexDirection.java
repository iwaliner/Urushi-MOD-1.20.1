package com.iwaliner.urushi.util;

public enum ComplexDirection {
    N(1),
    N_NE(2),
    NE(3),
    E_NE(4),
    E(5),
    E_SE(6),
    SE(7),
    S_SE(8),
    S(9),
    S_SW(10),
    SW(11),
    W_SW(12),
    W(13),
    W_NW(14),
    NW(15),
    N_NW(16),
    N_AN(17),
    AN(18),
    A_AN(19),
    E_AE(20),
    AE(21),
    A_AE(22),
    S_AS(23),
    AS(24),
    A_AS(25),
    W_AW(26),
    AW(27),
    A_AW(28),
    A1(29),
    A2(30),
    N_BN(31),
    BN(32),
    B_BN(33),
    E_BE(34),
    BE(35),
    B_BE(36),
    S_BS(37),
    BS(38),
    B_BS(39),
    W_BW(40),
    BW(41),
    B_BW(42),
    B1(43),
    B2(44),
    FAIL(0);   // change if in need

    private int id;

    private ComplexDirection(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    public static boolean isNEWS(ComplexDirection direction) {
        if(direction == null){
            return false;
        }
        int id = direction.getID();
        if (id > 16) {
            return false;
        }
        return id % 4 == 1;
    }
}
