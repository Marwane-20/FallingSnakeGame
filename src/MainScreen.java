import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MainScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static MainScreen frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MainScreen();
					frame.pack();
					frame.setVisible(true);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public MainScreen() throws IOException {
		try {
			  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch(Exception e) {
			  System.out.println("IU personnalisée introuvable dans le système d'exploitation: " + e);
			}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BufferedImage image = ImageIO.read(new File(".\\\\images\\\\FallingSnakes.PNG"));
		setBounds(100, 100, 1030, 640);
		contentPane = new JPanel() {
			 /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			    protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        g.drawImage(image, 0, 0, null);
			    }
		};	
		contentPane.setBackground(Color.RED);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setPreferredSize(new Dimension(1030, 640));
		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton(" EASY  (\u3063 \u00B0\u0414 \u00B0;)\u3063");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FallingSnakes("easy");
				frame.dispose();
				frame = null;
			}
		});
		
		JLabel lblNewLabel = new JLabel("Difficult\u00E9");
		lblNewLabel.setBackground(Color.BLUE);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Verdana Pro Light", Font.BOLD, 24));
		lblNewLabel.setBounds(469, 457, 115, 38);
		contentPane.add(lblNewLabel);
		btnNewButton.setBounds(122, 531, 190, 29);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton(" NORMAL  ^____^");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FallingSnakes("normal");
			}
		});
		btnNewButton_1.setBounds(431, 531, 190, 29);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("HARD   (\u2310\u25A0_\u25A0)");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FallingSnakes("hard");
			}
		});
		btnNewButton_2.setBounds(723, 531, 190, 29);
		contentPane.add(btnNewButton_2);
	}
}
