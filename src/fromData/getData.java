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
import Mesh.Hex;
import Mesh.Vertex;

public class getData {
	List<Vertex> vertexs ;	
	List<Hex> hexs;
	
	public getData() {
		vertexs = new ArrayList<Vertex>();
		hexs = new ArrayList<Hex>();
	}
	public void Do( List<String> strings ) {
		for(String str : strings) {
			String[] strArr = str.split(" ");
			if(strArr.length == 5 && strArr[0].equals("Vertex")) {
				vertexs.add(new Vertex(Integer.parseInt(strArr[1]), new CPoint(Double.parseDouble(strArr[2]), Double.parseDouble(strArr[3]), Double.parseDouble(strArr[4]))));
			}else if (strArr.length == 10 && strArr[0].equals("Hex")) {
				Hex hex = new Hex(Integer.parseInt(strArr[1]));
				hex.vertex_ids[0] = Integer.parseInt(strArr[2]);
				hex.vertex_ids[1] = Integer.parseInt(strArr[3]);
				hex.vertex_ids[2] = Integer.parseInt(strArr[4]);
				hex.vertex_ids[3] = Integer.parseInt(strArr[5]);
				hex.vertex_ids[4] = Integer.parseInt(strArr[6]);
				hex.vertex_ids[5] = Integer.parseInt(strArr[7]);
				hex.vertex_ids[6] = Integer.parseInt(strArr[8]);
				hex.vertex_ids[7] = Integer.parseInt(strArr[9]);
				hexs.add(hex);
			}else {
				
			}
		}
	}
	public List<Vertex> getVertexs(){
		return vertexs;
	}
	public List<Hex> getHexs(){
		return hexs;
	}
	
	
}
