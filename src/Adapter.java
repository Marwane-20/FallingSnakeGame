import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Adapter extends KeyAdapter {     			//Listener class pour capturer l'input de l'utilisateur pour faire bouger le canon
				public Adapter(Canon canon) {
					Adapter.canon = canon;
				}
		        private static Canon canon;
		        public int screenWidth = Grille2D.screenWidth,
		   			 screenHeight = Grille2D.screenHeight,
		   			 unitSize = Grille2D.unitSize;
				private static boolean shootCanon = false;
				@Override
		        public void keyPressed(KeyEvent e) {
					int pos = canon.getPosX();
		            switch(e.getKeyCode()) {			// Ecoute 3 evenement possible LEFT,RIGHT,SPACE
		                case KeyEvent.VK_RIGHT:
		                	if(pos >= screenWidth - unitSize) //
		                		return;
		                    canon.setPosX(unitSize + pos);
		                    break;

		                case KeyEvent.VK_LEFT:
		                	if(pos <= 0)                   //verifier si le canon est toujours visible sur l'ecran
		                		return;
		                	canon.setPosX(pos - unitSize);
		                    break;

		                case KeyEvent.VK_UP:
		                   
		                    break;

		                case KeyEvent.VK_DOWN:
		                    
		                    break;
		                    
		                case KeyEvent.VK_SPACE:
		                	shootCanon = true;
						try {
							canon.prepareProjectile(pos);    //instancier un projectile pour le lancer à la position pos
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	                	
		                	break;
		            }
		        }
				public static Canon getCanon() {
					return canon;
				}
				public void setCanon(Canon canon) {
					Adapter.canon = canon;
				}
				public static boolean shouldShoot() {
					return shootCanon;
				}
		    }