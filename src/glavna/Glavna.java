package glavna;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.imageio.ImageIO;

public class Glavna {
	static int i = 0;
	static int j = 0;

	public static void main(String[] args) throws IOException {
		//Priprema setova za neuralnu mrezu
		rezi("andeo.jpg");
		rezi("cold.jpg");
		rezi("cryhappy.jpg");
		rezi("grimasa.jpg");
		rezi("happy.jpg");
		rezi("star.jpg");
		rezi("sunglasses.jpg");
		reziZaTest("novitest.jpg");
		String[] arg = new String[1];
		arg[0]="command.txt";
		Image.main(arg);
		

	}
	
	//funkcija za rezanje kontinuirane slike u više komada
	public static void rezi(String naziv) throws IOException {
		File file = new File(naziv);
		FileInputStream fis = new FileInputStream(file);
		BufferedImage image = ImageIO.read(fis);
		int rows = 1;
		int cols = 7;
		int chunks = rows * cols;
		int chunkWidth = image.getWidth() / cols;
		int chunkHeight = image.getHeight() / rows;
		int count = 0;
		BufferedImage imgs[] = new BufferedImage[chunks];
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
				Graphics2D gr = imgs[count++].createGraphics();
				gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x,
						chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
				gr.dispose();
			}
		}
		System.out.println("Rezanje slike je završeno.");
		for (i = 0; i < imgs.length; i++) {
			ImageIO.write(imgs[i], "jpg", new File("slika" + (7 * j + i) + ".jpg"));

		}
		j++;
		System.out.println("Kreiranje slika je završeno.");
	}
	public static void reziZaTest(String naziv) throws IOException {
		File file = new File(naziv);
		FileInputStream fis = new FileInputStream(file);
		BufferedImage image = ImageIO.read(fis);
		int rows = 1;
		int cols = image.getWidth()/108;
		int chunks = rows * cols;
		int chunkWidth = image.getWidth() / cols;
		int chunkHeight = image.getHeight() / rows;
		int count = 0;
		BufferedImage imgs[] = new BufferedImage[chunks];
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
				Graphics2D gr = imgs[count++].createGraphics();
				gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x,
						chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
				gr.dispose();
			}
		}
		System.out.println("Rezanje slike je završeno.");
		for (i = 0; i < imgs.length; i++) {
			ImageIO.write(imgs[i], "jpg", new File("slika" + (cols * j + i) + ".jpg"));
			try(FileWriter fw = new FileWriter("command.txt", true);
				    BufferedWriter bw = new BufferedWriter(fw);
				    PrintWriter out = new PrintWriter(bw))
				{
				    out.println("Whatis: image:slika" + (cols * j + i) + ".jpg");
				} catch (IOException e) {
				    //prazan exception handling TODO
				}

		}
		j++;
		System.out.println("Kreiranje slika je završeno.");
	}

}
