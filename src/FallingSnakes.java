import java.io.IOException;

import javax.swing.JFrame;

public class FallingSnakes extends JFrame{	//La JFrame qui ajoute le JPanel Grille2D
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public FallingSnakes(String difficulty) throws IOException{
			this.add(new Grille2D(difficulty));
			this.setTitle("Snake");
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setResizable(false);
	        this.pack();
	        this.setVisible(true);
	        this.setLocationRelativeTo(null);	        
		}
			
	}