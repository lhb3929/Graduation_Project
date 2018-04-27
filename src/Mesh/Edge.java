package Mesh;

public class Edge {
//	/*
//	Pointers to the two halfedges attached to the current edge.
//	 */
//	CHalfEdge[] m_halfedge = new CHalfEdge[2];
	
	
	/*
	The string associated to the current edge.
	 */
	String m_string;
	
	Vertex vertex1;
	Vertex vertex2;
	
	public Edge( Vertex v1 , Vertex v2 , String string) {
		vertex1 = v1;
		vertex2 = v2;
		m_string = string;
	}
	public Edge( Vertex v1 , Vertex v2 ) {
		this(v1, v2, null);
	}
	
	public Vertex other(Vertex v) {
		if(v == null) {
			return null;
		}else {
			if( v.equals(vertex1)) {
				return vertex2;
			}else if(v.equals(vertex2)){
				return vertex1;
			}
			return null;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || vertex1 == null || vertex2 == null) {
			return false;
		}
		if(obj instanceof Edge) {
			Edge item = (Edge) obj;
			if(vertex1.equals(item.vertex1) && vertex2.equals(item.vertex2)) {
				return true;
			}else if (vertex2.equals(item.vertex1) && vertex1.equals(item.vertex2)) {
				return false;
			}
			return false;
		}else {
			return false;
		}
		
    }
}
