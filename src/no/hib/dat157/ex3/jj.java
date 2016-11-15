package no.hib.dat157.ex3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.hib.dat157.ex3.ImageReader;
import no.patternsolutions.javann.Backpropagation;

public class jj {
	private static ImageReader ir;

	public static void main(String[] args) throws IOException {
		
		ir = new ImageReader();
		File faces = new File("Faces/");
		
		double[][] trainingData = new double[56][960];
		double[][] trainingOutput = new double[56][4];
		
		 readInFilesAndAnswers(trainingData, trainingOutput, faces);
		 Backpropagation bp = new Backpropagation(960, new int[] {90}, 4);
		 bp.setLearnRate(0.2);
		 bp.setIterations(5000);
		 bp.setMomentum(0.7);
		 bp.randomizeWeights(-1, 1);
		 bp.trainPatterns(trainingData, trainingOutput);
		 
		 double[] result = bp.run(trainingData[7]);
	
		 for(int i = 0;  i < 4; i++) {
			 if(result[i] >= 0.5) {				 
				 System.out.print(1);			 
			 } else {
				 System.out.print(0);
			 }
		 }
	}

	
	 public static void readInFilesAndAnswers(double[][] pics, double[][] ans, File foldr) throws IOException {
		    int i = 0;
		    for (final File file : foldr.listFiles()) {
		      if (!file.isHidden()) {
		        pics[i] = ir.greyToneData(file.getPath());
		        ans[i] =  answer(file.getPath());
		        i++;
		      }
		    }
		  }
	
	public static double[] answer(String path){
		double[] answer = new double[4];
		if(path.contains("left")){
			answer[0]=1;
		}else if(path.contains("right")){
			answer[1]=1;
		}else if(path.contains("straight")){
			answer[2]=1;
		}else{
			answer[3]=1;
		}
		return answer;
	}
	
	
}
