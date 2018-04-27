package Mesh;

import java.util.HashMap;
import java.util.Map;

/*
 * 一个六面体网格
 */
public class HexMesh {
	
	/*
	 * 网格数据
	 */
	public Map<Integer , Vertex> h_Vertexs = new HashMap<Integer , Vertex>();
	public Map<String , HalfFace> h_HalfFaces = new HashMap<String , HalfFace>();
	public Map<Integer , Hex> h_Hexs = new HashMap<Integer , Hex>();
	
	
	public void readData(String fileName) {
		/*
		 * 从文件里面读取数据  到这个六面体网格中
		 */
		
		
	}
	public void addVertexs(Vertex v) {
		h_Vertexs.put(v.m_id , v);
	}
	public void addHalfFace(HalfFace hf) {
		h_HalfFaces.put(hf.s_id, hf);
	}
	public void addHex(Hex h) {
		h_Hexs.put(h.id, h);
	}
}
