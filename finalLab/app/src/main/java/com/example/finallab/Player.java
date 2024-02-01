package com.example.finallab;

public class Player {
    public final int MAX_HEALTH = 100;
    public final int MIN_HEALTH = 0;
    public final int MAX_DAMAGE = 12;
    private String name;
    private Damage damage;
    private Weapon weapon;
    private int health;

    public Player(String name) throws InvalidWeaponException, InvalidNameException {
        setName(name);
        setHealth(100);
    }

    public void setCompact(String weapon) throws InvalidWeaponException {
        switch (weapon.toUpperCase()){
            case "SWORD":
                setWeapon(Weapon.SWORD);
                setDamage(Damage._3);
                break;
            case "CLAYMORE":
                setWeapon(Weapon.CLAYMORE);
                setDamage(Damage._6);
                break;
            case "CATALYST":
                setWeapon(Weapon.CATALYST);
                setDamage(Damage._4);
                break;
            case "POLEARM":
                setWeapon(Weapon.POLEARM);
                setDamage(Damage._5);
                break;
            case "BOW":
                setWeapon(Weapon.BOW);
                setDamage(Damage._2);
                break;
            default:
                throw new InvalidWeaponException("Weapon is null or it is spell wrong or chose a different weapon or haves a space");
        }
    }

    public void setDamage(int damage) throws InvalidDamageException {
        if(damage <= MAX_DAMAGE){
            setDamage(Damage.valueOf("_" + damage));
        }else{
            throw new InvalidDamageException("It already pass the max limit or it return a null");
        }
    }

    public String getName() {
        return name;
    }

    private void setName(String name) throws InvalidNameException {
        if(name != null && name.length() > 0){
            this.name = name;
        }
        else{
            throw new InvalidNameException("Name cant have a null value and must be at least one character long");
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
        return "Player: " + getName() + "\nHealth: " + getHealth() +
                "\n" + getWeapon() + "\n" + getDamage();
    }
}
