public class SnakePart {    //Les objets de cette class constituent le serpent
	 private int posX;
	 private int posY;
	 				
	 public void setPos(int x , int y) {  //Constructeur
		 posX = x;
		 posY = y;
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