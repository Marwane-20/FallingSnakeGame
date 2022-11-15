import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class FallingSnakes {
	public FallingSnakes(String difficulty) {
		new Jeu(difficulty);
	}
	public static class Jeu extends JFrame{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Jeu(String difficulty){
			this.add(new Grille2D(difficulty));
			this.setTitle("Snake");
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setResizable(true);
	        this.pack();
	        this.setVisible(true);
	        this.setLocationRelativeTo(null);	        
		}
		public void disposeJeu() {
			this.dispose();
		}		
	}
	public static class Grille2D extends JPanel implements ActionListener{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		static final int screenWidth = 700;
		static final int screenHeight = 700;
		static final int unitSize = 20;
		static int timeInterval;
		private Timer timer;
		protected boolean snakeIsInvincible = false;
		static String direction = "right";
		static Snake snake;
		static List<Snake> snakes;
		static List<Piege> pieges;
		static Canon canon;
		private static Random randomX;
		static boolean shootCanon = false;
		static boolean trapsCreated = false;
		static final int InvincibilityDuration = 6000;
		private int nbrBois;
		private int nbrFraise;
		private int nbrMyrtille;
		private int nbrOr;
		private BufferedImage proImg;
		private BufferedImage canonImg;
        
		public Grille2D(String difficulty)  {
			this.setPreferredSize(new Dimension(screenWidth, screenHeight));
			this.setBackground(Color.WHITE);
	        this.setFocusable(true);
	        this.addKeyListener(new Adapter());	        
	        if(difficulty.equals("easy")) {snake = new Snake(13); timeInterval = 90; setNbrPiege("easy");}
	        else if(difficulty.equals("normal")) {snake = new Snake(6); timeInterval = 60;setNbrPiege("normal");}
	        else if(difficulty.equals("hard")) {snake = new Snake(5); timeInterval = 20;setNbrPiege("hard");}
	        timer = new Timer(timeInterval, this);
	        timer.start();
	        
	        pieges = new ArrayList<Piege>();
	        snakes = new ArrayList<Snake>();
	        canon = new Canon();
	        try {
				proImg = ImageIO.read(new File(".\\\\images\\\\projectileimg.jpeg"));
				canonImg = ImageIO.read(new File(".\\\\images\\\\canonimg.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
	        randomX = new Random(); 
		}
		public void setNbrPiege(String difficulty) {
			if(difficulty.equals("easy")) {
				nbrBois = 8;nbrFraise = 5;nbrMyrtille = 3;nbrOr = 1;
			}
			else if(difficulty.equals("normal")) {
				nbrBois = 12;nbrFraise = 9;nbrMyrtille = 6;nbrOr = 3;
			}
			else if(difficulty.equals("hard")) {
				nbrBois = 20;nbrFraise = 10;nbrMyrtille = 6;nbrOr = 6;
			}
		}
		@Override
		public void paintComponent(Graphics g)  {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g.create();
	        try {
	        	if(snake.snakeParts.size() <= 2) {
		    		timer.stop();
		    		JOptionPane.showMessageDialog(this," You have won !!!!");	    		
					return;
					
				}
				draw(g2d);
			} catch (IOException e) {
				e.printStackTrace();
			}	       
	    }
	    
	    public void draw(Graphics2D g) throws IOException {
	        drawGrid(g);
	        setTraps(g);
	        drawTraps(g);
	        drawSnake(g);
	        drawCanon(g);
	        if(shootCanon) {
	        	shootProjectile(g);
	        	sheckTrapProjectileCollision();
	        	if(snakeIsInvincible == false)
	        		checkProjectileCollision();
	        	
	        }
	    }	    
	    private void drawGrid(Graphics2D g) {
	        for (int i = 1; i < screenHeight / unitSize; i++) {
	            g.drawLine(i * unitSize, 0, i * unitSize, screenHeight );
	            g.drawLine(0, i * unitSize, screenWidth, i * unitSize);
	        }	       
	    }
	    private void setTraps(Graphics2D g) throws IOException {	    
			if(trapsCreated)
	    		return;
	    	for(int i = 0;i < nbrBois;i++) {
	    		pieges.add(new Bois());
	    	}
	    	for(int i = 0;i < nbrFraise;i++) {
	    		pieges.add(new Fraise());
	    	}
	    	for(int i = 0;i < nbrMyrtille;i++) {
	    		pieges.add(new Myrtille());
	    	}
	    	for(int i = 0;i < nbrOr;i++) {
	    		pieges.add(new Or());
	    	}
	    	trapsCreated = true;
	    }
	    private void drawTraps(Graphics2D g) {
	    	for(Piege p:pieges) { 
	        	g.drawImage(p.image, p.posX * unitSize, p.posY * unitSize,null);
	        }
	    }
	    public void drawSnake(Graphics2D g) {	    	
	    	g.setColor(Color.GREEN);
	    	if(snakeIsInvincible)
	    		g.setColor(Color.BLACK);
	        for(int i = 0;i < snake.snakeParts.size() ;i++) 
	        	g.fillRect(snake.snakeParts.get(i).posX, snake.snakeParts.get(i).posY, unitSize, unitSize);
	    }
	    public void drawCanon(Graphics2D g) {
	    	g.drawImage(canonImg,canon.posX, screenHeight - unitSize * 2, null);
	    }
	    public void shootProjectile(Graphics2D g) {
	    	g.setColor(Color.RED);
	    	for(Projectile p:canon.projectiles) {
	    		if(p.posY >= 0) {
	    			p.posY -= unitSize;
		    		g.drawImage(proImg,p.posX, p.posY, null);	
		    		
	    		}
	    	}
	    	
	    }
	  
		@Override
		public void actionPerformed(ActionEvent e) {	    	

	    	moveSnake();
	    	repaint();
			
		}
		public void moveSnake() {
			setupSnake();			
			checkTrapCollision();
			
			checkWallCollision();			
		}
		public void setupSnake() {			
			if(direction.equals("right") ) 
				snake.snakeParts.get(0).posX += unitSize;			
			else if(direction.equals("left")) 
				snake.snakeParts.get(0).posX -= unitSize;
							
			for (int i = snake.snakeParts.size() - 1; i >0; i--) {
	            snake.snakeParts.get(i).posX = snake.snakeParts.get(i - 1).posX; 
	            snake.snakeParts.get(i).posY = snake.snakeParts.get(i - 1).posY; 
	        }
			
		}
		private void checkTrapCollision() {
			for(int j = 0;j < pieges.size();j++) {				       	
		            if ((snake.snakeParts.get(0).posX + unitSize == pieges.get(j).posX * unitSize || snake.snakeParts.get(0).posX - unitSize == pieges.get(j).posX * unitSize) && snake.snakeParts.get(0).posY == pieges.get(j).posY * unitSize) {	
		            	if(pieges.get(j).type.equals("bois")) {
		            		
		            		if(direction.equals("left")) {
		            			snake.snakeParts.get(0).posY += unitSize;
		            			direction = "right";
		            		}
		            		else {
		            			snake.snakeParts.get(0).posY += unitSize;
		            			direction = "left";
		            		}
		            	}
		            	
		            }
	        	}
			for(int j = 0;j < pieges.size();j++) {				       	
	            if (snake.snakeParts.get(0).posX  == pieges.get(j).posX * unitSize && snake.snakeParts.get(0).posY == pieges.get(j).posY * unitSize) {	
	            	if(pieges.get(j).type.equals("fraise")) {
	            		snake.snakeParts.add(new SnakePart());
	            		pieges.remove(j);
	            	}
	            	else if(pieges.get(j).type.equals("myrtille")) {
	            		snakeIsInvincible = true;
	            		new java.util.Timer().schedule( 
	            		        new java.util.TimerTask() {
	            		            @Override
	            		            public void run() {
	            		                snakeIsInvincible  = false;
	            		            }
	            		        }, 
	            		        InvincibilityDuration 
	            		);
	            		pieges.remove(j);
	            	}
	            	else if(pieges.get(j).type.equals("or")) {
	            		for(Piege p:pieges) { 
	        	        	p.posX = randomX.nextInt(screenHeight / unitSize);
	        	        	p.posY = randomX.nextInt((screenHeight / unitSize) - 4);
	        	        }
        	        	pieges.remove(j);
	            	}
	            }
			}
			
	    }		
		private void checkWallCollision() {			
			if(snake.snakeParts.get(0).posX >= screenWidth) {
				direction = "left";
				snake.snakeParts.get(0).posY += unitSize;					
			}
			if(snake.snakeParts.get(0).posX < 0) {
				direction = "right";
				snake.snakeParts.get(0).posY += unitSize;					
			}
			if(snake.snakeParts.get(0).posY > screenHeight - 3 * unitSize && snake.snakeParts.get(0).posX == canon.posX) {
				timer.stop();
	    		JOptionPane.showMessageDialog(this," You have lost !!!!");
			}
		}
		public void checkProjectileCollision() {			
			for(int j = 0;j < canon.projectiles.size();j++) {
				for (int i = 0; i < snake.snakeParts.size(); i++) {	        	
		            if (snake.snakeParts.get(i).posX == canon.projectiles.get(j).posX && snake.snakeParts.get(i).posY == canon.projectiles.get(j).posY - unitSize) {	                
		                snake.snakeParts.remove(snake.snakeParts.get(snake.snakeParts.size() - 1));
		            }
	        	}
	        }
	    }
		public void sheckTrapProjectileCollision() {
			for(int j = 0;j < canon.projectiles.size();j++) {
				for (int i = 0; i < pieges.size(); i++) {	        	
		            if (pieges.get(i).posX * unitSize == canon.projectiles.get(j).posX && pieges.get(i).posY * unitSize == canon.projectiles.get(j).posY) {	                
		            	pieges.remove(i);		            	
		            }
	        	}
	        }
		}
		 public static class Snake{
			 public List<SnakePart> snakeParts;		
			 public int length;
			 public Snake(int length) {
				 this.length = length;
				 snakeParts = new ArrayList<SnakePart>();
				 for(int i = 0;i < length;i++) {
					 snakeParts.add(new SnakePart());
				 }
					 
			 }
		 }
		 public static abstract class Piege {
			 public String type;
			 public BufferedImage image;
			 public int posX;
			 public int posY;
			 public Piege() {
				 posX = randomX.nextInt(screenWidth / unitSize);
				 posY = randomX.nextInt((screenHeight / unitSize) - 4);
				 
			 }
			 public void setPos(int x, int y) {
				 posX = x;
				 posY = y;
			 }			 
		 }
		 public class Bois extends Piege{
			 public Bois() throws IOException {
				 this.type = "bois";
				 this.image = ImageIO.read(new File(".\\\\images\\\\bois.png"));
			 }
		 }		
		 public class Fraise extends Piege{
			 public Fraise() throws IOException {
				 this.type = "fraise";
				 this.image = ImageIO.read(new File(".\\\\images\\\\fraise.png"));
			 }
		 }
		 public class Myrtille extends Piege{
			 public Myrtille() throws IOException {
				 this.type = "myrtille";
				 this.image = ImageIO.read(new File(".\\\\images\\\\Myrtille.png"));
			 }
		 }
		 public class Or extends Piege{
			 public Or() throws IOException {
				 this.type = "or";
				 this.image = ImageIO.read(new File(".\\\\images\\\\Gold.png"));
			 }
		 }
		 public static class SnakePart {
			 public int posX;
			 public int posY;
			 				
			 public void setPos(int x , int y) {
				 posX = x;
				 posY = y;
			 }
		 }
		 public static class Canon{
			 public int posX = unitSize * (int)(unitSize / 1.3);		 
			 public List<Projectile> projectiles;
			 public Canon() {
				 projectiles = new ArrayList<Projectile>();
			 }
			 public void prepareProjectile(int x) {
				 Projectile p = new Projectile();
				 p.setPos(x, screenHeight - unitSize);
				 projectiles.add(p);
				 
			 }
		 }
		 public static class Projectile{
			 public int posX;
			 public int posY = Grille2D.unitSize;
			 public void setPos(int x, int y) {
				 posX = x;
				 posY = y;
			 }
		 }
		 public static class Adapter extends KeyAdapter {
		        @Override
		        public void keyPressed(KeyEvent e) {
		            switch(e.getKeyCode()) {
		                case KeyEvent.VK_RIGHT:
		                	if(canon.posX >= screenWidth - unitSize)
		                		return;
		                    canon.posX += unitSize;
		                    break;

		                case KeyEvent.VK_LEFT:
		                	if(canon.posX <= 0)
		                		return;
		                    canon.posX -= unitSize;
		                    break;

		                case KeyEvent.VK_UP:
		                   
		                    break;

		                case KeyEvent.VK_DOWN:
		                    
		                    break;
		                    
		                case KeyEvent.VK_SPACE:
		                	shootCanon = true;
		                	canon.prepareProjectile(canon.posX);		                	
		                	break;
		            }
		        }
		    }
		
	}
	
	 	

}
