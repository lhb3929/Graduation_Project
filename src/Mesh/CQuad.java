package Mesh;
//
//CQuad
//Graduation_Project
//
//Created by daBiaoGe on 2018/4/25.
//Copyright © 2018年 刘宏斌. All rights reserved.
//
//自定义的 随便性CFace
//主要用来判断边缘点的  没有相同面上的点  均为 平面边缘点


public class CQuad extends CFace{
	//  四边形四个顶点  m_id
	//	仅仅是记录 4个顶点的 m_id  并不是 vertex 数组
	int[] vertexs = new int[4];
	
	public CQuad( int i1 , int i2 , int i3, int i4) {
		vertexs[0] = i1;
		vertexs[1] = i2;
		vertexs[2] = i3;
		vertexs[3] = i4;
	}
	public CQuad() {
		super();
		for( int i = 0 ; i < 4 ; i ++ ) {
			vertexs[i] = -1;
		}
	}
	public CQuad( int i0 , int i1 , int i2 , int i3, int i4 ) {
		m_id = i0;
		vertexs[0] = i1;
		vertexs[1] = i2;
		vertexs[2] = i3;
		vertexs[3] = i4;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Integer.toString(m_id));
		stringBuilder.append("( ");
		stringBuilder.append(Integer.toString(vertexs[0]));
		stringBuilder.append(" , ");
		stringBuilder.append(Integer.toString(vertexs[1]));
		stringBuilder.append(" , ");
		stringBuilder.append(Integer.toString(vertexs[2]));
		stringBuilder.append(" , ");
		stringBuilder.append(Integer.toString(vertexs[3]));
		stringBuilder.append(" )");
		return stringBuilder.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if( obj == null ) {
			return false;
		}
		if(obj instanceof CQuad) {
			CQuad item = (CQuad) obj;
			if(equal(item)) {
				return true;
			}
			return false;
		}else {
			return false;
		}
	}
	
	public boolean equal(CQuad item) {
		int counter = 0;
		for(int i = 0 ; i < 4  ; i ++ ) {
			for(int j = 0 ; j < 4 ; j ++ ) {
				if(this.vertexs[i] == item.vertexs[j]) {
					counter += 1;
					break;
				}
			}
		}
		if(counter == 4) {
			return true;
		}else {
			return false;
		}
		
	}
	@Override
	public int hashCode() {
		return 0;
	}
}
