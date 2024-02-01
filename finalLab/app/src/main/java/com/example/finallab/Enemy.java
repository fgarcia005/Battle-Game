package com.example.finallab;

import java.util.Random;

public class Enemy {
    public final int MAX_HEALTH = 100;
    public final int MIN_HEALTH = 0;
    public final int MAX_DAMAGE = 12;
    private String name;
    private Damage damage;
    private Weapon weapon;
    private int health;

    public Enemy() {
        makeEnemy();
    }

    private void makeEnemy() {
        Random rand = new Random();
        int random = rand.nextInt(5) + 1;
        switch (random){
            case 1:
                setName("Swordsman");
                setHealth(110);
                setWeapon(Weapon.SWORD);
                setDamage(Damage._3);
                break;
            case 2:
                setName("Slayer");
                setHealth(110);
                setWeapon(Weapon.CLAYMORE);
                setDamage(Damage._6);
                break;
            case 3:
                setName("Wizard");
                setHealth(110);
                setWeapon(Weapon.CATALYST);
                setDamage(Damage._4);
                break;
            case 4:
                setName("Guisarmier");
                setHealth(110);
                setWeapon(Weapon.POLEARM);
                setDamage(Damage._5);
                break;
            case 5:
                setName("Archer");
                setHealth(110);
                setWeapon(Weapon.BOW);
                setDamage(Damage._2);
                break;
        }
    }
    public void setDamage(int damage) {
        setDamage(Damage.valueOf("_" + damage));
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if(name != null && name.length() > 0){
            this.name = name;
        }
    }

    public Damage getDamage() {
        return damage;
    }

    private void setDamage(Damage damage) {
        if(damage != null){
            this.damage = damage;
        }
    }

    public Weapon getWeapon() {
        return weapon;
    }

    private void setWeapon(Weapon weapon) {
        if(weapon != null){
            this.weapon = weapon;
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if(health >= MIN_HEALTH && health <= MAX_HEALTH){
            this.health = health;
        } else if (health > MAX_HEALTH) {
            this.health = 100;
        } else if (health < MIN_HEALTH) {
            this.health = 0;
        }
    }

    @Override
    public String toString() {
        return "Enemy: " + getName() + "\nHealth: " + getHealth() +
                "\n" + getWeapon() + "\n" + getDamage();
    }
}
