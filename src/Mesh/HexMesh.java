package Mesh;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import AboutData.fromFile;
import AboutData.getData;
import AboutData.writeData;

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
	
	
	
	public HexMesh() {
		
	}
	/*
	 * 尝试写成单例模式
	 */
	
	
	//Laplacian 平滑技术
	public void Laplacian() {
		
		for(Map.Entry<Integer, Vertex> entry : h_Vertexs.entrySet()) {
			Vertex item = entry.getValue();
			double sum_X = 0.0 , sum_Y = 0.0 ,sum_Z = 0.0;
			/*
			 * 只是根据邻接点求得 该点坐标，并未把该点本身算入
			 */
			for(int i : item.m_Vertexs ) {
				sum_X += getVertex_X(i);
				sum_Y += getVertex_Y(i);
				sum_Z += getVertex_Z(i);
			}
			item.m_point.setX(sum_X / item.m_Vertexs.size());
			item.m_point.setY(sum_Y / item.m_Vertexs.size());
			item.m_point.setZ(sum_Z / item.m_Vertexs.size());
			
			entry.setValue(item);
		}
	}
	
	//  	Laplacian平滑技术
	public void Laplacian_V() {
		
		for(Map.Entry<Integer, Vertex> entry : h_Vertexs.entrySet()) {
			Vertex item = entry.getValue();
			double sum_X = item.m_point.getX() , sum_Y = item.m_point.getY() ,sum_Z = item.m_point.getZ();
			/*
			 * 根据邻接点 和 该点本身 求得 该点坐标 
			 */
			for(int i : item.m_Vertexs ) {
				sum_X += getVertex_X(i);
				sum_Y += getVertex_Y(i);
				sum_Z += getVertex_Z(i);
			}
			item.m_point.setX(sum_X / ( item.m_Vertexs.size() + 1 ));
			item.m_point.setY(sum_Y / ( item.m_Vertexs.size() + 1 ));
			item.m_point.setZ(sum_Z / ( item.m_Vertexs.size() + 1 ));
			
			entry.setValue(item);
		}
	}
		
	
	public double getVertex_X(int id) {
		return h_Vertexs.get(id).m_point.getX();
	}
	public double getVertex_Y(int id) {
		return h_Vertexs.get(id).m_point.getY();
	}
	public double getVertex_Z(int id) {
		return h_Vertexs.get(id).m_point.getZ();
	}
	
	
	
	

	
	
	
	
	public void readData() {
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
			
			//除此之外还有填充 vertex中相邻结点和边的结构
			
		}
		System.out.println("read succeed !!!");
		
	}
	public void writeFile() throws IOException {
		writeData wData = new writeData();
		wData.writeFile(this);
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
