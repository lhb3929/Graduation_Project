package Mesh;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fromData.fromFile;
import fromData.getData;

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
		getData data = new getData();
		try {
			/*
			 * data  的  hexs  和  vertexs 变量分别存储着 六面体 和顶点数据
			 */
			data.Do(new fromFile().ReadFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("文件路径无法找到或者 文件不存在 ");
		}
		
		for(Vertex vertex : data.getVertexs()) {
			addVertexs(vertex);
		}
		for(Hex hex : data.getHexs()) {
			addHex(hex);
			
			
			//此处书写添加半面的代码  根据hex八个顶点的顺序，依次创建六个半面
			//
		}
		
		
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
