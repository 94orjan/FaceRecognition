package no.hib.dat157.ex3;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class ImageReader {
		String filePath = " ";
		FileInputStream fileInputStream;
		DataInputStream dis;
		Scanner scan;
		int picWidth;
		int picHeight;
		int maxvalue;
		
		public double[] getGreyToneData(String filePath) throws IOException{
			
		fileInputStream = new FileInputStream(filePath);
		scan = new Scanner(fileInputStream);
		
		// Discard the magic number
		scan.nextLine();
		
		// Read pic width, height and max value
		picWidth = scan.nextInt();
		picHeight = scan.nextInt();;
		maxvalue = scan.nextInt();;
		fileInputStream.close();
		
		 // Now parse the file as binary data
		 fileInputStream = new FileInputStream(filePath);
		 dis = new DataInputStream(fileInputStream);
		 
		 // Discard the two nest lines
		 int numnewlines = 2;
		 while (numnewlines > 0) {
		     char c;
		     do {
		         c = (char)(dis.readUnsignedByte());
		     } while (c != '\n');
		     numnewlines--;
		 }
		 
		 // read the image data
		 double[] greyTone = new double[picHeight * picWidth];
		 for (int i = 0; i < greyTone.length; i++) {
		         greyTone[i] = dis.readUnsignedByte();
	}
		return greyTone;
		}

}
