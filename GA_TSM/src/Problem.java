/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 * @author Aliazar
 *
 */
public class Problem { 
	
	protected static String name = "TCM";

/*
	protected static int dimension = 6;

	     protected static int[] costs = {35,52,54,34,53,51}; // Fitness function value

    protected static int[][] constraints = {
    	{1,1,0,0,0,0},     //1 tc2
    	{1,1,0,0,0,1},     //1  tc2
    	{0,0,1,1,1,0},     //1  tc4
        {0,0,1,1,0,0},     //1 tc4
    	{0,0,0,1,1,1},     //1 tc4
    	{0,1,0,0,1,1}      //1 tc1
    };
    */

/*	protected static int dimension = 9;
	//protected static int[] costs = {44,43,27,61,40,74,63,22,11};
	//protected static int[] costs = {1,1,1,1,1,1,1,1,1};
	protected static int[] costs = {13,11,8,19,15,20,20,6,4};
	protected static int[][] constraints = {
			{1,	1,	0,	1,	0,	0,	1,	0,	0},
			{1,	1,	0,	1,	0,	0,	1,	0,	0},
			{1,	0,	0,	1,	1,	1,	1,	0,	0},
			{0,	1,	0,	1,	0,	1,	1,	0,	0},
			{1,	1,	0,	1,	1,	1,	1,	1,	0},
			{1,	0,	1,	1,	0,	1,	1,	1,	0},
			{0,	0,	0,	1,	1,	1,	1,	0,	0},
			{0,	0,	1,	1,	1,	0,	1,	0,	0},
			{0,	0,	0,	1,	0,	1,	1,	0,	0},
			{1,	1,	0,	1,	1,	1,	1,	0,	0},
			{0,	1,	0,	1,	0,	1,	1,	0,	0},
			{0,	1,	0,	0,	0,	1,	1,	0,	0},
			{1,	0,	0,	0,	0,	1,	0,	0,	0},
			{0,	0,	0,	0,	0,	1,	0,	1,	0},
			{0,	0,	0,	0,	1,	1,	0,	1,	0},
			{0,	0,	0,	0,	1,	1,	0,	0,	0},
			{0,	0,	0,	0,	1,	1,	0,	0,	0},
			{0,	0,	1,	0,	0,	0,	0,	0,	0},
			{0,	0,	1,	0,	0,	0,	0,	0,	1},
			{0,	0,	0,	0,	0,	0,	0,	0,	1}


	};*/

	protected static int optimal = 100;

	protected static int dimension;
	protected static int[] costs;
	protected static int[][] constraints;


	static String filename ="C:\\Users\\Aliaz\\Documents\\TSM_Thesis_Experiment\\GA_TSM\\src\\XML-Security.csv";
	//static String filename ="C:\\Users\\Aliaz\\Documents\\TSM_Thesis_Experiment\\GA_TSM\\src\\jmeter.csv";
	//static String filename ="C:\\Users\\Aliaz\\Documents\\TSM_Thesis_Experiment\\GA_TSM\\src\\jacoco.csv";
	//static String filename ="C:\\Users\\Aliaz\\Documents\\TSM_Thesis_Experiment\\GA_TSM\\src\\SARNM.csv";
	public static void main123() throws Exception {
		String[][] matrix=readFile(filename);
		double [][] doubleMatrix=new double[matrix.length][matrix[0].length];
		int [][] xmlMatrix=new int[matrix.length][matrix[0].length];

		dimension=xmlMatrix.length;

		costs = new int [dimension];

		for (int i=0;i<matrix.length;i++){
			for (int k=0;k<matrix[0].length;k++)
			{
				doubleMatrix[i][k]=Double.parseDouble(matrix[i][k]);
				xmlMatrix[i][k]=(int)(doubleMatrix[i][k]);
				System.out.print(" "+xmlMatrix[i][k]);
			}

			double[] TestcaseFit=Objective_Function.iPOP_Fit(xmlMatrix[i]);
               costs[i]= (int)(TestcaseFit[0]);
			System.out.println("       NO 1 "+TestcaseFit[2]+" Cost"+TestcaseFit[0]
					+"    No 0 ="+TestcaseFit[3]+"     Sum "+(TestcaseFit[2]+TestcaseFit[3])+"     total"+TestcaseFit[4]);
		}
		constraints = transposeMatrix(xmlMatrix);
		//System.out.println("XML Security data set Number of columns ="+xmlMatrix[0].length+" Rows = "+xmlMatrix.length);
	}

	private static String[][] readFile(String filename) throws Exception {
		try (CSVReader reader = new CSVReader(new BufferedReader(
				new FileReader(filename)));) {

			List<String[]> lines = reader.readAll();
			return lines.toArray(new String[lines.size()][]);
		}

	}
	public static int[][] transposeMatrix(int[][] arr) {
		int[][] transposed = new int[arr[0].length][arr.length];
		IntStream.range(0, arr.length)
				.forEach(i -> IntStream.range(0, arr[0].length)
						.forEach(j -> transposed[j][i] = arr[i][j]));
		return transposed;
	}
}