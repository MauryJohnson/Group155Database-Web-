package QHandle;
import java.awt.*; 

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.*; 
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.urls.*; 
import org.jfree.data.category.*; 
import org.jfree.data.general.*;

/**
 * Create Bar Graph object. Constructed
 * with 2d array, each row is within bar group
 * each column is new bar group
 * data is comprised of doubles
 * @author Maury Johnson
 *
 */
public class Graph {

	//public static String Stage;
	public static JFreeChart BarChart;
	public static LinkedList<String> Files = new LinkedList<String>();

	public static LinkedList<String> Keys;
	
	public static int Count = 0;
	
	public static int WIDTH = 5000;
	public static int HEIGHT = 5000;
	
	public Graph() {

		
	}
	
	//Data, Title!!, XLabel -> Category, YLabel -> scorel;
	public static void BG(double[][] Data,String chartTitle,String Category,String XLabel, String YLabel,LinkedList<String> XCategories,LinkedList<String> XGroupsLabels,int Type) {
		try {
		BarChart = ChartFactory.createBarChart(chartTitle, Category, YLabel,createDataset(Data,XCategories,XGroupsLabels,Type),PlotOrientation.VERTICAL,true,true,false);	
				//Save BarChart
			  final ChartRenderingInfo info = new ChartRenderingInfo
			   (new StandardEntityCollection());
			
			  
			  Keys = null;
			  
			  Count+=1;
			  
			  final File file1 = new File(chartTitle+""+Count+".png");
			  
			  file1.deleteOnExit();
			  
			  do {
			  Files.add(file1.getAbsolutePath());
			  }while(!Files.contains(file1.getAbsolutePath()));
			  
			  System.out.printf("Added new file:%s", file1.getAbsolutePath());
			  ChartUtilities.saveChartAsPNG(file1, BarChart, WIDTH, HEIGHT, info);
			  } catch (Exception e) {
				 System.out.println(e);
			  }
	}
	
	//Convert important data from resultSet
	public static double[][] ConvertData(ResultSet rs,ResultSetMetaData rsmd, LinkedList<String> XCategory, LinkedList<String> XGroupsLabels, int type){
		ArrayList<double[]>D = new ArrayList<double[]>();
		//Convert for type 0, gather beers count
		if(type==0) {
			//Stage="../eclipse-workspace/Group155Database/Drinker1.png";
			//XCategory : BeerNames
			//XGroups : BeerNames
			Hashtable<String,double[]> H = new Hashtable<String,double[]>();
			LinkedList<String>Keys=new LinkedList<String>();
			
			//Value (Frequency!)
			try {
				int BCount = 0;
				String entry = "";
				while(rs.next()) {
					int i=1;
					int len = rsmd.getColumnCount();
					boolean Added = false;
					while(i<=len) {
						//New push
						if(H.get(rs.getString(i))==null){
							H.put(rs.getString(i),new double[] {1});
							Keys.add(rs.getString(i));
						}
						//Old push, increase occurence count
						else {
							H.get(rs.getString(i))[0]+=1;
						}
						i+=1;
					}
					
					System.out.println();
				}
				//Iterate through HTable, store labels, group names, and occurrences
				
				//Set values to be used for coloring
				SetDXG(D,H,Keys,XCategory,XGroupsLabels,0);
			
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//System.out.println("FAILED");
				e.printStackTrace();
			}
			
		}
		//String1,...,String3,Value,String5
		if(type==1) {
			//Stage="../eclipse-workspace/Group155Database/Drinker1.png";
			//XCategory : BeerNames
			//XGroups : BeerNames
			Hashtable<String,double[]> H = new Hashtable<String,double[]>();
			LinkedList<String>Keys=new LinkedList<String>();
			
			try {
				while(rs.next()) {
						if(H.get(rs.getString(1))==null){
							H.put(ExpandRS(rs,new int[] {1,2,3,5}),new double[] {Double.parseDouble(rs.getString(4))});
							Keys.add(ExpandRS(rs,new int[] {1,2,3,5}));
						}
						//Old push, increase occurence count, shouldn't happen with Bar,cons,drinker,day->price....
						else {
							H.get(ExpandRS(rs,new int[] {1,2,3,5}));
						}
				}
				
				//Set values to be used for coloring
				SetDXG(D,H,Keys,XCategory,XGroupsLabels,1);
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//String,String,Value
		if(type==2||type==4) {
			
			Hashtable<String,double[]> H = new Hashtable<String,double[]>();
			LinkedList<String>Keys=new LinkedList<String>();
			
			try {
				while(rs.next()) {
						if(H.get(rs.getString(1))==null){
							H.put(ExpandRS(rs,new int[] {1,2}),new double[] {Double.parseDouble(rs.getString(3))});
							Keys.add(ExpandRS(rs,new int[] {1,2}));
						}
						//Old push, increase occurence count, shouldn't happen with Bar,cons,drinker,day->price....
						else {
							H.get(ExpandRS(rs,new int[] {1,2}));
						}
				}
				
				//Set values to be used for coloring
				SetDXG(D,H,Keys,XCategory,XGroupsLabels,5);
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(type==3) {
			Hashtable<String,double[]> H = new Hashtable<String,double[]>();
			LinkedList<String>Keys=new LinkedList<String>();
			
			try {
				while(rs.next()) {
						if(H.get(rs.getString(1))==null){
							H.put(ExpandRS(rs,new int[] {1,2,4}),new double[] {Double.parseDouble(rs.getString(3))});
							Keys.add(ExpandRS(rs,new int[] {1,2,4}));
						}
						//Old push, increase occurence count, shouldn't happen with Bar,cons,drinker,day->price....
						else {
							H.get(ExpandRS(rs,new int[] {1,2,4}));
						}
				}
				
				//Set values to be used for coloring
				SetDXG(D,H,Keys,XCategory,XGroupsLabels,5);
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(type==5) {
			Hashtable<String,double[]> H = new Hashtable<String,double[]>();
			LinkedList<String>Keys=new LinkedList<String>();
			
			try {
				while(rs.next()) {
						if(H.get(rs.getString(1))==null){
							H.put(ExpandRS(rs,new int[] {1,2}),new double[] {Double.parseDouble(rs.getString(3))});
							Keys.add(ExpandRS(rs,new int[] {1,2}));
						}
						//Old push, increase occurence count, shouldn't happen with Bar,cons,drinker,day->price....
						else {
							H.get(ExpandRS(rs,new int[] {1,2}));
						}
				}
				
				//Set values to be used for coloring
				SetDXG(D,H,Keys,XCategory,XGroupsLabels,4);
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(type==6) {
			Hashtable<String,double[]> H = new Hashtable<String,double[]>();
			LinkedList<String>Keys=new LinkedList<String>();
			
			try {
				while(rs.next()) {
						if(H.get(rs.getString(1)+"\n")==null){
							H.put(ExpandRS(rs,new int[] {1}),new double[] {Double.parseDouble(rs.getString(2))});
							Keys.add(ExpandRS(rs,new int[] {1}));
						}
						//Old push, increase occurence count, shouldn't happen with Bar,cons,drinker,day->price....
						else {
							if((Double.parseDouble(rs.getString(2))>H.get(rs.getString(1)+"\n")[0])){
							H.get(ExpandRS(rs,new int[] {1}));
							H.put(rs.getString(1)+"\n", new double[] {Double.parseDouble(rs.getString(2))});
							}
						}
				}
				
				//Set values to be used for coloring
				SetDXG(D,H,Keys,XCategory,XGroupsLabels,4);
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ToD(D);
	}
	//Create new line expansion for result set row values
	//Give all indices to expand Result set on!!!
	private static String ExpandRS(ResultSet rs, int[] Indices) {
		// TODO Auto-generated method stub
		String App = "";
		for(int k=0;k<Indices.length;k+=1) {
			try {
				App+=rs.getString(Indices[k]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(k<Indices.length)
			App+="\n";
		}
		return App;
	}

	private static void SetDXG(ArrayList<double[]> D, Hashtable<String, double[]> H, LinkedList<String> Keys,
			LinkedList<String> XCategory, LinkedList<String> XGroupsLabels,int Type) {
		// TODO Auto-generated method stub
		String Key;
		int i=0;
		
		Graph.Keys = Keys;
		
		while(i<Keys.size()) {
			Key = Keys.get(i);
			System.out.printf("KEY: %s\n",Key);
			if(H.get(Key)!=null) {
			System.out.printf("Key Unlocked:");
			System.out.println(H.get(Key)[0]);
			D.add(new double[] {H.get(Key)[0]});
			
			if(Type<5) {
			XCategory.add(Key);
			XGroupsLabels.add(Key);
			}
			else{
			XCategory.add(Key.replaceAll("\n", ","));
			XGroupsLabels.add(Key);
			}
			
			}
			i+=1;
		}
	}

	private static double[][] ToD(ArrayList<double[]>D){
		double[][] d = new double[D.size()][D.get(0).length];
		for(int i=0; i<D.size();i+=1) {
			for(int j=0; j<D.get(i).length;j+=1) {
				d[i][j] = D.get(i)[j];
			}
		}
		return d;
	}
	
	/**
	 * 
	 * Create category dataset 
	 * 
	 * Data is 2d array, 
	 * each row is part of XGroupLabels[i], 
	 * each column is new XCategory
	 * 
	 * @param data2
	 * @param xCategories
	 * @param xGroupsLabels
	 * @return
	 */
	private static CategoryDataset createDataset(double[][] Data, LinkedList<String> XCategories,LinkedList<String> XGroupsLabels,int Type) {
		// TODO Auto-generated method stub
		
		System.out.println("Create Dataset");
		
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	
		int Columns = 0;
		if(Data.length>0) {
			Columns = Data[0].length;
		}
		
		//Iterate rows then columns
		
		//Add order, Data, XGroup, XCategory
		//for(int i=0;i<Columns;i+=1) {
			//Iterate through 2d array
			//for(int u=0; u<Data.length;u+=1) {
				//For each group ex: bar 1, bar 2,....
		
				//System.out.printf("Group Labels Size:%d X Categories Size:%d",XGroupsLabels.size(),XCategories.size());
			
				for(int j=0; j<XGroupsLabels.size()&&j<Data.length;j+=1) {
				//For each category, ex beer,food,soft_d
				for(int k=0; k<XCategories.size()&&k<Data[j].length;k+=1) {
					//if(Type==0) {
					if(Type==1) {
						System.out.printf("XCat: %s\n", XCategories.get(k));
						dataset.addValue(Data[j][k], Graph.Keys.get(j).split("\n")[0]+" , "+Graph.Keys.get(j).split("\n")[1]+" , "+Graph.Keys.get(j).split("\n")[2]+" , "+Graph.Keys.get(j).split("\n")[3], XCategories.get(k).split("\n")[3] + "-" + Graph.Keys.get(Graph.Keys.size()-1).split("\n")[3]);	
					}
					else if(Type<2||Type==5) {
					dataset.addValue(Data[j][k], XGroupsLabels.get(j), XCategories.get(k).split("\n")[0] + "-" + Graph.Keys.get(Graph.Keys.size()-1).split("\n")[0]);
					}
					else if(Type<3){
						System.out.printf("XCat: %s\n", XCategories.get(k));
						dataset.addValue(Data[j][k], Graph.Keys.get(j).split("\n")[1], XCategories.get(k).split(",")[0]);	
					}
					else if(Type==4) {
						System.out.printf("XCat: %s\n", XCategories.get(k));
						dataset.addValue(Data[j][k], Graph.Keys.get(j).split("\n")[0]+","+Graph.Keys.get(j).split("\n")[1], XCategories.get(k).split(",")[0] + "-" + Graph.Keys.get(Graph.Keys.size()-1).split("\n")[0]);	
					}
					else if(Type==6) {
						System.out.printf("XCat: %s\n", XCategories.get(k));
						//dataset.addValue(Data[j][k], Graph.Keys.get(j).split("\n")[0], XCategories.get(k).split(",")[0] + "-" + Graph.Keys.get(Graph.Keys.size()-1).split("\n")[0]);	
						dataset.addValue(Data[j][k], Graph.Keys.get(j)+"\n", XCategories.get(k).split("\n")[0] + "-" + Graph.Keys.get(Graph.Keys.size()-1).split("\n")[0]);
					}
					else {
						System.out.printf("XCat: %s\n", XCategories.get(k));
						dataset.addValue(Data[j][k], Graph.Keys.get(j).split("\n")[2], XCategories.get(k).split(",")[0]);	
					}
					//}
					/*
					else if(Type==1) {
					dataset.addValue(Data[j][k], XGroupsLabels.get(j), XCategories.get(k));
					}
					*/
					}
				}
			//}
		//}
		
		return dataset;
	}
	
}
