package graph.io;

import graph.util.Node;

import org.apache.commons.collections15.Factory;

public class NodeFactory implements Factory<Node> {
	int i = 1;

	public NodeFactory() {
		i = 1;
	}

	@Override
	public Node create() {
		return new Node(i++);
	}
}
