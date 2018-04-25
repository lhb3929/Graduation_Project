package Mesh;
//
//CFace
//Graduation_Project
//
//Created by daBiaoGe on 2018/4/25.
//Copyright © 2018年 刘宏斌. All rights reserved.
//


public class CFace {
	/*
	 * id of the current face
	 */
	int m_id;
	/*
	 * one halfedge attaching to the current face
	 */
	CHalfEdge m_halfEdge;
	
	String m_string;
	
	public CFace(CHalfEdge item) {
		this.m_halfEdge = item;
	}
	public CFace() {
		this(null);
	}
}
