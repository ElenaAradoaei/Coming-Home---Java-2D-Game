package Coming_Home;

import ComingHomeEntity.Entity;
import ComingHomeGraphics.Sprite;
import ComingHomeGraphics.SpriteSheet;
import Coming_Home.input.KeyInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Game extends Canvas implements Runnable{  //canvas ne lasa sa afisam lucruri in frame
                                                       //runnable este o interfata predef
    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 270;
    public static final int HEIGHT = WIDTH/17*10;
    public static final int SCALE =4;
    public static final String TITLE = "Coming Home";

    public static int coins = 0;

    private Thread thread;
    private boolean running = false;
    public static Handler handler;
    public static SpriteSheet sheet;
    public static Camera cam;
    private BufferedImage image;

    public static Sprite grass;
    public static Sprite player[] = new Sprite[8];
    public static Sprite mushroom;
    public static Sprite goomba[];
    public static Sprite coin;

    public Game(){
        Dimension size = new Dimension(WIDTH*SCALE, HEIGHT*SCALE);
        setPreferredSize(size); //canvas
        setMaximumSize(size);
        setMinimumSize(size);
    }

    private void init() {
        handler = new Handler();
        cam = new Camera();
        sheet = new SpriteSheet("/textures/spritesheet.png");

        addKeyListener(new KeyInput()); //dam un nou fresh input
        grass = new Sprite(sheet,1,1);
        mushroom = new Sprite(sheet, 2, 1);
       coin = new Sprite(sheet, 3, 1);
        player = new Sprite[8];
        goomba = new Sprite[8];
        for(int i=0; i<player.length; i++){
            player[i] = new Sprite(sheet, i+1,16);
        }
        for(int i=0; i<goomba.length; i++){
            goomba[i] = new Sprite(sheet, i+1,15);
        }

        try {
            image = ImageIO.read(getClass().getResource("/textures/level1.png"));
        } catch (IOException e){
            e.printStackTrace();
        }

        handler.createLevel(image);
    }

    private synchronized void start(){
        if(running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop(){
        if(!running) return;
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        init();
        requestFocus(); //focuseaza ceea ce rulam
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double ns = 1000000000.0/60.0;
        int frames = 0;
        int ticks = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta>=1){
                tick();
                ticks++;
                delta--;
            }
            if (running)
                render();
            frames++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println(frames +" Frame per second");
                ticks = 0;
                frames = 0;
            }
        }
        stop();
    }
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs==null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(new Color(0, 255, 196));
        g.fillRect(0,0,getWidth(),getHeight());

        g.drawImage(Game.coin.getBufferedImage(), 50, 20, 70, 70, null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier", Font.BOLD,0));
        g.drawString("x" + coins, 160, 120);

        g.translate(cam.getX(), cam.getY());
        handler.render(g);
        g.dispose();
        bs.show();
    }

    public void tick(){ // pentru update
        handler.tick();
        for(Entity e:handler.entity){
            if(e.getId()==Id.player){
                cam.tick(e);
            }
        }
    }

    public static int getFrameWidth(){
       return WIDTH*SCALE;
    }
    public static int getFrameHeight(){
        return HEIGHT*SCALE;
    }

    public static void main(String[] args){
        Game game = new Game();
        JFrame frame = new JFrame(TITLE); //fereastra  cu titlu
        frame.add(game);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); //fereastra in mijlocul ecranului
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE); //butonul X inchide fereastra
        frame.setVisible(true);
        game.start();
    }
}

