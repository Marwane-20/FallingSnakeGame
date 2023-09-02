
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Canon{
	 private int screenHeight = Grille2D.screenHeight,
			 	unitSize = Grille2D.unitSize;
   	 private List<Projectile> projectiles;
	 private int posX = unitSize;	
	 public BufferedImage canonImg;                        //Image associé Ã  l'objet canon

	 public Canon() throws IOException {
		 projectiles = new ArrayList<Projectile>();    //La liste de tous les projectiles qu'on va lancer
		 canonImg = ImageIO.read(new File(".\\\\images\\\\canonimg.png"));
	 }
	 public void prepareProjectile(int x) throws IOException {
		 Projectile p = new Projectile();
		 p.setPos(x, screenHeight - unitSize);       //Création des projectiles
		 projectiles.add(p);		 
	 }
	public int getPosX() {     //Getter
		return posX;
	}
	public void setPosX(int posX) {  //Setter
		this.posX = posX;
	}
	public List<Projectile> getProjectiles() {
		return projectiles;
	}
	public void setProjectiles(List<Projectile> projectiles) {
		this.projectiles = projectiles;
	}
	public void prepareProjectile() {                         //Surcharge de fonction
    	for(Projectile p:projectiles) {
    		if(p.getPosY() >= 0) {
    			p.setPosY(p.getPosY() - unitSize);	  // On décremente la posY de tous les projectiles puisque MaxY = 0
    		}
    	}
    	
    }
}