package Mesh;
//
//BaseMesh
//Graduation_Project
//
//Created by daBiaoGe on 2018/4/25.
//Copyright © 2018年 刘宏斌. All rights reserved.
//

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseMesh {
	
	List<CVertex> vertexs;
	List<CEdge> edges;
	List<CFace> faces;
	
	Map<Integer, CVertex> m_map_vertex;
	Map<Integer, CFace> m_map_face;
	
	public BaseMesh() {
		vertexs = new ArrayList<CVertex>();
		edges = new ArrayList<CEdge>();
		faces = new ArrayList<CFace>();
		
		m_map_vertex = new HashMap<>();
		m_map_face = new HashMap<>();
	}
	
	CVertex edgeVertex_source(CEdge e) {
		if(e.m_halfedge[0] != null) {
			return e.m_halfedge[0] .source();
		}else {
			return null;
		}
	}
	CVertex edgeVertex_target(CEdge e) {
		if(e.m_halfedge[0] != null) {
			return e.m_halfedge[0].target();
		}else {
			return null;
		}
	}
	CFace edgeFace0(CEdge e) {
		if(e.m_halfedge[0] != null) {
			return e.m_halfedge[0].face();
		}else {
			return null;
		}
	}
	CFace edgeFace1(CEdge e) {
		if(e.m_halfedge[1] != null) {
			return e.m_halfedge[1].face();
		}else {
			return null;
		}
	}

	CHalfEdge edgeHalfedge(CEdge e , int id) {
		return e.getCHalfEdge(id);
	}
	
	CFace halfedgeFace(CHalfEdge he) {
		return he.face();
	}
	CHalfEdge faceHalfedge(CFace f) {
		//因为一个CFace只有一个半边的实例变量，所以只返回一个，但可以通过halfEdge的api中的pre和next进行遍历
		return f.m_halfEdge;
	}
	CHalfEdge halfedgeNext(CHalfEdge he) {
		return he.he_next();
	}
	CHalfEdge halfedgePrev(CHalfEdge he) {
		return he.he_prev();
	}
	CHalfEdge halfedgeSym(CHalfEdge he) {
		return he.he_sym();
	}
	CEdge halfedgeEdge(CHalfEdge he) {
		return he.edge();
	}
	/*
	 * 	The target vertex of a halfedge. 
	   @param he the input halfedge.
	   @return the target vertex of he.
	 */
	CVertex halfedgeVertex(CHalfEdge he) {
		return he.target();
	}
	CVertex halfedgeTarget(CHalfEdge he) {
		return he.target();
	}
	CVertex halfedgeSource(CHalfEdge he) {
		return he.source();
	}
	boolean isBoundary(CEdge e) {
		if(e.getCHalfEdge(0) == null || e.getCHalfEdge(1) == null) {
			return true;
		}else {
			return false;
		}
	}
	boolean isBoundary(CVertex vertex) {
		return vertex.m_boundary;
	}
	boolean isBoundary(CHalfEdge he) {
		if(he.he_sym() == null ) {
			return true;
		}
		return false;
	}
	
	int numVertices() {
		return vertexs.size();
	}
	int numEdges() {
		return edges.size();
	}
	int numFaces() {
		return faces.size();
	}
	
	CHalfEdge vertexMostClwOutHalfEdge(CVertex vertex) {
		return vertex.most_clw_out_halfedge();
	}
	CHalfEdge vertexMostCcwOutHalfEdge(CVertex vertex) {
		return vertex.most_ccw_out_halfedge();
	}
	
//	CHalfEdge corner(CVertex v , CFace f) {}
	
	CHalfEdge vertexNextCcwOutHalfEdge(CHalfEdge he) {
		if(he.he_sym() != null) {
			return he.clw_rotate_about_source();
		}
		return null;
	}
	/*!
	The next Clw Out HalfEdge 
	\param he the input halfedge .
	\return the next Clw Out HalfEdge, sharing the same source of he.
	*/
	CHalfEdge vertexNextClwOutHalfEdge(CHalfEdge he) {
		if(he.he_sym() != null) {
			return he.clw_rotate_about_source();
		}
		return null;
	}
	/*!
	The most Clw In HalfEdge of a vertex
	\param v the input vertex.
	\return the most Clw In HalfEdge of v.
	*/

	CHalfEdge vertexMostClwInHalfEdge(CVertex v) {
		return v.most_clw_in_halfedge();
	}
	/*!
	The most Ccw In HalfEdge of a vertex
	\param v the input vertex.
	\return the most Clw In HalfEdge of v.
	*/
	CHalfEdge vertexMostCcwInHalfEdge(CVertex v) {
		//in 和 out 就是 同一条edge的两条半边之间的区别  
		return v.most_ccw_in_halfedge();
	}
	/*!
	The next Clw In HalfEdge 
	\param he the input halfedge .
	\return the next Clw In HalfEdge, sharing the same target of he.
	*/
	CHalfEdge vertexNextClwInHalfEdge(CHalfEdge he) {
		return he.clw_rotate_about_target();
	}
	/*!
	The next Clw HalfEdge of a halfedge in a face
	\param he the input halfedge.
	\return the next Clw HalfEdge of he in a face.
	*/
	CHalfEdge faceNextClwHalfEdge(CHalfEdge he) {
		return he.he_prev();
	}
	/*!
	The next Ccw HalfEdge of a halfedge in a face
	\param he the input halfedge.
	\return the next Ccw HalfEdge of he in a face.
	*/
	CHalfEdge faceNextCcwHalfEdge(CHalfEdge he) {
		return he.he_next();
	}
	/*!
	The most Ccw HalfEdge of a face
	\param face the input face.
	\return the most Ccw HalfEdge of f.
	*/
	CHalfEdge faceMostCcwHalfEdge(CFace face) {
		return face.m_halfEdge;
	}
	/*!
	The most Clw HalfEdge in a face
	\param face the input face.
	\return the most Clw HalfEdge in a face.
	*/
	CHalfEdge faceMostClwHalfEdge(CFace face) {
		return face.m_halfEdge.he_next();
	}
	
	double edgeLength(CEdge edge) {
		CVertex v1 = edgeVertex_source(edge);
		CVertex v2 = edgeVertex_target(edge);
		return (Math.sqrt((v1.m_point.getX() - v2.m_point.getX()) * (v1.m_point.getX() - v2.m_point.getX() + (v1.m_point.getY() - v2.m_point.getY() * (v1.m_point.getY() - v2.m_point.getY())))));
	}
	
	
	
	//  Create
	CVertex createVertex(int id) {
		CVertex v = new CVertex(id, null);
		vertexs.add(v);
		m_map_vertex.put(id, v);
		return v;
	}
	
	//通过 顶点数组  和 id  创建一个面，并且加入到集合中
	CFace createFace(CVertex[] v , int id ) {
		CFace face = new CFace();
		face.m_id = id;
		
		
		//create halfedges
		CHalfEdge[] hes = new CHalfEdge[4] ;
		for( int i = 0 ; i < 4 ; i ++ ) {
			hes[i] = new CHalfEdge();
			CVertex vertex = v[i];
			hes[i].m_vertex = vertex;
			vertex.m_halfedge = hes[i];
		}
		//linking to each other
		for(int i = 0 ; i < 4 ; i ++ ) {
			hes[i].m_next = hes[( i + 1 % 4)];
			hes[i].m_prev = hes[( i + 3 % 4)];
		}
		//connecting with edge
		for(int i = 0 ; i < 4 ; i ++ ) {
			CEdge e = createEdge( v[i], v[(i+2)%3] );
			if( e.m_halfedge[0]  == null )
			{
				e.m_halfedge[0] = hes[i];
			}
			else
			{
				e.m_halfedge[1] = hes[i];
			}
			hes[i].m_edge = e;
		}
		//linking to face
		for(int i = 0 ; i < 4 ; i ++ ) {
			hes[i].m_face = face;
			//一个面只有一个半边实例变量，只要完成半边到face的标记 和 半边pre ， next的标记 既可以完成遍历 和 获取整体数据变量
			face.m_halfEdge = hes[i];
		}
		
		
		faces.add(face);
		m_map_face.put(id, face);
		return face;
	}
	
	// 根据两个点创建一个边或者返回一条已经存在的边
	CEdge createEdge(CVertex v1 , CVertex v2) {
		CVertex pv = v1.m_id < v2.m_id ? v1 : v2;
		List<CEdge> lEdges = pv.m_Edges;
		for(CEdge edge : lEdges) {
			CEdge pE = edge;
			CHalfEdge pH = pE.getCHalfEdge(0);
			if( pH.source() == v1 && pH.target() == v2 ) 
			{
				return pE;		
			}
			if( pH.source() == v2 && pH.target() == v1 )
			{
				return pE;
			}
		}
		CEdge edge  = new CEdge();
		edges.add(edge);
		lEdges.add(edge);
		return edge;
	}
	//查找 顶点为 v1,v2的两条边
	CEdge vertexEdge(CVertex v1 , CVertex v2) {
		CVertex pv = v1.m_id < v2.m_id ? v1 : v2;
		List<CEdge> lEdges = pv.m_Edges;
		for(CEdge edge : lEdges) {
			CEdge pE = edge;
			CHalfEdge pH = edgeHalfedge(pE, 0);
			if( pH.source() == v1 && pH.target() == v2 ) 
			{
				return pE;		
			}
			if( pH.source() == v2 && pH.target() == v1 )
			{
				return pE;
			}
		}
		return null;
	}
	CHalfEdge vertexHalfedge(CVertex v1 , CVertex v2) {
		CEdge edge = vertexEdge(v1, v2);
		if(edge != null) {
			CHalfEdge he = edge.getCHalfEdge(0);
			if(he.vertex() == v2 && he.he_prev().vertex() == v1 ) {
				return he;
			}
			he = edge.getCHalfEdge(1);
			if(he.vertex() == v2 && he.he_prev().vertex() == v1 ) {
				return he;
			}
		}
		return null;
	}
	
	List<CEdge> vertexEdges(CVertex v){
		return v.m_Edges;
	}
	CHalfEdge vertexHalfedge(CVertex v) {
		return v.m_halfedge;
	}
	
    //删除一个面
	void deleteFace(CFace face) {
		
	}

}
