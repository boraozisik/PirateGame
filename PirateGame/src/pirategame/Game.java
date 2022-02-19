
package pirategame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Bora
 */

class Cannonball{
    private int x;
    private int y;

    public Cannonball(int x, int y) {
        this.x = x;
        this.y = y;
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
public class Game extends JPanel implements KeyListener,ActionListener{
    Timer timer = new Timer(1,this);
    private int passedTime = 0;
    private int cannonballSpent = 0;
    
    private BufferedImage image1;
    private BufferedImage image2;
    private BufferedImage image3;
    
    private ArrayList<Cannonball> cannonballs = new ArrayList<Cannonball>();
    
    private int cannonballdirY = 3;
    private int pirateX = 0;
    private int piratedirX = 5;
    private int pirateShipX = 0;
    private int dirSeaX = 20;

    public boolean checkImpact(){
        for(Cannonball cannonball: cannonballs){
            if(new Rectangle(cannonball.getX(),cannonball.getY(),511 / 30, 491 / 30).intersects(new Rectangle(pirateX,0,377/10,367/10))){
            return true;
            }
        }
        return false;
    }
    
    public Game() {
        
        try {
            image1 = ImageIO.read(new FileImageInputStream(new File("pirate_ship.png")));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            image2 = ImageIO.read(new FileImageInputStream(new File("pirate.png")));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            image3 = ImageIO.read(new FileImageInputStream(new File("cannonball.png")));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        setBackground(Color.cyan);
        
        timer.start();
        
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        
        passedTime += 5;
        
        g.drawImage(image2, pirateX, 0,image2.getWidth() / 10, image2.getHeight() / 10,this);
        
        g.drawImage(image1, pirateShipX, 490,image2.getWidth() / 5, image2.getHeight() / 5,this );
        
        for(Cannonball cannonball: cannonballs){
            if(cannonball.getY() < 0){
                cannonballs.remove(cannonball);
            }
        }
                       
        for(Cannonball cannonball: cannonballs){
           g.drawImage(image3,cannonball.getX(), cannonball.getY(),image3.getWidth() / 30 ,image3.getHeight() / 30 ,this);
        }
        
        if(checkImpact()){
            timer.stop();
            
            String message = "You have won!!\n" +
                    "Cannonball Spent: " +cannonballSpent + "\n" +
                    "Passed Time: " + (passedTime / 1000.0) + " seconds";
            
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }

    @Override
    public void repaint() {
        super.repaint(); 
    }
    
    
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        
        if(code == KeyEvent.VK_LEFT){
            if(pirateShipX <= 0){
                pirateShipX = 0;
            }
            else{
                pirateShipX -= dirSeaX;
            }
        }
        else if(code == KeyEvent.VK_RIGHT){
            if(pirateShipX >= 708){
                pirateShipX = 708;
            }
            else{
                pirateShipX += dirSeaX;
            }
        }
        else if(code == KeyEvent.VK_CONTROL){
            cannonballs.add(new Cannonball(pirateShipX + 30, 490));
            
            cannonballSpent++;
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        for(Cannonball cannonball: cannonballs){
            cannonball.setY(cannonball.getY() - cannonballdirY);
        }
        pirateX += piratedirX;
        
           if(pirateX >= 750){
               piratedirX = -piratedirX;
           }
           if(pirateX <= 0){
               piratedirX = -piratedirX;
           }
        
        repaint();
    }
    
}
