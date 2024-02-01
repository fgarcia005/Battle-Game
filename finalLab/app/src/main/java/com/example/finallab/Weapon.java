package com.example.finallab;

public enum Weapon {
    SWORD("Sword"),
    CLAYMORE("Claymore"),
    CATALYST("Catalyst"),
    POLEARM("Polearm"),
    BOW("Bow");
    private String weapon;

    Weapon(String weapon) {
        this.weapon = weapon;
    }

    @Override
    public String toString() {
        return "Weapon Name: " + weapon;
    }
    public String getWeaponName() {
        return weapon;
    }
}
