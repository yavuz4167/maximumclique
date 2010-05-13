package graph.util;

/**
 * 
 * @author bbarczynski
 * @date 2010-05-13
 * 
 */
public class Node {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Node(int id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "V" + id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Node other = (Node) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
