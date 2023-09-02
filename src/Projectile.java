import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Projectile{
	 private int posX;
	 private int posY = Grille2D.unitSize;
	 public BufferedImage proImg;                 //Image associé à l'objet Projectile
	 public Projectile() throws IOException {
		 proImg = ImageIO.read(new File(".\\\\images\\\\projectileimg.jpeg"));
	 }
	 public void setPos(int x, int y) {
		 posX = x;
		 posY = y;
	 }
	public int getPosX() {   //Getter
		return posX;
	}
	public void setPosX(int posX) {  //Setter
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
}