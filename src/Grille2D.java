import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;



public class Grille2D extends JPanel implements ActionListener{  //la grille où se deroule le jeu
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		static final int screenWidth = 700;    // Taille horizontale de la grille
		static final int screenHeight = 700;   // Taille verticale de la grille
		static final int unitSize = 25;    // Taille de chaque cellule/case
		static int timeInterval;       //Vitesse de jeu
		private Timer timer;          //Le timer qui declanche le actionEvent
		static Snake snake;
		static List<Snake> snakes;
		static List<Piege> pieges;
		static Canon canon;
		private static Random randomX;
		static boolean trapsCreated = false;
		private int nbrBois;
		private int nbrFraise;     //nbr de piège
		private int nbrMyrtille;
		private int nbrOr;
		
        
		public Grille2D(String difficulty)  {
			this.setPreferredSize(new Dimension(screenWidth, screenHeight));
			this.setBackground(Color.WHITE);
	        this.setFocusable(true);
	        if(difficulty.equals("easy")) {snake = new Snake(4); timeInterval = 60; setNbrPiege("easy");}   //Nbr de piège et vitesse depend de la difficulté
	        else if(difficulty.equals("normal")) {snake = new Snake(6); timeInterval = 60;setNbrPiege("normal");}
	        else if(difficulty.equals("hard")) {snake = new Snake(5); timeInterval = 20;setNbrPiege("hard");}
	        timer = new Timer(timeInterval, this);
	        timer.start();
	        
	        pieges = new ArrayList<Piege>();
	        snakes = new ArrayList<Snake>();
	        try {
				canon = new Canon();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        this.addKeyListener(new Adapter(canon));	//Listener des touches         	        
	        randomX = new Random(); 
		}
		public void setNbrPiege(String difficulty) {
			if(difficulty.equals("easy")) {
				nbrBois = 0;nbrFraise = 38;nbrMyrtille = 0;nbrOr = 0;
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
	        Graphics2D g2d = (Graphics2D) g.create();  // On cr�e un clone de l'objet g de type Graphics2D 
	        try {
	        	if(snake.getSize() <= 2) {       //Si la taille du serpent est inf�rieure à 2 on gagne
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
	        drawGrid(g);                                            // Dessiner la grille
	        setTraps(g);						// initialiser le pieges
	        drawTraps(g);						// Dessiner le pieges
	        drawSnake(g);                                           // Dessiner le snake
	        drawCanon(g);                                           // Dessiner le canon
	        if(Adapter.shouldShoot()) {
	        	shootProjectile(g);                             //lancer le projectile
	        	sheckTrapProjectileCollision();                 //On verifie si le projectile a touché un piege
	        	if(!snake.isInvincible())
	        		checkProjectileCollision();             //Si le snake est invincible on ignore l'effet du projectile sur lui
	        	
	        }
	    }	    
	    private void drawGrid(Graphics2D g) {
	        for (int i = 1; i < screenHeight / unitSize; i++) {    //screenHeight / unitSize est le nombre de case du carrée
	            g.drawLine(i * unitSize, 0, i * unitSize, screenHeight );  
	            g.drawLine(0, i * unitSize, screenWidth, i * unitSize);
	        }	       
	    }
	    private void setTraps(Graphics2D g) throws IOException {	// Ajoute les pièges a la list des pièges   
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
	        	g.drawImage(p.getImage(), p.getPosX() * unitSize, p.getPosY() * unitSize,null);
	        }
	    }
	    public void drawSnake(Graphics2D g) {	    	
	    	g.setColor(Color.GREEN);
	    	if(snake.isInvincible())
	    		g.setColor(Color.BLACK);
	        for(int i = 0;i < snake.getSnakeParts().size() ;i++) 
	        	g.fillRect(snake.getSnakeParts().get(i).getPosX(), snake.getSnakeParts().get(i).getPosY(), unitSize, unitSize);
	    }
	    public void drawCanon(Graphics2D g) {
	    	g.drawImage(canon.canonImg,canon.getPosX(), screenHeight - unitSize * 2, null);
	    }
	    public void shootProjectile(Graphics2D g) {
	    	g.setColor(Color.RED);
	    	canon.prepareProjectile();
	    	for(Projectile p:canon.getProjectiles()) {
	    		if(p.getPosY() >= 0) {	    		                       //Si le projectile sort de la grille on arrete de le dessiner	
		    		g.drawImage(p.proImg,p.getPosX(), p.getPosY(), null);	
		    		
	    		}
	    	}
	    	
	    }
	  
		@Override
		public void actionPerformed(ActionEvent e) {	    //ActionEvent lancé par l'objet timer	

	    	moveSnake();
	    	repaint();
			
		}
		public void moveSnake() {                            
			setupSnake();			
			checkTrapCollision();
			
			checkWallCollision();			
		}
		public void setupSnake() {	             //	Changer la direction du mouvement du serpent	
			if(snake.getDirection().equals("right") ) 
				snake.moveRight();		
			else if(snake.getDirection().equals("left")) 
				snake.moveLeft();
			snake.move();			
			
		}
		private void checkTrapCollision() {
			for(int j = 0;j < pieges.size();j++) {				       	
		            if ((snake.getSnakeParts().get(0).getPosX() + unitSize == pieges.get(j).getPosX() * unitSize || snake.getSnakeParts().get(0).getPosX() - unitSize == pieges.get(j).getPosX() * unitSize) && snake.getSnakeParts().get(0).getPosY() == pieges.get(j).getPosY() * unitSize) {	
		            	if(pieges.get(j).getType().equals("bois")) {
		            		
		            		if(snake.getDirection().equals("left")) {	   //Chnager la direction si le serpent touche le bois	            				
		            			snake.setDirection("right");
		            		}
		            		else {
		            			snake.setDirection("left");
		            		}
		            		snake.getSnakeParts().get(0).setPosY(snake.getSnakeParts().get(0).getPosY() + unitSize);
		            	}
		            	
		            }
	        	}
			for(int j = 0;j < pieges.size();j++) {				       	
	            if (snake.getSnakeParts().get(0).getPosX()  == pieges.get(j).getPosX() * unitSize && snake.getSnakeParts().get(0).getPosY() == pieges.get(j).getPosY() * unitSize) {	
	            	if(pieges.get(j).getType().equals("fraise")) {
	            		snake.getSnakeParts().add(new SnakePart());
	            		pieges.remove(j);
	            	}
	            	else if(pieges.get(j).getType().equals("myrtille")) {
	            		snake.setInvincible(true);
	            		new java.util.Timer().schedule( 
	            		        new java.util.TimerTask() {
	            		            @Override
	            		            public void run() {
	            		            	snake.setInvincible(false);     //Le seprent devient normal après la fin de la durée spécifié par InvincibilityDuration
	            		            }
	            		        }, 
	            		         snake.getInvincibilityDuration() 
	            		);
	            		pieges.remove(j);
	            	}
	            	else if(pieges.get(j).getType().equals("or")) {
	            		for(Piege p:pieges) { 
	        	        	p.setPosX(randomX.nextInt(screenHeight / unitSize));    //Changer la position des pièges
	        	        	p.setPosY(randomX.nextInt((screenHeight / unitSize) - 4));	        	        	
	        	        }        	        	
	            		pieges.remove(j);   //Retirer chaque piège consommé
	            	}
	            	
	            }
			}
			
	    }		
		private void checkWallCollision() {	 //On verifie si le serpent est dans la grille (visible)
			if(snake.getSnakeParts().get(0).getPosX() >= screenWidth) {
				snake.setDirection("left");
				snake.getSnakeParts().get(0).setPosY(snake.getSnakeParts().get(0).getPosY() + unitSize);					
			}
			if(snake.getSnakeParts().get(0).getPosX() < 0) {
				snake.setDirection("right");
				snake.getSnakeParts().get(0).setPosY(snake.getSnakeParts().get(0).getPosY() + unitSize);					
			}
			if(snake.getSnakeParts().get(0).getPosY() > screenHeight - 3 * unitSize && snake.getSnakeParts().get(0).getPosX() == canon.getPosX()) {
				timer.stop();
	    		JOptionPane.showMessageDialog(this," You have lost !!!!");   //Si le serpent touche le canon on perd
			}
		}
		public void checkProjectileCollision() {			
			for(int j = 0;j < canon.getProjectiles().size();j++) {
				for (int i = 0; i < snake.getSnakeParts().size(); i++) {	        	
		            if (snake.getSnakeParts().get(i).getPosX() == canon.getProjectiles().get(j).getPosX() && snake.getSnakeParts().get(i).getPosY() == canon.getProjectiles().get(j).getPosY() - unitSize) {	                
		                snake.getSnakeParts().remove(snake.getSnakeParts().get(snake.getSnakeParts().size() - 1));
		            }
	        	}
	        }
	    }
		public void sheckTrapProjectileCollision() {
			for(int j = 0;j < canon.getProjectiles().size();j++) {
				for (int i = 0; i < pieges.size(); i++) {	        	
		            if (pieges.get(i).getPosX() * unitSize == canon.getProjectiles().get(j).getPosX() && pieges.get(i).getPosY() * unitSize == canon.getProjectiles().get(j).getPosY()) {	                
		            	pieges.remove(i);	//Si le projectile touche un piège il sera détruit	            	
		            }
	        	}
	        }
		}
}