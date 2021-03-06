package util;
/*
 * Helper class to load in images for the game, view those images, generate a map for the game,
 * and test out how to display the graphics
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import objects.GameObject;

public class Textures {
	public static BufferedImage[] grass;	//Grass images
	public static BufferedImage[] trees;	//Tree images
	public static BufferedImage Map;		//Array of images making up the map
	public static BufferedImage[][][] vlad;	//Animation images for the player
	public static BufferedImage[][][] skell;
	
	private static int RGBfilterTrees = -11836545;		//Integer value of color to filter
	private static int RGBfilterMobs = -10599895;
	private static int mapSize = 100;
	private static int shiftX = 0;
	private static int shiftY = 0;
	
	public static void doAll() {
		try {
			loadGrass();
			loadTrees();
			loadVlad();
			loadSkell();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		generateMap();
	}
	
	/*
	 *Generates the map for the game. 
	 * Will only be used once when finished and
	 * it will be outputed to a file
	*/
	public static void generateMap() {
		
		Map = new BufferedImage(40*mapSize, 40*mapSize, BufferedImage.TYPE_4BYTE_ABGR);
		
		BufferedImage[][] tiles = new BufferedImage[mapSize][];
		for (int i=0; i<tiles.length; i++) {
			tiles[i] = new BufferedImage[mapSize];
			for (int j=0; j<tiles[i].length; j++) {
				if (i == j)
					tiles[i][j] = grass[3];
				else
					tiles[i][j] = grass[3];
			}
		}
		
		for (int i=0; i<tiles.length; i++) {
			for (int j=0; j<tiles[i].length; j++) {
				Map.getGraphics().drawImage(tiles[i][j], i*40, j*40, null);
			}
		}
		
		//GameObject[] T = new GameObject[C.size()];
		//mapViewer(C.toArray(T));
	}
	
	//Loads in the tree tiles
	public static void loadTrees() throws IOException {
		BufferedImage img = ImageIO.read(new File("images/map/TREE.GIF"));
		
		trees = new BufferedImage[1];
		
		trees[0] = img.getSubimage(15*8, 20*8-1, 10*8, 15*8);
		trees[0] = toBufferedImage(makeColorTransparent(trees[0], Color.getColor(null, RGBfilterTrees)));
	}

	//Loads in the grass tiles
 	public static void loadGrass() throws IOException{
		
		//imageViewer("images/map/GRS2ROC.bmp");
		BufferedImage img = ImageIO.read(new File("images/map/GRS2ROC.bmp"));
		
		grass = new BufferedImage[15];
		
		int w = 5*8;
		int h = w;
		
		grass[0] = img.getSubimage(5*8, 10*8+1, w, h);	//Top Left Corner
		grass[1] = img.getSubimage(15*8, 10*8+1, w, h);	//Top border
		grass[2] = img.getSubimage(25*8, 10*8+1, w, h);	//Top Right Corner
		grass[3] = img.getSubimage(35*8, 10*8+1, w, h);	//Grass
		grass[4] = img.getSubimage(5*8, 20*8+1, w, h);	//Left border
		grass[5] = img.getSubimage(15*8, 20*8+1, w, h);	//Stone
		grass[6] = img.getSubimage(25*8, 20*8+1, w, h);	//Right border
		grass[7] = img.getSubimage(35*8, 20*8+1, w, h);	//Top Left big
		grass[8] = img.getSubimage(45*8, 20*8+1, w, h);	//Top Right big
		grass[9] = img.getSubimage(5*8, 30*8+1, w, h);	//bottom left
		grass[10] = img.getSubimage(15*8, 30*8+1, w, h);	//bottom border
		grass[11] = img.getSubimage(25*8, 30*8+1, w, h);	//bottom right
		grass[12] = img.getSubimage(35*8, 30*8+1, w, h);	//bottom left big
		grass[13] = img.getSubimage(45*8, 30*8+1, w, h);	//bottom right big
		
	}
 	
 	public static void loadVlad() throws IOException {
 		/*
 		 * 0 = walk images 
 		 * 1 = attack images
 		 * 2 = hit images
 		 * 
 		 * For each of those, there are eight different directions
 		 * the character can face. For each direction, there are 
 		 * eight different animation images
 		 */
 		
 		vlad = new BufferedImage[3][8][];
 		
 		String path = "images/vlad/vlad sword/";
 		String[] types = { "walking ", "attack ",  "been hit " };
 		String[] direction = { "e", "n", "ne", "nw", "s", "se", "sw", "w"};
 		
 		int k=0;
 		for (BufferedImage[][] B : vlad) {
 			for (int i=0; i<B.length; i++) {
 				if (k == 1)
 					B[i] = new BufferedImage[12];
 				else
 					B[i] = new BufferedImage[8];
 				
 				for (int j=0; j<B[i].length; j++) {
 					B[i][j] = ImageIO.read(new File(path + types[k] + direction[i] + String.format("%04d", j) + ".bmp"));
 					B[i][j] = toBufferedImage(makeColorTransparent(B[i][j], Color.getColor(null, RGBfilterMobs)));
 				}
 			}
 			k++;
 		}
 	}
 	
 	public static void loadSkell() throws IOException {
 		/*
 		 * 0 = sprint images 
 		 * 1 = attack images
 		 * 2 = standing
 		 * 
 		 * For each of those, there are eight different directions
 		 * the character can face. For each direction, there are 
 		 * eight different animation images
 		 */
 		
 		skell = new BufferedImage[3][8][];
 		
 		String path = "images/skelly/swordskel bitmaps/swordskel ";
 		String[] types = { "rennt ", "attack ", "steht" };
 		String[] direction = { "n", "ne", "nw", "s", "se", "sw","e", "w"};
 		
 		int k=0;
 		for (BufferedImage[][] B : skell) {
 			for (int i=0; i<B.length; i++) {
 				if (k == 0)
 					B[i] = new BufferedImage[9];
 				else if (k == 1)
 					B[i] = new BufferedImage[10];
 				else
 					B[i] = new BufferedImage[1];
 				
 				for (int j=0; j<B[i].length; j++) {
 					B[i][j] = ImageIO.read(new File(path + types[k] + (k!=2?direction[i]:"") + String.format("%04d", j) + ".bmp"));
 					B[i][j] = toBufferedImage(makeColorTransparent(B[i][j], Color.getColor(null, B[i][j].getRGB(0, 0))));
 				}
 			}
 			k++;
 		}
 	}
 	
 	//Helper functions to view imported images and manipulate them
	public static void imageViewer(String s) throws IOException {
		final BufferedImage img = ImageIO.read(new File(s));
		
		
		JFrame j = new JFrame();
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = j.getContentPane();
		
		JPanel p = new JPanel() {
		
			protected void paintComponent(Graphics g) {
				
				int scale = 8;
				g.drawImage(img, 0, 0, Color.BLUE, null);
				
				for (int i=0; i<img.getWidth()/scale; i++) {
					g.drawLine(i*scale,0, i*scale, img.getHeight());
					
				}
				
				for (int i=0; i<img.getHeight()/scale; i++) {
					g.drawLine( 0, i*scale, img.getWidth(), i*scale);
				}
			}
		};
		
		p.setPreferredSize(new Dimension(img.getWidth(),img.getHeight()));
		c.add(p);
		j.pack();
		j.setVisible(true);
	}
	
	public static void imageViewer(final BufferedImage img){		
		
		JFrame j = new JFrame();
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = j.getContentPane();
		JPanel p = new JPanel() {
			protected void paintComponent(Graphics g) {
				
				int scale = 8;
				g.drawImage(img, 0, 0, Color.RED, null);
			}
		};
		
		p.setPreferredSize(new Dimension(img.getWidth(),img.getHeight()));
		c.add(p);
		j.pack();
		j.setVisible(true);
	}
	
	public static void imageViewer(final BufferedImage[][] img){		
		
		
		if (img == null) {
			System.out.println("Null image");
			return;
		}
		
		JFrame j = new JFrame();
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		int width = img[0][0].getWidth()*img.length;
		int height = width;
		
		Container c = j.getContentPane();
		
		JPanel p = new JPanel() {
			protected void paintComponent(Graphics g) {
				
				
				for (int i=0; i<img.length; i++) {
					for (int j=0; j<img[i].length; j++) {
						g.drawImage(img[i][j], i*8*5, j*8*5, Color.red, null);
					}
				}
				
			
			}
		};
		
		p.setPreferredSize(new Dimension(width,height));
		c.add(p);
		j.pack();
		j.setVisible(true);
	}

	public static void mapViewer(final GameObject[] obj){		
		
		final JFrame j = new JFrame();
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		final int width = 600;
		final int height = 600;
		
		Container c = j.getContentPane();
		
		JPanel p = new JPanel() {
			public final int buff = 125;
			//private final BufferedImage buffI = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
			
			protected void paintComponent(Graphics g) {
				//Graphics2D bg = (Graphics2D) buffI.getGraphics();
				
				/*
				//Draws the ground tiles
				for (int i=0; i<width/(40); i++) {
					for (int j=0; j<height/(40); j++) {
						if (shiftX < 0 && startX > 0)
							bg.drawImage(img[startX-1 + i][startY+ j], i*40+shiftX, j*40+shiftY, Color.red, null);
						if (shiftY < 0 && startY > 0)
							bg.drawImage(img[startX + i][startY-1 + j], i*40+shiftX, j*40+shiftY, Color.red, null);
						
							bg.drawImage(img[startX + i][startY+ j], i*40+shiftX, j*40+shiftY, Color.red, null);
					}
				}
				*/
				
				g.drawImage(Map.getSubimage(shiftX, shiftY, width, height),0,0,null);
				//Draws game objects, checks if they are in the screen view 
				for (GameObject G : obj) {
					int X = (int)G.getBounds().x;
					int Y = (int)G.getBounds().y;
					
					if (X >= shiftX-buff && X <= shiftX+width+buff && Y >= shiftY-buff && Y <= shiftY+height+buff)
						g.drawImage(G.getTexture(), X-shiftX, Y-shiftY, null);
				}
				
			
				
				g.setColor(Color.RED);
				g.drawString("X = " + shiftX + " Y = " +shiftY , 10, 10);
				
			}
		};
		
		p.setPreferredSize(new Dimension(width,height));
		c.add(p);
		
		//Key listener to move around map
		j.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
			
				System.out.println(arg0.getKeyChar());
				switch (arg0.getKeyChar()) {
				
				case 'w':
					shiftY -= 10;
					break;
				case 'a':
					shiftX -= 10;
					break;
				case 's':
					shiftY += 10;
					break;
				case 'd':
					shiftX += 10;
					break;
				}
				
				if (shiftX < 0)
					shiftX = 0;
				if (shiftY < 0)
					shiftY = 0;
				
				j.repaint();
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		j.setPreferredSize(new Dimension(width-50,height-50));
		j.pack();
		j.setVisible(true);
	}

	public static Image makeColorTransparent (Image im, final Color color) {
 
		ImageFilter filter = new RGBImageFilter() {
			public int markerRGB = color.getRGB() | 0xFF000000;
   
			public final int filterRGB(int x, int y, int rgb) {
		                     
				if ( ( rgb | 0xFF000000 ) == markerRGB ) {
		                                        
					return 0x00FFFFFF & rgb;
		                                               
				}
		                        
				else {
		                        
					// nothing to do
		                       
					return rgb;
		                     
				}
		                     
			}
		                   
		};

		ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
	    return Toolkit.getDefaultToolkit().createImage(ip);
	
	    
	}
	
	public static BufferedImage toBufferedImage(Image img)	{
	    
    	if (img instanceof BufferedImage) {
	        return (BufferedImage) img;
    	}
		     
    	BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

    	Graphics2D bGr = bimage.createGraphics();
			             
    	bGr.drawImage(img, 0, 0, null);
    	bGr.dispose();
    	
    	return bimage;
			                             
    }
	    
	
}
	
