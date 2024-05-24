package Coming_Home;

import ComingHomeEntity.Entity;

import static Coming_Home.Game.getFrameHeight;
import static Coming_Home.Game.getFrameWidth;

public class Camera {
    public int x, y;

    public void tick(Entity player){
        setX(-player.getX() + getFrameWidth()/2);
        setY(-player.getY() + getFrameHeight()/2);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
