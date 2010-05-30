package graph.gui;

import graph.test.MassRandomTester;
import graph.util.Helper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.CategoryTableXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.thoughtworks.xstream.XStream;

public class RadomTestsWindow extends JFrame implements ActionListener {

	private final static String configFile = "RandomTestsConfig.xml";

	private MassRandomTester tester;

	// swing
	private Container content;
	private JLabel chartForProbability;
	private JLabel chartForVertex;
	private JLabel chartForVertexAverage;

	public RadomTestsWindow() {
		tester = (MassRandomTester) readConfigFile();
		if (tester == null)
			return;

		ArrayList<ArrayList<BigInteger>> times = tester.run();
		for (ArrayList<BigInteger> time : times) {
			for (BigInteger bigInteger : time) {
				System.out.print(bigInteger.divide(BigInteger.valueOf(1000000)) + ", ");
			}
			System.out.println("");
		}
		ArrayList<BigInteger> averageVertexTimes = tester.getAverageVertexTimes();
		for (BigInteger bigInteger2 : averageVertexTimes) {
			System.out.print(Helper.durationToMiliSecond(bigInteger2) + ", ");
		}
		System.out.println("");
		chartForVertex = createChartVertex();
		chartForProbability = createChartProbability();
		chartForVertexAverage = createChartVertexAverage();

		// adding widgtes
		content = getContentPane();
		content.setBackground(Color.white);
		content.setLayout(new BorderLayout());

		content.add(chartForVertex, BorderLayout.WEST);
		content.add(chartForProbability, BorderLayout.EAST);
		content.add(chartForVertexAverage, BorderLayout.SOUTH);

		setPreferredSize(new Dimension(810, 840));
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	private Object readConfigFile() {
		FileInputStream stream;
		try {
			FileInputStream input = new FileInputStream(configFile);
			XStream xstream = new XStream();
			return xstream.fromXML(input);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.toString());
			e.printStackTrace();
		}
		return null;
	}

	private JLabel createChartVertex() {
		JLabel chartLabel = new JLabel();

		// /wykres !!
		XYSplineRenderer splineRenderer = new XYSplineRenderer();

		NumberAxis yAxis = new NumberAxis("Time [ms]");
		yAxis.setAutoRangeIncludesZero(false);
		XYDataset xyDataset = createDateSetFoVertex();
		XYPlot subPlot = new XYPlot(xyDataset, null, yAxis, splineRenderer);
		subPlot.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);

		NumberAxis xAxis = new NumberAxis("Vertex");
		xAxis.setAutoRangeIncludesZero(false);

		CombinedDomainXYPlot mainPlot = new CombinedDomainXYPlot(xAxis);

		mainPlot.add(subPlot);
		mainPlot.setOrientation(PlotOrientation.VERTICAL);

		JFreeChart chart = new JFreeChart("Algorithm complexity", JFreeChart.DEFAULT_TITLE_FONT, mainPlot, true);

		// JFreeChart chart = ChartFactory.createXYLineChart("Algorithm
		// complexity", "Vertices", "Time [ms]", xyDataset,
		// PlotOrientation.VERTICAL, true, true, false);

		// Regresion
		double[] powerRegression = Regression.getPowerRegression(xyDataset, xyDataset.getSeriesCount() / 2);
		System.out.println(powerRegression[0] + " " + powerRegression[1]);

		BufferedImage image = chart.createBufferedImage(400, 400);
		chartLabel.setIcon(new ImageIcon(image));

		return chartLabel;
	}

	private XYDataset createDateSetFoVertex() {

		CategoryTableXYDataset xyDataset = new CategoryTableXYDataset();
		ArrayList<ArrayList<BigInteger>> averageTimes = tester.getTimes();
		int i = 0;
		for (Double probability : tester.getProbabilityList()) {
			int j = 0;
			for (Integer numVertex : tester.getNumVertexList()) {
				xyDataset.add(numVertex, Helper.durationToMiliSecond(averageTimes.get(j).get(i)), probability
						.toString());
				j++;
			}
			i++;
		}
		return xyDataset;
	}

	// private double[] computeRegresion() {
	// // Regresion
	// double[] powerRegression =
	// Regression.getPowerRegression(createDateSetFoProbability(), 1);
	// System.out.println(powerRegression[0] + " " + powerRegression[1]);
	// }

	private String printComplexity(double[] reg) {
		return reg[0] + "*" + " x " + "^" + reg[1];
	}

	private JLabel createChartProbability() {
		JLabel chartLabel = new JLabel();

		XYSplineRenderer splineRenderer = new XYSplineRenderer();

		NumberAxis yAxis = new NumberAxis("Time [ms]");
		yAxis.setAutoRangeIncludesZero(false);
		XYDataset xyDataset = createDateSetForProbability();
		XYPlot subPlot = new XYPlot(xyDataset, null, yAxis, splineRenderer);
		subPlot.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);

		NumberAxis xAxis = new NumberAxis("Probability");
		xAxis.setAutoRangeIncludesZero(false);

		CombinedDomainXYPlot mainPlot = new CombinedDomainXYPlot(xAxis);

		mainPlot.add(subPlot);
		mainPlot.setOrientation(PlotOrientation.VERTICAL);

		JFreeChart chart = new JFreeChart("Algorithm time for different probability", JFreeChart.DEFAULT_TITLE_FONT,
				mainPlot, true);

		BufferedImage image = chart.createBufferedImage(400, 400);
		chartLabel.setIcon(new ImageIcon(image));

		return chartLabel;
	}

	private XYDataset createDateSetForProbability() {

		CategoryTableXYDataset xyDataset = new CategoryTableXYDataset();
		ArrayList<ArrayList<BigInteger>> averageTimes = tester.getTimes();
		int i = 0;
		for (Double probability : tester.getProbabilityList()) {
			int j = 0;
			for (Integer numVertex : tester.getNumVertexList()) {
				xyDataset.add(probability, Helper.durationToMiliSecond(averageTimes.get(j).get(i)), numVertex
						.toString());
				j++;
			}
			i++;
		}
		return xyDataset;
	}

	private JLabel createChartVertexAverage() {
		JLabel chartLabel = new JLabel();

		// /wykres !!
		XYSplineRenderer splineRenderer = new XYSplineRenderer();

		NumberAxis yAxis = new NumberAxis("Time [ms]");
		yAxis.setAutoRangeIncludesZero(false);
		XYDataset xyDataset = createDateSetFoVertexAverage();
		XYPlot subPlot = new XYPlot(xyDataset, null, yAxis, splineRenderer);
		subPlot.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);

		NumberAxis xAxis = new NumberAxis("Vertex");
		xAxis.setAutoRangeIncludesZero(false);

		CombinedDomainXYPlot mainPlot = new CombinedDomainXYPlot(xAxis);

		mainPlot.add(subPlot);
		mainPlot.setOrientation(PlotOrientation.VERTICAL);

		JFreeChart chart = new JFreeChart("Algorithm avg complexity", JFreeChart.DEFAULT_TITLE_FONT, mainPlot, false);

		// // Regresion
		double[] powerRegression = Regression.getPowerRegression(xyDataset, 0);
		System.out.println(powerRegression[0] + " " + powerRegression[1]);
		setTitle(printComplexity(powerRegression));

		BufferedImage image = chart.createBufferedImage(400, 400);
		chartLabel.setIcon(new ImageIcon(image));

		return chartLabel;
	}

	private XYDataset createDateSetFoVertexAverage() {

		XYSeriesCollection xyDataset = new XYSeriesCollection();
		XYSeries series = new XYSeries("ble");
		int i = 0;
		for (Integer numVertex : tester.getNumVertexList()) {
			series.add(numVertex, Helper.durationToMiliSecond(tester.getAverageVertexTimes().get(i++)));
			// series.add(numVertex, Integer.valueOf(30));
		}
		xyDataset.addSeries(series);
		return xyDataset;
	}
}
