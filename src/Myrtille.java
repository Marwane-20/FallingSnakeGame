import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Myrtille extends Piege{ //Toutes ces classes qui héritent de Piege ont une image et un thype different
	 public Myrtille() throws IOException {
		 setType("myrtille");
		 setImage(ImageIO.read(new File(".\\\\images\\\\Myrtille.png")));
	 }
}