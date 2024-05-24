package ComingHomeEntity.mob;
import ComingHomeEntity.Entity;
import ComingHomeTile.Tile;
import Coming_Home.Game;
import Coming_Home.Handler;
import Coming_Home.Id;

import java.awt.*;

public class Player extends Entity {
    private int frame = 0;
    private int frameDelay = 0;

    private boolean animate = false;

    public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler){
        super(x, y, width, height,solid, id, handler);
        setVelX(0);
    }
    public void render(Graphics g){
        if(facing == 0) {
            g.drawImage(Game.player[frame+4].getBufferedImage(), x, y, width, height, null); // pt plaierii din stanga, trb sa sara in spritesheet peste cei din drt
        } else if (facing==1){
            g.drawImage(Game.player[frame].getBufferedImage(), x, y, width, height, null);
        }
    }
    public void tick(){
        x+=velX;
        y+=velY;
        if(x<=0) x=0;
        if(y<=0) y=0;
        if(velX!=0)
            animate = true;
        else
            animate = false;
        for(int i=0; i<handler.tile.size(); i++){
            Tile t = handler.tile.get(i);
            if(t.solid){
                if(t.getId()==Id.wall) {
                    if (getBoundsTop().intersects(t.getBounds())) {
                        setVelY(0);
                        if (jumping) {
                            gravity = 0.0; //0.0
                            falling = true;
                        }
                    }
                    if (getBoundsBottom().intersects(t.getBounds())) {
                        setVelY(0);
                        if (falling) {
                            falling = false;
                        }
                    } else {
                        if (!falling && !jumping) {
                            jumping = false;
                            gravity = 0.0;
                            falling = true;
                        }
                    }
                    if (getBoundsLeft().intersects(t.getBounds())) {
                        setVelX(0);
                        x = t.getX() + width;
                    }
                    if (getBoundsRight().intersects(t.getBounds())) {
                        setVelX(0);
                        x = t.getX() - width;
                    }
                    if(getBounds().intersects(t.getBounds())&&(t.getId()==Id.coin)){
                        Game.coins++;
                        t.die();
                    }
                }
            }
        }
        for(int i=0; i<handler.entity.size(); i++){
            Entity e = handler.entity.get(i);
            if(e.getId()==Id.mushroom){ //daca este o ciuperca, playerul isi dubleaza dim
                if(getBounds().intersects(e.getBounds())){
                    int tpX = getX();
                    int tpY = getY();
                    width+=25;
                    height+=25;
                    setX(tpX-width);
                    setY(tpY-height);
                    e.die();  //dupa ce este "atinsa", ciuperca dispare
                }
            } else if(e.getId()==Id.goomba){
                if(getBoundsBottom().intersects(e.getBoundsTop())){
                    e.die();
                }else{
                    if(getBounds().intersects(e.getBounds())){
                        die();
                    }
                }
            }
        }
        //salt + gravitatie
        if(jumping){
            gravity-=0.2;
            setVelY((int)-gravity);
            if(gravity<=0.0){
                jumping = false;
                falling = true;
            }
        }
        if(falling){
            gravity+=0.15;
            setVelY((int)gravity);
        }
        if(velX!=0){
            frameDelay++;
            if(frameDelay>=10){
                frame++;
                if(frame>=3){
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
    }
}
