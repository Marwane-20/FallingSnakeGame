import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Piege { // Class abstraite la mère de tous les pièges
	private String type;
	private BufferedImage image;
	private Random random;
	private int posX;
	private int posY;
	private int screenWidth = Grille2D.screenWidth,
			 screenHeight = Grille2D.screenHeight,
			 unitSize = Grille2D.unitSize;
	 public Piege() {	 
		 random = new Random();
		 posX = random.nextInt(screenWidth / unitSize);   //Position X aleatoire dans la grille
		 posY = random.nextInt((screenHeight / unitSize) - 4);  //Position Y aleatoire dans la grille
	 }
	 public void setPos(int x, int y) {
		 posX = x;
		 posY = y;
	 }
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	} 
}