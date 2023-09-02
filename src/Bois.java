import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Bois extends Piege{ //Toutes ces classes qui héritent de Piege ont une image et un thype different
	 public Bois() throws IOException {
		 setType("bois");
		 setImage(ImageIO.read(new File(".\\\\images\\\\bois.png")));
	 }
}		