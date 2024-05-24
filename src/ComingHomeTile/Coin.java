package ComingHomeTile;

import Coming_Home.Game;
import Coming_Home.Handler;
import Coming_Home.Id;

import java.awt.*;

public class Coin extends Tile{
    public Coin(int x, int y, int width, int height, boolean solid, Id id, Handler handler){
        super(x, y, width, height, solid,  id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.coin.getBufferedImage(), x, y, width, height, null);
    }

    @Override
    public void tick() {

    }
}
