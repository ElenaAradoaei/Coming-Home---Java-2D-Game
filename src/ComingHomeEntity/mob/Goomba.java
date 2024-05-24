package ComingHomeEntity.mob;

import ComingHomeEntity.Entity;
import ComingHomeTile.Tile;
import Coming_Home.Game;
import Coming_Home.Handler;
import Coming_Home.Id;

import java.awt.*;
import java.util.Random;

public class Goomba extends Entity {
    private int frame = 0;
    private int frameDelay = 0;
    private Random random = new Random();

    public Goomba(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
        int dir = random.nextInt(2);

        switch (dir){
            case 0:
                setVelX(-2);
                facing = 0;
                break;
            case 1:
                setVelX(2);
                facing = 1;
                break;
        }
    }
    public void render(Graphics g){
        if(facing == 0) {
            g.drawImage(Game.goomba[frame+4].getBufferedImage(), x, y, width, height, null); // pt plaierii din stanga, trb sa sara in spritesheet peste cei din drt
        } else if (facing==1){
            g.drawImage(Game.goomba[frame].getBufferedImage(), x, y, width, height, null);
        }
    }
    public void tick() {
        x += velX;
        y += velY;
        for (int i = 0; i < handler.tile.size(); i++) {     //coliziuni
            Tile t = handler.tile.get(i);
            if (t.solid) {
                if (getBoundsBottom().intersects(t.getBounds())) {
                    setVelY(0);
                    if (falling) {
                        falling = false;
                    }
                } else {
                    if (!falling) {
                        gravity = 0.8;
                        falling = true;
                    }
                }
                if (getBoundsLeft().intersects(t.getBounds())) {
                    setVelX(2);
                    facing = 1;
                }
                if (getBoundsRight().intersects(t.getBounds())) {
                    setVelX(-2);
                    facing = 0;
                }
            }
        }
        if (falling) {
            gravity += 0.1;
            setVelY((int) gravity);
        }
        if (velX != 0) {
            frameDelay++;
            if (frameDelay >= 10) {
                frame++;
                if (frame >= 3) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
    }
}
