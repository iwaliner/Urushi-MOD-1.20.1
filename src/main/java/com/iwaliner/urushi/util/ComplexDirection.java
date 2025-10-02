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
    N_UN(17),
    UN(18),
    U_UN(19),
    E_UE(20),
    UE(21),
    U_UE(22),
    S_US(23),
    US(24),
    U_US(25),
    W_UW(26), // West by Up West
    UW(27), // Up West
    U_UW(28), // Up by Up West
    U_NSdir(29), // Facing Up, and the mirror goes North-South
    U_WEdir(30), // Facing Up, and the mirror goes East-West
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
    B1(43), // I DON'T KNOW WHAT IS B1 AND B2
    B2(44),
    FAIL(0);

    private int id;

    ComplexDirection(int id) {
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
