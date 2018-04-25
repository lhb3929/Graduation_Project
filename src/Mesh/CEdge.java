package Mesh;
//
//CEdge
//Graduation_Project
//
//Created by daBiaoGe on 2018/4/25.
//Copyright © 2018年 刘宏斌. All rights reserved.
//

public class CEdge {
	/*
	Pointers to the two halfedges attached to the current edge.
	 */
	CHalfEdge[] m_halfedge = new CHalfEdge[2];
	/*
	The string associated to the current edge.
	 */
	String m_string;
	
	public CEdge(CHalfEdge normal , CHalfEdge dual) {
		m_halfedge[0] = normal;
		m_halfedge[1] = dual;
	}
	public CEdge() {
		this(null, null);
	}
	/*!
	* The halfedge attached to the current edge
	* param id either 0 or 1
	* return the halfedge[id]
	 */
	CHalfEdge getCHalfEdge( int id ) {
		if(id >= 0 && id < 2) {
			return m_halfedge[id];
		}else {
			return null;
		}
	}
	/*
	 * whether the edge is on the boundary.
	 */
	boolean boundary() { 
		return (m_halfedge[0] == null && m_halfedge[1] != null ) || (m_halfedge[0] != null && m_halfedge[1] == null ); 
	}
	/*
	 * The dual halfedge to the input halfedge
		\param he halfedge attached to the current edge
		\return the other halfedge attached to the current edge
	 */
	CHalfEdge other(CHalfEdge he) {
		return ( he != m_halfedge[0] ) ? m_halfedge[0] : m_halfedge[1];
	}
	String string() {
		return m_string;
	}
}
