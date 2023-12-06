package Powerups;

public abstract class Powerup {
    public String name;
    public PowerupType type;
    public String description;
    public int strength;

    //public Sound sound;

    public Powerup(String name, PowerupType type, String description, int strength /*, Sound sound*/) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.strength = strength;
        //this.sound = sound;
    }

    public abstract void activatePowerUp(PowerupUser user);
    public String getDescription() {
        return description;
    }

    //public void playSound() {};
}