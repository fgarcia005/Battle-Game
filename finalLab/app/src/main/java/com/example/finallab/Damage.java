package com.example.finallab;

public enum Damage {
    _2(2),
    _3(3),
    _4(4),
    _5(5),
    _6(6),
    _7(7),
    _8(8),
    _9(9),
    _10(10),
    _11(11),
    _12(12);
    private int magicNumber;

    Damage(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    @Override
    public String toString() {
        return "Damage Points: " + magicNumber;
    }

    public int getDamagePoints() {
        return magicNumber;
    }
}
