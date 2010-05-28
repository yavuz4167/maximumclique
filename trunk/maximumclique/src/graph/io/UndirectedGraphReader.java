package graph.io;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.io.MatrixFile;
import graph.io.utils.EdgeFactory;
import graph.io.utils.NodeFactory;
import graph.io.utils.UndirectedGraphFactory;
import graph.util.Edge;
import graph.util.Node;

/**
 * 
 * @author bbarczynski
 * 
 */
public class UndirectedGraphReader extends MatrixFile<Node, Edge> {
	public UndirectedGraphReader() {
		super(null, new UndirectedGraphFactory(), new NodeFactory(), new EdgeFactory());
	}

	@Override
	public Graph<Node, Edge> load(String file) {
		try {
			Graph<Node, Edge> g = super.load(file);
			return g;
		}
		catch (Exception e) {
			return null;
		}
		
	}

}