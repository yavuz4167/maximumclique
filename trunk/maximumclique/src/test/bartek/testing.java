package test.bartek;

import edu.uci.ics.jung.graph.Graph;
import graph.algorithm.MaximumClique;
import graph.io.UndirectedGraphReader;
import graph.test.MassRandomTester;
import graph.test.RandomTester;
import graph.util.Edge;
import graph.util.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class testing {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// simpleTest();

		// randomTest();
		massRandomTest();
	}

	private static void randomTest() {
		RandomTester randomTester = new RandomTester(30, 0.95, 10);
		double time = randomTester.run();
		System.out.println("Sredni czas to: " + time);

	}

	private static void massRandomTest() {
		ArrayList<Integer> numVerticesList = new ArrayList<Integer>();
		numVerticesList.add(20);
		numVerticesList.add(25);
		numVerticesList.add(30);
		numVerticesList.add(35);
		numVerticesList.add(40);
		ArrayList<Double> probabilityList = new ArrayList<Double>();
		// probabilityList.add(0.20);
		// probabilityList.add(0.40);
		// probabilityList.add(0.50);
		probabilityList.add(0.60);
		probabilityList.add(0.70);
		probabilityList.add(0.80);
		probabilityList.add(0.85);
		probabilityList.add(0.90);
		probabilityList.add(0.95);

		MassRandomTester massRandomTester = new MassRandomTester(numVerticesList, probabilityList, 10);
		ArrayList<ArrayList<Double>> times = massRandomTester.run();
		for (ArrayList<Double> time : times) {
			System.out.println(time);

		}
	}

	private static void simpleTest() {
		UndirectedGraphReader reader = new UndirectedGraphReader();
		Graph<Node, Edge> g1 = reader.load("graphs//example_n16_k9.txt");

		System.out.println("The graph g1 = " + g1.toString());
		MaximumClique<Node, Edge> maximumClique = new MaximumClique<Node, Edge>(g1);
		List<Set<Node>> cliques = maximumClique.getCliques();
		for (Set<Node> clique : cliques) {
			System.out.println("Nawiększa klika to: " + maximumClique.printClique(clique));
		}
	}
}
