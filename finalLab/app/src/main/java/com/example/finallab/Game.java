package com.example.finallab;

import java.util.Random;
import java.util.Timer;

public class Game {
    public final int MAX_ENEMIES = 50;
    public final int MAX_PLAYER_LIFE = 10;
    private Player[] playersLife;
    private Enemy[] enemies;
    private int playerCount = 0;
    private int enemyDeathCount = 0;
    private int playerPoints = 0;
    private int playerDodgePoints = 0;
    private int enemyDodgePoints = 0;
    private String output = "";
    private Random rand = new Random();
    private int onHealPoints = 10;
    private int onDodgePoints = 5;
    private int onBoostPoints = 2;
    private boolean skipHeal = false;
    private int skipDodge = 0;

    public Game() {
        playersLife = new Player[MAX_PLAYER_LIFE];
        enemies = new Enemy[MAX_ENEMIES];

        for (int i = 0; i < enemies.length; i++) {
            enemies[i] = new Enemy();
        }
    }

    public String displayEnemyText(){
        if(findCurrentEnemy() != null){
            return findCurrentEnemy().toString();
        }
        return "Enemy not found";
    }

    public String displayPlayerText(){
        if(findCurrentplayer() != null){
            return findCurrentplayer().toString();
        }
        return "player not found";
    }

    public int displayEnemyImage(){
        switch (findCurrentEnemy().getWeapon()) {
            case BOW:
                return R.drawable.enemybow;
            case SWORD:
                return R.drawable.enemysword;
            case POLEARM:
                return R.drawable.enemypolearm;
            case CATALYST:
                return R.drawable.enemycatalyst;
            case CLAYMORE:
                return R.drawable.enemyclaymore;
        }
        return R.drawable.ic_launcher_foreground;
    }

    public int displayPlayerImage(){
        switch (findCurrentplayer().getWeapon()) {
            case BOW:
                return R.drawable.playerbow;
            case SWORD:
                return R.drawable.playersword;
            case POLEARM:
                return R.drawable.playerpolearm;
            case CATALYST:
                return R.drawable.playercatalyst;
            case CLAYMORE:
                return R.drawable.playerclaymore;
        }
        return R.drawable.ic_launcher_foreground;
    }

    public String displayOutput(){
        return output;
    }

    public int getOnHealPoints() {return onHealPoints;}

    public int getOnDodgePoints() {return onDodgePoints;}

    public int getOnBoostPoints() {return onBoostPoints;}

    private void setSkipHeal(boolean skipHeal) {
        this.skipHeal = skipHeal;
    }

    public boolean isSkipHeal() {
        return skipHeal;
    }

    private void setSkipDodge(int skipDodge) {
        this.skipDodge = skipDodge;
    }

    private int getSkipDodge() {
        return skipDodge;
    }

    public void addPlayerlife(String name) throws InvalidWeaponException, InvalidNameException {
        try{
            Player player = new Player(name);
            playersLife[playerCount++] = player;
        } catch (InvalidWeaponException e) {
            throw new InvalidWeaponException(e.getMessage());
        } catch (InvalidNameException ex) {
            throw new InvalidNameException(ex.getMessage());
        }
    }

    private Enemy findCurrentEnemy(){
        for (int i = 0; i < enemies.length; i++) {
            if(enemies[i] == null){break;}
            if(enemies[i].getHealth() >= 0 || enemies[i].getHealth() <= 0){return enemies[i];}
        }
        return null;
    }

    private int findCurrentEnemyIndex(){
        for (int i = 0; i < enemies.length; i++) {
            if(enemies[i] == null){break;}
            if(enemies[i].getHealth() >= 0 || enemies[i].getHealth() <= 0){return i;}
        }
        return -1;
    }

    public Player findCurrentplayer(){
        for (int i = 0; i < playersLife.length; i++) {
            if(playersLife[i] == null){break;}
            if(playersLife[i].getHealth() >= 0 || playersLife[i].getHealth() <= 0){return playersLife[i];}
        }
        return null;
    }

    private int findCurrentPlayerIndex(){
        for (int i = 0; i < playersLife.length; i++) {
            if(playersLife[i] == null){break;}
            if(playersLife[i].getHealth() >= 0 || enemies[i].getHealth() <= 0){return i;}
        }
        return -1;
    }

    private void removeEnemy(int enemyIndex) {
        if (enemyIndex == -1){return;}
        Enemy[] temp = new Enemy[MAX_ENEMIES];
        for (int i = 0; i < enemies.length - 1; i++) {
            if(i < enemyIndex){
                temp[i] = enemies[i];
            } else if (i >= enemyIndex) {
                temp[i] = enemies[i+1];
            }
        }
        enemies = temp;
    }

    private void removePlayer(int playerIndex) {
        if (playerIndex == -1){return;}
        Player[] temp = new Player[MAX_PLAYER_LIFE];
        for (int i = 0; i < playersLife.length - 1; i++) {
            if(i < playerIndex){
                temp[i] = playersLife[i];
            } else if (i >= playerIndex) {
                temp[i] = playersLife[i+1];
            }
        }
        playersLife = temp;
    }

    private void gainLife() throws InvalidWeaponException, InvalidNameException {
        if(checkForNull()){
            if(playerPoints == 5 && enemyDeathCount <= 45){
                addPlayerlife(findCurrentplayer().getName());
                addWeapon(findCurrentplayer().getWeapon().getWeaponName());
                output = "Player gain an extra life";
                playerPoints = 0;
            }
        }
    }

    private void addWeapon(String weaponName) throws InvalidWeaponException {
        for (int i = 0; i < playersLife.length; i++) {
            if(playersLife[i] == null){
                break;
            }
            if(playersLife[i] == findCurrentplayer()){
                continue;
            }
            playersLife[i].setCompact(weaponName);
        }
    }

    private boolean checkForNull(){
        for (int i = 0; i < playersLife.length; i++) {
            if(playersLife[i] == null){
                return true;
            }
        }
        return false;
    }

    public boolean gameOver(){
        if(findCurrentplayer() == null){
            output = "Player has lost";
            return true;
        } else if (findCurrentEnemy() == null) {
            output = "Player has won";
            return true;
        }
        return false;
    }

    public void playerAttack() throws InvalidWeaponException, InvalidNameException {
        Player player = findCurrentplayer();
        Enemy enemy = findCurrentEnemy();
        int totalDamage = player.getDamage().getDamagePoints() - enemyDodgePoints;
        enemyDodgePoints = 0;
        if(totalDamage < 0) {
            output = "Player Did No Damage";
            return;
        }int enemyIndex = findCurrentEnemyIndex();
        int chance = rand.nextInt(10) + 1;
        switch (chance) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                enemy.setHealth(enemy.getHealth() - totalDamage);
                output = "Player Attack";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                enemy.setHealth(enemy.getHealth() - (totalDamage / 2));
                output = "Player Attack and did Half-Damage";
                break;
            case 10:
                output = "Enemy has Dodge Player Attack";
                break;
        }if(enemy.getHealth() <= 0){
            enemyDeathCount++;
            playerPoints++;
            removeEnemy(enemyIndex);
            gainLife();
        }
    }

    public void playerHeal(){
        if(onHealPoints > 0) {
            int healPoints = rand.nextInt(100) + 1;
            findCurrentplayer().setHealth(findCurrentplayer().getHealth() + healPoints);
            output = "Player has Heal " + healPoints + " points";
            onHealPoints--;
        }else {
            output = "You Don't Have Any Heals Left";
        }

    }

    public void playerBoost() throws InvalidDamageException {
        try {
            if (onBoostPoints > 0) {
                int damagepoints = findCurrentplayer().getDamage().getDamagePoints();
                damagepoints *= 2;
                findCurrentplayer().setDamage(damagepoints);
                output = "Player has Boosted";
                onBoostPoints--;
            } else {
                output = "You are out of Boost and Skip Your Turn";
            }
        }catch (InvalidDamageException e){
            throw new InvalidDamageException(e.getMessage());
        }
    }

    public void playerDodge(){
        if(onDodgePoints > 0) {
            playerDodgePoints = 100;
            output = "Player Dodge";
            onDodgePoints--;
        }else {
            output = "You Don't Have Any Dodge Left and Skip Your Turn";
        }
    }

    public void enemyTurn(){
        int choice = rand.nextInt(4)+1;

        switch (choice){
            case 1:
                enemyAttack();
                break;
            case 2:
                if(getSkipDodge() == 2) {
                    enemyDodge();
                    setSkipDodge(0);
                    break;
                }else {
                    output = "Enemy Skip Turn";
                    setSkipDodge(getSkipDodge() + 1);
                    break;
                }
                case 3:
                if(!isSkipHeal()){
                    enemyHeal();
                    setSkipHeal(true);
                    break;
                }else {
                    setSkipHeal(false);
                    output = "Enemy Skip Turn";
                    break;
                }
            case 4:
                if(findCurrentEnemy().getDamage().getDamagePoints() < 7) {
                    enemyBoost();
                    break;
                }
                else{
                    output = "Enemy Skip Turn";
                    break;
                }
        }
    }

    private void enemyAttack() {
        Player player = findCurrentplayer();
        Enemy enemy = findCurrentEnemy();
        int totalDamage = enemy.getDamage().getDamagePoints() - playerDodgePoints;
        playerDodgePoints = 0;
        if(totalDamage < 0) {
            output = "Enemy Did No Damage";
        }else {
            int playerIndex = findCurrentPlayerIndex();
            int chance = rand.nextInt(10) + 1;
            switch (chance) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    player.setHealth(player.getHealth() - totalDamage);
                    output = "Enemy Attack";
                    break;
                case 6:
                case 7:
                case 8:
                case 9:
                    player.setHealth(player.getHealth() - (totalDamage / 2));
                    output = "Enemy Attack and did Half-Damage";
                    break;
                case 10:
                    output = "Player has Dodge Enemy Attack";
                    break;
            }
            if(player.getHealth() <= 0){
                removePlayer(playerIndex);
            }
        }
    }

    private void enemyDodge(){
        enemyDodgePoints = 100;
        output = "Enemy Dodge";
    }

    private void enemyHeal(){
        int healPoints = rand.nextInt(50) + 1;
        findCurrentEnemy().setHealth(findCurrentEnemy().getHealth() + healPoints);
        output = "Enemy has Heal " + healPoints + " points";
    }

    private void enemyBoost()  {
        int damagepoints = findCurrentEnemy().getDamage().getDamagePoints();
        damagepoints *= 2;
        findCurrentEnemy().setDamage(damagepoints);
        output = "Enemy has Boosted";
    }
}