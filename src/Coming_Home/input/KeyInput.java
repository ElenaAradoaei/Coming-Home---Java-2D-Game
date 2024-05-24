package Coming_Home.input;

import ComingHomeEntity.Entity;
import Coming_Home.Game;
import Coming_Home.Id;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyInput implements KeyListener {
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        for(int i=0; i<Game.handler.entity.size(); i++){
            Entity en = Game.handler.entity.get(i);
            if(en.getId()== Id.player){
                switch(key){
                    case KeyEvent.VK_W:
                        if(!en.jumping){
                            en.jumping = true;
                            en.gravity=10.0;
                        }
                        break;
                    case KeyEvent.VK_A:
                        en.setVelX(-5);
                        break;
                    case KeyEvent.VK_D:
                        en.setVelX(5);
                        break;
                }
            }
        }
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        for(int i=0; i<Game.handler.entity.size(); i++){
            Entity en = Game.handler.entity.get(i);
            if(en.getId()== Id.player){
                switch(key){
                    case KeyEvent.VK_W:
                        en.setVelY(0);
                        break;
//                    case KeyEvent.VK_S:
//                        en.setVelY(0);
//                        break;
                    case KeyEvent.VK_A:
                        en.setVelX(0);
                        en.facing = 0;
                        break;
                    case KeyEvent.VK_D:
                        en.setVelX(0);
                        en.facing = 1;
                        break;
                }
            }
        }
    }
    public void keyTyped(KeyEvent arg0){
        //nu folosim
    }

}
