package graph.util;

/**
 * 
 * @author bbarczynski
 * @date 2010-05-13
 * 
 */
public class Edge {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "E" + id;
	}
}
