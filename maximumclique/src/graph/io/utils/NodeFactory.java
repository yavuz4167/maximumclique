package graph.io.utils;

import graph.util.Node;

import org.apache.commons.collections15.Factory;

public class NodeFactory implements Factory<Node> {
	private int i = 1;

	public NodeFactory() {
		i = 1;
	}

	@Override
	public Node create() {
		System.out.println("Create Node i=" + i);
		return new Node(i++);
	}
}
