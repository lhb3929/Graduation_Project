package Mesh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import AboutData.fromFile;
import AboutData.getData;
import AboutData.writeData;
import Geometry.CPoint;
import Math.Mesh_Math;

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
	
	public List<CQuad> quads = new ArrayList<CQuad>();
	
	
	
	
	
	/*
	 * 选出可优化结点时使用的数据结构
	 */
//	Set<Integer> outVertexs;   //外部点
//	Set<Integer> inVertexs;    //内部点
	Set<Integer> vertexs_opt = new HashSet<Integer>();  //可优化点
	
	
	
	public HexMesh() {
		/*
		 * 尝试写成单例模式
		 */
	}
	public void Vertexfilter() {
		Set<Integer> outVertexs = new HashSet<Integer>();   //外部点
		Set<Integer> inVertexs = new HashSet<Integer>();    //内部点
		
		
		Set<CQuad> t_Quads_in = new HashSet<CQuad>();
		Set<CQuad> t_Quads_out = new HashSet<CQuad>();
//		
//		for(int i = 0 ; i < quads.size() ; i ++ ) {
////			if(quads.get(i).equals(quads.get(22))) {
////				System.out.println("true" + Integer.toString(i));
////			}
//			System.out.println( Integer.toString(i));
//			if(t_Quads1.contains ( quads.get(i))) {
//				t_Quads2.remove(quads.get(i));
//				inVertexs.add(quads.get(i).vertexs[0]);
//				inVertexs.add(quads.get(i).vertexs[1]);
//				inVertexs.add(quads.get(i).vertexs[2]);
//				inVertexs.add(quads.get(i).vertexs[3]);
//			}else {
//				t_Quads1.add(quads.get(i));
//				t_Quads2.add(quads.get(i));
//			}
//		}
//		for(CQuad i : t_Quads2) {
//			outVertexs.add(i.vertexs[0]);
//			outVertexs.add(i.vertexs[1]);
//			outVertexs.add(i.vertexs[2]);
//			outVertexs.add(i.vertexs[3]);
//		}
		Map<Integer, List<CQuad>> map_cquads = new HashMap<Integer , List<CQuad>>();
		for(int i = 0 ; i < quads.size() ; i ++) {
			if(map_cquads.get(quads.get(i).vertexs[0] + quads.get(i).vertexs[1] + quads.get(i).vertexs[2] + quads.get(i).vertexs[3]) == null) {
				map_cquads.put(quads.get(i).vertexs[0] + quads.get(i).vertexs[1] + quads.get(i).vertexs[2] + quads.get(i).vertexs[3],new ArrayList<CQuad>());
			}
			map_cquads.get(quads.get(i).vertexs[0] + quads.get(i).vertexs[1] + quads.get(i).vertexs[2] + quads.get(i).vertexs[3]).add(quads.get(i));
		}
		int counter = 0;
		for(Map.Entry<Integer, List<CQuad>> entry : map_cquads.entrySet()) {
			if(entry.getValue().size() == 1) {
				t_Quads_out.add(entry.getValue().get(0));
//				System.out.println("out" );
			}else {
				for(int i = 0 ; i < entry.getValue().size() ; i ++ ) {
					boolean flag = true;
					for(int j = 0 ; j < entry.getValue().size() && flag; j ++ ) {
						if( entry.getValue().get(j).equals(entry.getValue().get(i)) ) {
							t_Quads_in.add(entry.getValue().get(i));
//							System.out.println("in" + Integer.toString(entry.getKey()));
							flag = false;
						}
					}
					if(flag) {
						t_Quads_out.add(entry.getValue().get(i));
//						System.out.println("out" + Integer.toString(entry.getKey()));
					}
					
				}
			}
			
//			System.out.println("do   " + Integer.toString(counter ++ ));
		}
		for(CQuad in : t_Quads_in ) {
			inVertexs.add(in.vertexs[0]);
			inVertexs.add(in.vertexs[1]);
			inVertexs.add(in.vertexs[2]);
			inVertexs.add(in.vertexs[3]);
		}
		for(CQuad in : t_Quads_out ) {
			inVertexs.add(in.vertexs[0]);
			inVertexs.add(in.vertexs[1]);
			inVertexs.add(in.vertexs[2]);
			inVertexs.add(in.vertexs[3]);
		}
		
		for(int i : inVertexs) {
			vertexs_opt.add(i);
		}
		
		Map<Integer , Vertex> map_outVertexs = new HashMap<Integer , Vertex>();
		for(int i : outVertexs) {
			Vertex vertex = new Vertex(i);
			for(int j : h_Vertexs.get(i).m_Vertexs) {
				if( outVertexs.contains(j) ) {
					vertex.m_Vertexs.add(j);
				}
			}
			map_outVertexs.put(i, vertex);
		}
		for(int i : outVertexs) {
			if(isPlane(map_outVertexs , i)) {
				vertexs_opt.add(i);
				System.out.println(Integer.toString(i) + "  is plane !!");
			}
		}
		System.out.println("filter succeed !!!");
		
		
	}
	public boolean isPlane(Map<Integer , Vertex> map_outVertexs , int id) {
		Vertex vertex = map_outVertexs.get(id);
		int counter = 0;
		Vertex[] vertexs = new Vertex[3];
		CPoint temp = null;
		for(int i : vertex.m_Vertexs) {
			if(counter < 3) {
				vertexs[counter] = h_Vertexs.get(i);
			}else if(counter == 3){
				temp = Mesh_Math.CalNormalVector(vertexs[0], vertexs[1], vertexs[2]);
			}else {
				if( !Mesh_Math.perpendicular(h_Vertexs.get(i).m_point, temp)) {
					return false;
				}
			}
		}
		return true;
	}

	
	
	
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
			double sum_X = item.m_point.getX() , sum_Y = item.m_point.getY() , sum_Z = item.m_point.getZ();
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
			/*
			 * 自定义 左手定则
			 */
			quads.add(new CQuad( hex.vertex_ids[0], hex.vertex_ids[1], hex.vertex_ids[2], hex.vertex_ids[3] ));
			quads.add(new CQuad( hex.vertex_ids[7], hex.vertex_ids[6], hex.vertex_ids[5], hex.vertex_ids[4] ));
			quads.add(new CQuad( hex.vertex_ids[4], hex.vertex_ids[0], hex.vertex_ids[3], hex.vertex_ids[7] ));
			quads.add(new CQuad( hex.vertex_ids[1], hex.vertex_ids[5], hex.vertex_ids[6], hex.vertex_ids[2] ));
			quads.add(new CQuad( hex.vertex_ids[0], hex.vertex_ids[4], hex.vertex_ids[5], hex.vertex_ids[1] ));
			quads.add(new CQuad( hex.vertex_ids[2], hex.vertex_ids[6], hex.vertex_ids[7], hex.vertex_ids[3] ));
			
			h_HalfFaces.put(Integer.toString(hex.id) + "_" + "1", new HalfFace(Integer.toString(hex.id) + "_" + "1", hex.vertex_ids[0], hex.vertex_ids[1], hex.vertex_ids[2], hex.vertex_ids[3]));
			h_HalfFaces.put(Integer.toString(hex.id) + "_" + "2", new HalfFace(Integer.toString(hex.id) + "_" + "2", hex.vertex_ids[7], hex.vertex_ids[6], hex.vertex_ids[5], hex.vertex_ids[4]));
			h_HalfFaces.put(Integer.toString(hex.id) + "_" + "3", new HalfFace(Integer.toString(hex.id) + "_" + "3", hex.vertex_ids[4], hex.vertex_ids[0], hex.vertex_ids[3], hex.vertex_ids[7]));
			h_HalfFaces.put(Integer.toString(hex.id) + "_" + "4", new HalfFace(Integer.toString(hex.id) + "_" + "4", hex.vertex_ids[1], hex.vertex_ids[5], hex.vertex_ids[6], hex.vertex_ids[2]));
			h_HalfFaces.put(Integer.toString(hex.id) + "_" + "5", new HalfFace(Integer.toString(hex.id) + "_" + "5", hex.vertex_ids[0], hex.vertex_ids[4], hex.vertex_ids[5], hex.vertex_ids[1]));
			h_HalfFaces.put(Integer.toString(hex.id) + "_" + "6", new HalfFace(Integer.toString(hex.id) + "_" + "6", hex.vertex_ids[2], hex.vertex_ids[6], hex.vertex_ids[7], hex.vertex_ids[3]));
			
			
			//除此之外还有填充 vertex中相邻结点和边的结构
			int v0 = hex.vertex_ids[0];
			int v1 = hex.vertex_ids[1];
			int v2 = hex.vertex_ids[2];
			int v3 = hex.vertex_ids[3];
			int v4 = hex.vertex_ids[4];
			int v5 = hex.vertex_ids[5];
			int v6 = hex.vertex_ids[6];
			int v7 = hex.vertex_ids[7];
			
			h_Vertexs.get(v0).m_Vertexs.add(v1);
			h_Vertexs.get(v0).m_Vertexs.add(v3);
			h_Vertexs.get(v0).m_Vertexs.add(v4);
			
			h_Vertexs.get(v1).m_Vertexs.add(v0);
			h_Vertexs.get(v1).m_Vertexs.add(v2);
			h_Vertexs.get(v1).m_Vertexs.add(v5);
			
			h_Vertexs.get(v2).m_Vertexs.add(v1);
			h_Vertexs.get(v2).m_Vertexs.add(v3);
			h_Vertexs.get(v2).m_Vertexs.add(v6);
			
			h_Vertexs.get(v3).m_Vertexs.add(v0);
			h_Vertexs.get(v3).m_Vertexs.add(v2);
			h_Vertexs.get(v3).m_Vertexs.add(v7);
			
			h_Vertexs.get(v4).m_Vertexs.add(v0);
			h_Vertexs.get(v4).m_Vertexs.add(v5);
			h_Vertexs.get(v4).m_Vertexs.add(v7);
			
			h_Vertexs.get(v5).m_Vertexs.add(v4);
			h_Vertexs.get(v5).m_Vertexs.add(v6);
			h_Vertexs.get(v5).m_Vertexs.add(v7);
			
			h_Vertexs.get(v6).m_Vertexs.add(v2);
			h_Vertexs.get(v6).m_Vertexs.add(v5);
			h_Vertexs.get(v6).m_Vertexs.add(v7);
			
			h_Vertexs.get(v7).m_Vertexs.add(v3);
			h_Vertexs.get(v7).m_Vertexs.add(v4);
			h_Vertexs.get(v7).m_Vertexs.add(v6);
			
			
		}
		Vertexfilter();
		System.out.println("read succeed !!!");
		System.out.println("test data !!!");
		
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
