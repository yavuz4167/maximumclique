package test.bartek;

import java.io.File;
import java.util.List;
import java.util.Set;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import graph.algorithm.MaximumClique;
import graph.io.UndirectedGraphReader;
import graph.util.Edge;
import graph.util.Node;

public class testing {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Start");
		// Graph<V, E> where V is the type of the vertices
		// and E is the type of the edges
		Graph<Integer, String> g = new SparseMultigraph<Integer, String>();
		// Add some vertices. From above we defined these to be type Integer.
		g.addVertex(1);
		g.addVertex(2);
		g.addVertex(3);
		g.addEdge("Edge-A", 1, 2); // Note that Java 1.5 auto-boxes primitives
		g.addEdge("Edge-B", 2, 3);

		System.out.println("The graph g = " + g.toString());

		File f = new File(".");
		System.out.println(f.getAbsolutePath());

		Object node = new Node(3);
		System.out.println(node.toString());

		// final Factory<Node> nodeFactory = new Factory<Node>() {
		//
		// @Override
		// public Node create() {
		// // TODO Auto-generated method stub
		// return new Node(1);
		// }
		// };
		// MatrixFile mf = new MatrixFile<Node, Edge>(null, new
		// Factory<UndirectedSparseGraph<Node, Edge>>() {
		//
		// @Override
		// public UndirectedSparseGraph<Node, Edge> create() {
		// return new UndirectedSparseGraph<Node, Edge>();
		// }
		// }, new NodeFactory(), new EgdeFactory());
		// Graph<Node, Edge> g1 = mf.load("graphs//g1.txt");

		UndirectedGraphReader reader = new UndirectedGraphReader();
		Graph<Node, Edge> g1 = reader.load("graphs//example_n11_k6.txt");

		System.out.println("The graph g1 = " + g1.toString());
		MaximumClique<Node, Edge> maximumClique = new MaximumClique<Node, Edge>(g1);
		List<Set<Node>> cliques = maximumClique.getCliques();
		for (Set<Node> clique : cliques) {
			System.out.println("Nawiększa klika to: " + maximumClique.printClique(clique));
		}
	}
}
