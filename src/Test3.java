import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;


import java.io.IOException;


public class Test3 {
	/*                                                  Remove this comment to run Test3 which is a normal nokia snake game with no food
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Jeu frame = new Jeu();
					frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	public static class Jeu extends JFrame{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Jeu(){
			this.add(new Grille2D());
			this.setTitle("Snake");
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setResizable(true);
	        this.pack();
	        this.setVisible(true);
	        this.setLocationRelativeTo(null);	        
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
		static int timeInterval = 70;
		private Timer timer;
		protected boolean snakeIsInvincible = false;
		static String direction = "right";
		static Snake snake;
		
		private static Random randomX;
		
	
        
		public Grille2D() {
			this.setPreferredSize(new Dimension(screenWidth, screenHeight));
			this.setBackground(Color.WHITE);
	        this.setFocusable(true);
	        this.addKeyListener(new Adapter());	    
	        snake = new Snake(6);
	        timer = new Timer(timeInterval, this);
	        timer.start();
	        
	      
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
	        drawSnake(g);	       
	    }	    
	    private void drawGrid(Graphics2D g) {
	        for (int i = 1; i < screenHeight / unitSize; i++) {
	            g.drawLine(i * unitSize, 0, i * unitSize, screenHeight );
	            g.drawLine(0, i * unitSize, screenWidth, i * unitSize);
	        }	       
	    }	   
	   
	    public void drawSnake(Graphics2D g) {	    	
	    	g.setColor(Color.GREEN);
	    	if(snakeIsInvincible)
	    		g.setColor(Color.BLACK);
	        for(int i = 0;i < snake.snakeParts.size() ;i++) 
	        	g.fillRect(snake.snakeParts.get(i).posX, snake.snakeParts.get(i).posY, unitSize, unitSize);
	    }	   
	  
		@Override
		public void actionPerformed(ActionEvent e) {	    	

	    	moveSnake();
	    	repaint();
			
		}
		public void moveSnake() {
			setupSnake();						
			checkWallCollision();			
		}
		public void setupSnake() {
			if(snake.snakeParts.size() == 1) {
				return;
			}
			if(direction.equals("right") ) {
				snake.snakeParts.get(0).posX += unitSize;							
			}
			else if(direction.equals("left")) 
				snake.snakeParts.get(0).posX -= unitSize;
				
			else if(direction.equals("down")) 
				snake.snakeParts.get(0).posY += unitSize;
							
			else if(direction.equals("up")) 
				snake.snakeParts.get(0).posY -= unitSize;
				
			
			for (int i = snake.snakeParts.size() - 1; i >0; i--) {
	            snake.snakeParts.get(i).posX = snake.snakeParts.get(i - 1).posX; 
	            snake.snakeParts.get(i).posY = snake.snakeParts.get(i - 1).posY; 
	        }
			
		}
		
		private void checkWallCollision() {
			if(snake.snakeParts.size() == 1) {
				return;
			}
			if(snake.snakeParts.get(0).posX >= screenWidth) {
									
			}
			if(snake.snakeParts.get(0).posX < 0) {
								
			}
			if(snake.snakeParts.get(0).posY > screenHeight - unitSize) {
				timer.stop();
	    		JOptionPane.showMessageDialog(this," You have lost !!!!");
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
		 
		 public static class SnakePart {
			 public int posX;
			 public int posY;
			 				
			 public void setPos(int x , int y) {
				 posX = x;
				 posY = y;
			 }
		 }
		
		 public static class Adapter extends KeyAdapter {
		        @Override
		        public void keyPressed(KeyEvent e) {
		            switch(e.getKeyCode()) {
		            case KeyEvent.VK_RIGHT:
	                    if (!direction.equals("left")) {
	                        direction = "right";
	                    }
	                    break;

	                case KeyEvent.VK_LEFT:
	                    if (!direction.equals("right")) {
	                        direction = "left";
	                    }
	                    break;

	                case KeyEvent.VK_UP:
	                    if (!direction.equals("down")) {
	                        direction = "up";
	                    }
	                    break;

	                case KeyEvent.VK_DOWN:
	                    if (!direction.equals("up")) {
	                        direction = "down";
	                    }
	                    break;
		            }
		        }
		    }
		
	}
	
	 	

}
