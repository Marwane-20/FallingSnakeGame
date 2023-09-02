import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Or extends Piege{ //Toutes ces classes qui héritent de Piege ont une image et un thype different
	 public Or() throws IOException {
		 setType("or");
		 setImage(ImageIO.read(new File(".\\\\images\\\\Gold.png")));
	 }
}