package Mesh;
//
//CHalfEdge
//Graduation_Project
//
//Created by daBiaoGe on 2018/4/25.
//Copyright © 2018年 刘宏斌. All rights reserved.
//


public class CHalfEdge {
	/*
	 *  Edge, current halfedge attached to. 
	 */
	CEdge m_edge;
	/*
	 * Face, current halfedge attached to.
	 */
	CFace m_face;
	/*
	 * Target vertex of the current halfedge.
	 */
	CVertex m_vertex;		//target vertex
	/*
	 * Previous halfedge of the current halfedge, in the same face.
	 */
	CHalfEdge m_prev;
	/*
	 * ! Next halfedge of the current halfedge, in the same face. 
	 */
	CHalfEdge m_next;
	/*
	 * The string of the current halfedge.
	 */
	String m_string;
	
	public CHalfEdge(CEdge edge , CFace face , CVertex  vertex , CHalfEdge prev , CHalfEdge next , String str) {
		this.m_edge = edge;
		this.m_face = face;
		this.m_vertex = vertex;
		this.m_prev = prev;
		this.m_next = next;
		this.m_string = str;
	}
	public CHalfEdge() {
		this(null, null, null, null, null, "default_halfEdge");
	}
	/*
	 * The dual halfedge of the current halfedge.
	 */
	CHalfEdge he_sym() {
		
		return m_edge.other(this);
	}
	CHalfEdge he_prev() {
		return m_prev;
	}
	CHalfEdge he_next() {
		return m_next;
	}
	CVertex vertex() {
		return m_vertex;
	}
	CVertex target() {
		return m_vertex;
	}
	CVertex source() {
		return m_prev.vertex();
	}
	CFace face() {
		return m_face;
	}
	CEdge edge() {
		return m_edge;
	}
	
	//CHalfEdge 实例变量  并不是 private  所以不用谢setter 和 getter
	//ccw  逆时针  clw  顺时针
	
	/*
	 * roate the halfedge about its target vertex CCWly
	 */
	CHalfEdge ccw_rotate_about_target() {
		CHalfEdge he_dual = he_sym();
		if( he_dual == null ) 
			return null;

		return he_dual.he_prev();
	}
	/*
	 * roate the halfedge about its target vertex CLWly
	 */
	CHalfEdge clw_rotate_about_target() {
		CHalfEdge he = he_next().he_sym();
		return he;
	}
	/*
	 * roate the halfedge about its source vertex CCWly
	 */
	CHalfEdge ccw_rotate_about_source() {
		CHalfEdge he = he_prev().he_sym();
		return he;
	}
	/*
	 * roate the halfedge about its source vertex CLWly
	 */
	CHalfEdge clw_rotate_about_source() {
		CHalfEdge he = he_sym();
		if( he == null ) 
			return null;
		return he.he_next();
	}
	
	
	
	/*
	Read the traits from the string.
	 */
	void _from_string() {};
	/*
	Save the traits to the string.
	 */
	void _to_string() {};
	
}
