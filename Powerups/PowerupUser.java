package Powerups;

public interface PowerupUser {
    void startPowerup(Powerup powerup);

    void endPowerup();

    boolean isPowerupActive();
}
