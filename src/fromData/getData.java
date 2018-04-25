package fromData;
//
//getData
//Graduation_Project
//
//Created by daBiaoGe on 2018/4/25.
//Copyright © 2018年 刘宏斌. All rights reserved.
//
// 根据fromFile 得到的 Strings 然后解析每一条信息，把其中的 vertex  ， hex ， face 等装入到 对应的容器中

import java.util.ArrayList;
import java.util.List;

import Geometry.CPoint;
import Mesh.CQuad;
import Mesh.CVertex;

public class getData {
	List<CVertex> vertexs ;	
	List<CQuad> quads;
	
	public getData() {
		vertexs = new ArrayList<CVertex>();
		quads = new ArrayList<CQuad>();
	}
	public void Do( List<String> strings ) {
		for(String str : strings) {
			String[] strArr = str.split(" ");
			if(strArr.length == 5 && strArr[0].equals("Vertex")) {
				vertexs.add(new CVertex(Integer.parseInt(strArr[1]), new CPoint(Double.parseDouble(strArr[2]), Double.parseDouble(strArr[3]), Double.parseDouble(strArr[4]))));
			}else if (strArr.length == 6 && strArr[0].equals("Face")) {
				quads.add(new CQuad( Integer.parseInt(strArr[1]),Integer.parseInt(strArr[2]),Integer.parseInt(strArr[3]),Integer.parseInt(strArr[4]),Integer.parseInt(strArr[5]) ));
			}else {
				
			}
		}
	}
	public List<CVertex> getVertexs(){
		return vertexs;
	}
	public List<CQuad> getCQuads(){
		return quads;
	}
	
	
}
