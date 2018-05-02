package Mesh;

import java.util.HashSet;
import java.util.Set;

import Geometry.CPoint;

public class Vertex {
	/*
	 * vertex id
	 */
	public int m_id;
	/*
	 * vertex position point
	 */
	public CPoint m_point;
//	/*
//	 * inducating if the vertex is on the boundary
//	 */
//	boolean m_boundary;
	
	String m_string;
	/*
	 * List of adjacent edges, such that current vertex is 
	 * the end vertex of the edge with smaller id
	 */
//	Set<Edge> m_Edges = new HashSet<Edge>();
	
	//通过 存储 id来去mesh数据结构中 的map获取 相应 vertex
	Set<Integer> m_Vertexs = new HashSet<Integer>();
	
	public Vertex(int id , CPoint point) {
		this.m_id = id;
		this.m_point = point;
	}
	public Vertex(int id ) {
		this(id, null);
	}
	
	public Set<Integer> getVertexs(){
		return m_Vertexs;
	}
	public boolean addVertex(int v) {
		if(m_Vertexs.add(v)) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || m_point == null) {
			return false;
		}
		if(obj instanceof Edge) {
			Vertex item = (Vertex) obj;
			if(this.m_point.equals(item)) {
				return true;
			}
			return false;
		}else {
			return false;
		}
		
    }
	
	@Override
	public String toString() {
		return Integer.toString(m_id) + "( " + Double.toString(m_point.getX()) + " , " + Double.toString(m_point.getY()) + " , " + Double.toString(m_point.getZ()) + " )";
	}
}
