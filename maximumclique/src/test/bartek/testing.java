package test.bartek;

import java.io.File;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.io.MatrixFile;
import graph.io.NodeFactory;
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

		final Factory<Node> nodeFactory = new Factory<Node>() {

			@Override
			public Node create() {
				// TODO Auto-generated method stub
				return new Node(1);
			}
		};
		MatrixFile mf = new MatrixFile<Node, Edge>(null, new Factory<UndirectedSparseGraph<Node, Edge>>() {

			@Override
			public UndirectedSparseGraph<Node, Edge> create() {
				return new UndirectedSparseGraph<Node, Edge>();
			}
		}, new NodeFactory(), new Factory<Edge>() {

			@Override
			public Edge create() {
				// TODO Auto-generated method stub
				return new Edge();
			}
		});
		UndirectedSparseGraph<Node, Edge> g1 = (UndirectedSparseGraph<Node, Edge>) mf.load("graphs//g1.txt");

		System.out.println("The graph g1 = " + g1.toString());

	}
}
