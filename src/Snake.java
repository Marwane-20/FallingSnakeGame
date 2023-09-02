import java.util.ArrayList;
import java.util.List;

public class Snake{        // Le snake est constitu� de sa t�te et corps (snakePart) 
	 private List<SnakePart> snakeParts;	//Le coprs du serpent et aussi sa tete à la position 0
	 private int unitSize = Grille2D.unitSize;
	 private String direction = "right";   // Direction dans la grille
	 private boolean isInvincible = false;  // S'il mange la myrtille = true
         private int InvincibilityDuration = 6000;  //Durée d'invicibilité en ms
	 
	public Snake(int length) {    // length est la taille initiale du serpent
		 snakeParts = new ArrayList<SnakePart>();
		 for(int i = 0;i < length;i++) {
			 snakeParts.add(new SnakePart());
		 }								 
	 }
	 public int getSize() {
		 return snakeParts.size();
	 }
	public List<SnakePart> getSnakeParts() {
		return snakeParts;
	}
	public void setSnakeParts(List<SnakePart> snakeParts) {
		this.snakeParts = snakeParts;
	}
	public void moveRight() {
		this.snakeParts.get(0).setPosX(this.snakeParts.get(0).getPosX() + unitSize); // la tete du snake se deplace horizontalement
	}
	public void moveLeft() {
		this.snakeParts.get(0).setPosX(this.snakeParts.get(0).getPosX() - unitSize); //Le tete du snake se decplace verticalement
	}
	public void move() {
		for (int i = this.getSnakeParts().size() - 1; i >0; i--) {
            this.getSnakeParts().get(i).setPosX(this.getSnakeParts().get(i - 1).getPosX()); //Le coprs suit la direction de la tête
            this.getSnakeParts().get(i).setPosY(this.getSnakeParts().get(i - 1).getPosY()); 
        }
	}
	public String getDirection() {		
		return direction;
	}
	public void setDirection(String string) {
		direction = string;
	}
	public boolean isInvincible() {
		return isInvincible;
	}
	public void setInvincible(boolean isInvincible) {    //Setter
		this.isInvincible = isInvincible;
	}
        public int getInvincibilityDuration() {
		return InvincibilityDuration;
	}
	public void setInvincibilityDuration(int invincibilityDuration) {
		InvincibilityDuration = invincibilityDuration;
	}
}