package entity;

import engine.DrawManager;
import engine.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BossTest {
    private Boss boss;
    Set<Bullet> bullets;
    @BeforeEach
    void setUp() {
        boss = new Boss(0,0, DrawManager.SpriteType.Boss,new GameState(1,1,1,1,1));
        bullets = new HashSet<Bullet>();
    }

    @Test
    void doPattern() {
        boss.doPattern(bullets);
        for (Bullet bullet: bullets){
            assertEquals(bullet.getSpeed(),4);
        }
    }

    @Test
    void bossShoot() {
        boss.bossShoot(this.bullets,0,0,20);
        boss.bossShoot(this.bullets,6,5,20);
        boss.bossShoot(this.bullets,8,1,20);
        boss.bossShoot(this.bullets,4,6,20);
        for (Bullet bullet: bullets){
            assertEquals(bullet.getSpeed(),20);
        }
    }

    @Test
    void boosShootWithX() {
        boss.boosShootWithX(this.bullets,0,50,20,10);
        boss.boosShootWithX(this.bullets,0,410,20,10);
        boss.boosShootWithX(this.bullets,0,00,20,10);
        boss.boosShootWithX(this.bullets,0,100,20,10);

        for (Bullet bullet: bullets){
            assertEquals(bullet.getSpeed(),10);
            assertEquals(bullet.getX_speed(),20);
        }
    }
}