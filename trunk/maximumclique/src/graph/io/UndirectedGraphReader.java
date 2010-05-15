package graph.io;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.io.MatrixFile;
import graph.io.utils.EdgeFactory;
import graph.io.utils.NodeFactory;
import graph.io.utils.UndirectedGraphFactory;
import graph.util.Edge;
import graph.util.Node;

public class UndirectedGraphReader extends MatrixFile<Node, Edge> {
	public UndirectedGraphReader() {
		super(null, new UndirectedGraphFactory(), new NodeFactory(), new EdgeFactory());
		// TODO Auto-generated constructor stub
	}

	@Override
	public Graph<Node, Edge> load(String arg0) {
		// TODO [bbarczynski] weryfikajca czy graf jest na pewno nie skierowany,
		// 1 po i nad przekatna

		return super.load(arg0);
	}

}

// UndirectedSparseGraph<V, E> load(String fileName) {
//
// MatrixFile mf = new MatrixFile<V, E>(null, new
// Factory<UndirectedSparseGraph<V, E>>() {
//
// @Override
// public UndirectedSparseGraph<V, E> create() {
// return new UndirectedSparseGraph<V, E>();
// }
// }, new NodeFactory(), new EgdeFactory());
//
// Graph<V, E> graph = mf.load(fileName);
// // TODO [bbarczynski] weryfikacja czy graf jest nieskierowany, czyli pod
// // i nad przekatna sa takie same 1
//
// return graph;
// }

// TODO [bbarczynski]
// save()

