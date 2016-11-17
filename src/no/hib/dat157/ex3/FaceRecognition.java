package no.hib.dat157.ex3;

import java.io.File;
import java.io.IOException;


import no.hib.dat157.ex3.ImageReader;
import no.patternsolutions.javann.Backpropagation;

public class FaceRecognition {
	
	private static ImageReader ir;

	public static void main(String[] args) throws IOException {
		
		//Prepare training data and answers for the network
		
		ir = new ImageReader();
		File trainingFaces = new File("Faces/");
		File testFaces = new File("Faces.test/");
		
		double[][] trainingData = new double[56][960];
		double[][] trainingAnswers = new double[56][4];
		
		double[][] testingData = new double [13][960];
		double[][] testingAnswers = new double[13][4];
		
		 readImagesAndAnswers(trainingData, trainingAnswers, trainingFaces);
		 readImagesAndAnswers(testingData, testingAnswers, testFaces);
		 
		 //Set up Backpropagation network.
		 Backpropagation bp = new Backpropagation(960, new int[] {100}, 4);
		 bp.setLearnRate(0.1);
		 bp.setIterations(25000);
		 bp.setMomentum(0.3);
		 bp.randomizeWeights(-1, 1);
		 bp.trainPatterns(trainingData, trainingAnswers);
		 
		 Helper.printBPSettings(bp);
		 
		 //run all known images and store the output
		 double[][] outputKnown = new double[56][4];
		 readTrainingOutput(outputKnown, trainingData, bp);
		
		 //run all unknown test images and store the output
		 double[][] outputUnknown = new double[13][4];
		 readTrainingOutput(outputUnknown, trainingData, bp);
		 
		for(int i = 0; i<13; i++){
			
			for(int j=0; j<4; j++){
				System.out.print(outputUnknown[i][j]);
				//System.out.print(testingAnswers[i][j]);
				
			}
			System.out.println();
			System.out.println();
			
		}
		 System.out.println("Testing on all known images:" + "\n");
		 PrintResults(outputKnown, trainingAnswers);
		 
		 System.out.println("Testing on 13 unknown images:" + "\n");
		 PrintResults(outputUnknown, testingAnswers);
		
		
		 }
	
	
	/*
	 * This method runs all the training data through the network
	 * and stores the output data in an array.
	 */
	public static void readTrainingOutput(double[][] output, double[][] input, Backpropagation bp ){
		 
		for(int i = 0; i < output.length; i++){
			 output[i] = bp.run(input[i]);
			 RoundArray(output[i]);
		 }
	}
	
	/*
	 * This method compares the output data from run to the correct answers
	 * and prints out the number of correct answers.
	 */
	public static void PrintResults(double[][] output, double[][] answers){
		
		int numberOfCorrectResults = 0;
		 
		 for(int i =0; i<output.length; i++){
			if(Helper.compareArrays(output[i], answers[i])){
				numberOfCorrectResults++;
				}
		 }
		 System.out.println("Number of correct Answers: " 
				 			+ numberOfCorrectResults + "\n");
	}
	
	public static void RoundArray(double[] a){
		int indexWithBiggestNumber = 0;
		
		for(int i = 1; i<a.length; i++){
			if(a[i]>a[indexWithBiggestNumber]){
				indexWithBiggestNumber = i;
			}
		}
		for(int i =0; i<a.length; i++){
			a[i]=0.00;
		}
		a[indexWithBiggestNumber]=1.00;
	}

	/*
	 * This method uses the ImageReader to store all the greytone for each 
	 * photo in one array. It also stores the direction the face is looking.
	 */
	 public static void readImagesAndAnswers(double[][] data, double[][] ans, File foldr) throws IOException {
		    int i = 0;
		    for (final File file : foldr.listFiles()) { 
		      if (!file.isHidden()) {
		        data[i] = ir.getGreyToneData(file.getPath());
		        ans[i] =  Helper.getDirection(file.getPath());
		        i++;
		      }
		    }
		  }
	
}
