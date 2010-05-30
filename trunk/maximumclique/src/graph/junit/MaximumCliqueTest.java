package graph.junit;
import java.util.List;
import java.util.Set;

import edu.uci.ics.jung.graph.Graph;
import graph.algorithm.MaximumClique;
import graph.io.UndirectedGraphReader;
import graph.util.Edge;
import graph.util.Node;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MaximumCliqueTest extends TestCase {
	UndirectedGraphReader reader;

	/**
	 * Sets up the test fixture. (Called before every test case method.)
	 */
	public void setUp() {
		reader = new UndirectedGraphReader();
	}

	/**
	 * Tears down the test fixture. (Called after every test case method.)
	 */
	protected void tearDown() {
		reader = null;
	}

	public void testExample1() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n10_k4.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(4, maximumCliques.get(0).size());
	}
	
	public void testExample2() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n11_k4.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(4, maximumCliques.get(0).size());
	}
	
	
	public void testExample3() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n11_k5.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(5, maximumCliques.get(0).size());
	}
	
	public void testExample4() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n11_k6.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(6, maximumCliques.get(0).size());
	}
	
	public void testExample5() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n12_k3.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(3, maximumCliques.get(0).size());
	}
	
	public void testExample6() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n14_k7.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(7, maximumCliques.get(0).size());
	}
	
	public void testExample7() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n16_k9.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(9, maximumCliques.get(0).size());
	}
	
	
	public void testExample8() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n17_k3.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(3, maximumCliques.get(0).size());
	}
	
	public void testExample9() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n20_k10.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(10, maximumCliques.get(0).size());
	}
	
	public void testExample10() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n20_k8.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(8, maximumCliques.get(0).size());
	}
	
	public void testExample11() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n30_k15.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(15, maximumCliques.get(0).size());
	}
	
	public void testExample12() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n34_k14.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(14, maximumCliques.get(0).size());
	}
	
	
	public void testExample13() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n4_k4.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(4, maximumCliques.get(0).size());
	}
	
	public void testExample14() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n6_k3_2.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(3, maximumCliques.get(0).size());
	}
	
	public void testExample15() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n6_k3.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(3, maximumCliques.get(0).size());
	}
	
	public void testExample16() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n7_k3.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(3, maximumCliques.get(0).size());
	}
	
	public void testExample17() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n8_k3.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(3, maximumCliques.get(0).size());
	}
	
	
	public void testExample18() {
		Graph<Node, Edge> graph = reader.load("graphs//example_n8_k4.txt");
		MaximumClique<Node, Edge> mc = new MaximumClique<Node, Edge>(graph);
		List<Set<Node>> maximumCliques = mc.getCliques();
		assertEquals(4, maximumCliques.get(0).size());
	}

}
