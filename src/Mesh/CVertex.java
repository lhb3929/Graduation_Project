package Mesh;
//
//CVertex
//Graduation_Project
//
//Created by daBiaoGe on 2018/4/25.
//Copyright © 2018年 刘宏斌. All rights reserved.
//  

import java.util.ArrayList;
import java.util.List;

import Geometry.CPoint;

public class CVertex {
	/*
	 * vertex id
	 */
	int m_id;
	/*
	 * vertex position point
	 */
	CPoint m_point;
	/*
	 * normal at the vertex w  -----------  不理解
	 */
	CPoint m_normal;
	/*
	 * the most CCW incoming halfedge of the vertex
	 */
	CHalfEdge m_halfedge;
	/*
	 * inducating if the vertex is on the boundary
	 */
	boolean m_boundary;
	
	String m_string;
	
	/*
	 * List of adjacent edges, such that current vertex is 
	 * the end vertex of the edge with smaller id
	 */
	List<CEdge> m_Edges = new ArrayList<CEdge>();
	
	/*
	 *  brief The most counter clockwise incoming halfedge of the vertex
	 *  return the most CCW in halfedge
	 */
	CHalfEdge most_ccw_in_halfedge() {
		if(!m_boundary) {
			return m_halfedge;//current half edge is the most ccw in halfedge 
		}
		CHalfEdge he = m_halfedge.ccw_rotate_about_target();
		while(he != null) {
			m_halfedge = he;
			he = m_halfedge.ccw_rotate_about_target();
		}
		return m_halfedge;
	}
	
	/*
	 * most clockwise in halfedge
	 */
	CHalfEdge most_clw_in_halfedge() {
		//for interior vertex 
		if( !m_boundary )
		{
			return most_ccw_in_halfedge().ccw_rotate_about_target(); //the most ccw in halfedge rotate ccwly once to get the most clw in halfedge
		}
		//for boundary vertex
		CHalfEdge he = m_halfedge.clw_rotate_about_target();
		//rotate to the most clw in halfedge
		while( he != null )
		{
			m_halfedge = he;
			he = m_halfedge.clw_rotate_about_target();
		}

		return m_halfedge;
	}
	
	//most counter clockwise out halfedge
	CHalfEdge most_ccw_out_halfedge(){ 
		//for interior vertex
		if( !m_boundary )
		{
			return most_ccw_in_halfedge().he_sym(); //most ccw out halfedge is the dual of the most ccw in halfedge
		}

		//for boundary vertex
		CHalfEdge he = m_halfedge.he_next();
		//get the out halfedge which is the next halfedge of the most ccw in halfedge
		CHalfEdge ne = he.ccw_rotate_about_source();
		//rotate ccwly around the source vertex
		while( ne != null )
		{
			he = ne;
			ne = he.ccw_rotate_about_source();
		}

		return he;
	};

	//most clockwise out halfedge
	CHalfEdge most_clw_out_halfedge(){ 
		//for interior vertex
		if( !m_boundary )
		{
			return most_ccw_out_halfedge().ccw_rotate_about_source();  //most ccw out halfedge rotate ccwly once about the source
		}
		//get one out halfedge
		CHalfEdge he = m_halfedge.he_next();
		//rotate the out halfedge clwly about the source
		CHalfEdge ne = he.clw_rotate_about_source();
		
		while( ne != null )
		{
			he = ne;
			ne = he.clw_rotate_about_source();
		}

		return he;
	}
}
