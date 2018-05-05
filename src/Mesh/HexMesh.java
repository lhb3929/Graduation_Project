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
	 * 正指数
	 */
	public final static double index = 1;
	
	/*
	 * 网格数据
	 */
	public Map<Integer , Vertex> h_Vertexs = new HashMap<Integer , Vertex>();
	public Map<String , HalfFace> h_HalfFaces = new HashMap<String , HalfFace>();
	public Map<Integer , Hex> h_Hexs = new HashMap<Integer , Hex>();
	
	public List<CQuad> quads = new ArrayList<CQuad>();
	
	
	
	

	public Set<Integer> vertexs_opt = new HashSet<Integer>();  //可优化点
	
	
	
	public HexMesh() {
		/*
		 * 尝试写成单例模式
		 */
	}
	public void virvalFilfer() {
		for(Map.Entry<Integer, Vertex> entry : h_Vertexs.entrySet() ) {
			vertexs_opt.add(entry.getKey());
		}
	}
	public void Vertexfilter() {
		/*
		 * 选出可优化结点时使用的数据结构
		 */
//		Set<Integer> outVertexs;   //外部点
//		Set<Integer> inVertexs;    //内部点
		Set<Integer> outVertexs = new HashSet<Integer>();   //外部点
		Set<Integer> inVertexs = new HashSet<Integer>();    //内部点
		
		
		Set<CQuad> t_Quads_in = new HashSet<CQuad>();
		Set<CQuad> t_Quads_out = new HashSet<CQuad>();

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
			counter ++;
			if(counter %1000 ==0) {
				System.out.println("do   " + Integer.toString(counter ++ ));
			}
			
		}
		for(CQuad in : t_Quads_in ) {
			inVertexs.add(in.vertexs[0]);
			inVertexs.add(in.vertexs[1]);
			inVertexs.add(in.vertexs[2]);
			inVertexs.add(in.vertexs[3]);
		}
		for(CQuad in : t_Quads_out ) {
			outVertexs.add(in.vertexs[0]);
			outVertexs.add(in.vertexs[1]);
			outVertexs.add(in.vertexs[2]);
			outVertexs.add(in.vertexs[3]);
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
				}else {
					System.out.println(Integer.toString(j) + "不在表面" );
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
		int counter = 1;
		Vertex[] vertexs = new Vertex[3];
		vertexs[0] = h_Vertexs.get(id);
		CPoint temp = null;
		for(int i : vertex.m_Vertexs) {
			if(counter < 3) {
				vertexs[counter ++] = h_Vertexs.get(i);
			}else if(counter == 3){
				temp = Mesh_Math.CalNormalVector(vertexs[0], vertexs[1], vertexs[2]);
				if( !Mesh_Math.perpendicular(h_Vertexs.get(i).m_point , vertexs[0].m_point ,  temp)) {
					return false;
				}
			}else {
				if( !Mesh_Math.perpendicular(h_Vertexs.get(i).m_point , vertexs[0].m_point ,  temp)) {
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * times次数重载
	 */
	public void Laplacian(String fileName , int times) throws IOException{
		Map<Integer , Vertex> Vertexs = null;
		for(int i = 0 ; i < times ; i ++) {
			Vertexs = Laplacian();
		}
		writeFile( Vertexs , fileName);
	}
	
	//Laplacian 平滑技术
	public Map<Integer , Vertex> Laplacian() throws IOException {
		Map<Integer , Vertex> Vertexs = new HashMap<Integer , Vertex>();
		for(int i : vertexs_opt) {
			Vertex item = new Vertex(i);
			double sum_X = 0.0 , sum_Y = 0.0 ,sum_Z = 0.0;
			/*
			 * 只是根据邻接点求得 该点坐标，并未把该点本身算入
			 */
			for(int j : h_Vertexs.get(i).m_Vertexs ) {
				sum_X += getVertex_X(j);
				sum_Y += getVertex_Y(j);
				sum_Z += getVertex_Z(j);
			}
			int size = h_Vertexs.get(i).m_Vertexs.size();
			item.m_point.setX(sum_X / size);
			item.m_point.setY(sum_Y / size);
			item.m_point.setZ(sum_Z / size);
			
			Vertexs.put(i, item);
		}
		return Vertexs;
		
		
	}
	
	public void Laplacian_distance(String fileName ,int times) throws IOException{
		Map<Integer , Vertex> Vertexs = null;
		for(int i = 0 ; i < times ; i ++) {
			Vertexs = Laplacian_distance();
		}
		writeFile(Vertexs , fileName);
	}
	/*
	 * 举例加权矩阵
	 */
	public Map<Integer , Vertex>  Laplacian_distance() throws IOException {
		Map<Integer , Vertex> Vertexs = new HashMap<Integer , Vertex>();
		for(int i : vertexs_opt) {
			Vertex item = new Vertex(i);
			double sum_X = 0.0 , sum_Y = 0.0 ,sum_Z = 0.0;
			/*
			 * 只是根据邻接点求得 该点坐标，并未把该点本身算入
			 */
			double sum = 0.0;
			for(int j : h_Vertexs.get(i).m_Vertexs ) {
				double distance = Mesh_Math.computeDistance(h_Vertexs.get(i).m_point , h_Vertexs.get(j).m_point );
				sum += distance;
				sum_X += ( getVertex_X(j) - h_Vertexs.get(i).m_point.getX() ) * distance;
				sum_Y += ( getVertex_Y(j) - h_Vertexs.get(i).m_point.getY() ) * distance;
				sum_Z += ( getVertex_Z(j) - h_Vertexs.get(i).m_point.getZ() ) * distance;
			}
			item.m_point.setX(sum_X / sum + h_Vertexs.get(i).m_point.getX());
			item.m_point.setY(sum_Y / sum + h_Vertexs.get(i).m_point.getY());
			item.m_point.setZ(sum_Z / sum + h_Vertexs.get(i).m_point.getZ());
			
			Vertexs.put(i, item);
			
		}
		return Vertexs;
	}
	/*
	 * times次数重载
	 */
	public void HCLaplacian(String fileName , int times) throws IOException{
		Map<Integer , Vertex> Vertexs = null;
		for(int i = 0 ; i < times ; i ++) {
			Vertexs = HCLaplacian(index);
			Vertexs = HCLaplacian(index);
		}
		writeFile( Vertexs , fileName);
	}
	
	//Laplacian 平滑技术
	public Map<Integer , Vertex> HCLaplacian(double tmp) throws IOException {
		Map<Integer , Vertex> Vertexs = new HashMap<Integer , Vertex>();
		for(int i : vertexs_opt) {
			Vertex item = new Vertex(i);
			double sum_X = 0.0 , sum_Y = 0.0 ,sum_Z = 0.0;
			/*
			 * 只是根据邻接点求得 该点坐标，并未把该点本身算入
			 */
			for(int j : h_Vertexs.get(i).m_Vertexs ) {
				sum_X += getVertex_X(j) * tmp;
				sum_Y += getVertex_Y(j) * tmp;
				sum_Z += getVertex_Z(j) * tmp;
			}
			int size = h_Vertexs.get(i).m_Vertexs.size();
			item.m_point.setX(sum_X / size + h_Vertexs.get(i).m_point.getX());
			item.m_point.setY(sum_Y / size + h_Vertexs.get(i).m_point.getX());
			item.m_point.setZ(sum_Z / size + h_Vertexs.get(i).m_point.getX());
			
			Vertexs.put(i, item);
		}
		return Vertexs;
		
		
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
		/*
		 * 
		 */
//		Vertexfilter();
		virvalFilfer();
		
		
		System.out.println("read succeed !!!");
		System.out.println("test data !!!");
		
	}
	public void writeFile(Map<Integer , Vertex> vertexs , String fileName) throws IOException {
		writeData wData = new writeData();
		wData.writeFile(this , vertexs, fileName);
	}
	public void writeFileFirst() throws IOException {
		writeData wData = new writeData();
		wData.writeFileFirst(this , "/Users/liuhongbin/Documents/Documents/毕业论文/Data/outputfirst.hex");
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
