import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fraise extends Piege{ //Toutes ces classes qui héritent de Piege ont une image et un thype different
	 public Fraise() throws IOException {
		 setType("fraise");
		 setImage(ImageIO.read(new File(".\\\\images\\\\fraise.png")));
	 }
}