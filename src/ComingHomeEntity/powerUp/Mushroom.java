package ComingHomeEntity.powerUp;

import ComingHomeEntity.Entity;
import ComingHomeTile.Tile;
import Coming_Home.Game;
import Coming_Home.Handler;
import Coming_Home.Id;

import java.awt.*;
import java.util.Random;

public class Mushroom extends Entity {
    private Random random = new Random();
    public Mushroom(int x, int y, int width, int height, boolean solid, Id id, Handler handler){
        super(x, y, width, height, solid, id, handler);
        int dir = random.nextInt(2);

        switch (dir){
            case 0:
                setVelX(-2);
                break;
            case 1:
                setVelX(2);
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.mushroom.getBufferedImage(), x, y, width, height, null);
    }

    @Override
    public void tick() {
        x+=velX;
        y+=velY;
        for(int i=0; i<handler.tile.size(); i++){     //coliziuni
            Tile t = handler.tile.get(i);
            if(t.isSolid()){
                if (getBoundsBottom().intersects(t.getBounds())) {
                    setVelY(0);
                    if (falling) {
                        falling = false;
                    }
                } else {
                    if (!falling) {
                        gravity = 0.05;
                        falling = true;
                    }
                }
                if (getBoundsLeft().intersects(t.getBounds())) {
                    setVelX(2);
                }
                if (getBoundsRight().intersects(t.getBounds())) {
                    setVelX(-2);
                }
            }
        }
        if(falling){
            gravity+=0.1;
            setVelY((int)gravity);
        }
    }
}
