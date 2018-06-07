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
	public List<CQuad> quads = new ArrayList<CQuad>(); //辅助记录四边形集合
	
	public Set<Integer> vertexs_opt = new HashSet<Integer>();  //可优化点
	Map<Integer , Vertex> map_outVertexs = new HashMap<Integer , Vertex>();  //外部结点的邻域都在表面的集合
	
	public Map<Integer , Vertex> n_Vertexs = new HashMap<Integer , Vertex>();
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
	public void opt_Filfer() {
		int[] arr = {17661, 37198, 37209, 37210, 37211, 37212, 37213, 37225, 37226, 37227, 37228, 37229, 37241, 37242, 37243, 37244, 37245, 37257, 37258, 37259, 37260, 37261, 37273, 37274, 37275, 37276, 37277, 37289, 37290, 37291, 37292, 37293, 37305, 37306, 37307, 37308, 37309, 37321, 37322, 37323, 37324, 37325, 37337, 37338, 37339, 37340, 37341, 37353, 37354, 37355, 37356, 37357, 37369, 37370, 37371, 37372, 37373, 37385, 37386, 37387, 37388, 37389, 37401, 37402, 37403, 37404, 37405, 37417, 37418, 37419, 37420, 37421, 37426, 37433, 37434, 37435, 37436, 37437, 37438, 37439, 37440, 37447, 37448, 37449, 37450, 37451, 37452, 37463, 37464, 37465, 37466, 37467, 37468, 37479, 37480, 37481, 37482, 37483, 37484, 37495, 37496, 37497, 37498, 37499, 37500, 37511, 37512, 37513, 37514, 37515, 37516, 37527, 37528, 37529, 37530, 37531, 37532, 37543, 37544, 37545, 37546, 37547, 37548, 37559, 37560, 37561, 37562, 37563, 37564, 37575, 37576, 37577, 37578, 37579, 37580, 37591, 37592, 37593, 37594, 37595, 37596, 37607, 37608, 37609, 37610, 37611, 37612, 37623, 37624, 37625, 37626, 37627, 37628, 37639, 37640, 37641, 37642, 37643, 37644, 37655, 37656, 37657, 37658, 37659, 37660, 37671, 37672, 37673, 37674, 37675, 37676, 37677, 37678, 37687, 37688, 37689, 37690, 37691};
		for(int i : arr ) {
			vertexs_opt.add(i);
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

		/*
		 * 使用一种跟优化的数据结构来空间置换时间
		 */
//		Map<Integer, List<CQuad>> map_cquads = new HashMap<Integer , List<CQuad>>();
//		for(int i = 0 ; i < quads.size() ; i ++) {
//			if(map_cquads.get(quads.get(i).vertexs[0] + quads.get(i).vertexs[1] + quads.get(i).vertexs[2] + quads.get(i).vertexs[3]) == null) {
//				map_cquads.put(quads.get(i).vertexs[0] + quads.get(i).vertexs[1] + quads.get(i).vertexs[2] + quads.get(i).vertexs[3],new ArrayList<CQuad>());
//			}
//			map_cquads.get(quads.get(i).vertexs[0] + quads.get(i).vertexs[1] + quads.get(i).vertexs[2] + quads.get(i).vertexs[3]).add(quads.get(i));
//		}
//		/*
//		 * 输出计数器
//		 */
//		int counter = 0;
//		for(Map.Entry<Integer, List<CQuad>> entry : map_cquads.entrySet()) {
//			if(entry.getValue().size() == 1) {
//				t_Quads_out.add(entry.getValue().get(0));
////				System.out.println("out" );
//			}else {
//				for(int i = 0 ; i < entry.getValue().size() ; i ++ ) {
//					boolean flag = true;
//					for(int j = 0 ; j < entry.getValue().size() && flag; j ++ ) {
//						if( entry.getValue().get(j).equals(entry.getValue().get(i)) ) {
//							t_Quads_in.add(entry.getValue().get(i));
////							System.out.println("in" + Integer.toString(entry.getKey()));
//							flag = false;
//						}
//					}
//					if(flag) {
//						t_Quads_out.add(entry.getValue().get(i));
////						System.out.println("out" + Integer.toString(entry.getKey()));
//					}
//					
//				}
//			}
//			counter ++;
//			if(counter % 1000 == 0) {
//				System.out.println("do   " + Integer.toString(counter ++ ));
//			}
//			
//		}
		
		/*
		 * 遍历所有面，能够直接加入到in面集合中的为内部面，如果不能加入则已存在过一次，则加入到外部面中
		 */
		int counter = 1;
		for( CQuad quad : quads ) {
			boolean flag = true;
			for(CQuad out : t_Quads_out) {
				if(out.equals(quad) && flag) {
					t_Quads_in.add(quad);
					flag = false;
					break;
				}
			}
			if(flag) {
				t_Quads_out.add(quad);
			}
			
			if(counter++ % 1000 == 0) {
				System.out.println(Integer.toString(counter) + " / " + quads.size());
			}
		}
		
		for(CQuad in : t_Quads_in ) {
			inVertexs.add(in.vertexs[0]);
			inVertexs.add(in.vertexs[1]);
			inVertexs.add(in.vertexs[2]);
			inVertexs.add(in.vertexs[3]);
		}
		for(CQuad out : t_Quads_out ) {
			outVertexs.add(out.vertexs[0]);
			outVertexs.add(out.vertexs[1]);
			outVertexs.add(out.vertexs[2]);
			outVertexs.add(out.vertexs[3]);
		}
		/*
		 * 使用结束，释放空间，防止内存泄漏
		 */
//		t_Quads_in = null;
//		t_Quads_out = null;
		/*
		 * 初始化 n_Vertexs 数据结构
		 */
		for(int tmp : outVertexs) {
			Vertex vertex = n_Vertexs.get(tmp);
			for(int v : vertex.m_Vertexs) {
				if( !outVertexs.contains(v) && inVertexs.contains(v)) {
					vertex.m_Vertexs.remove(v);
				}
			}
		}
		/*
		 * 内部结点皆可优化
		 */
//		for(int i : inVertexs) {
//			vertexs_opt.add(i);
//		}
		for(int i = 0 ; i < h_Vertexs.size() ; i ++) {
			vertexs_opt.add(i);
		}
		/*
		 * 从外部结点中筛选出可优化的
		 */
		for(int i : outVertexs) {
			Vertex vertex_tmp = new Vertex(i);
			for(int j : h_Vertexs.get(i).m_Vertexs) {
				if( outVertexs.contains(j) ) {
					vertex_tmp.m_Vertexs.add(j);
				}else {
					System.out.println("结点 " + Integer.toString(j) + " 不在表面" );
				}
			}
			map_outVertexs.put(i, vertex_tmp);
		}
		for(int i : outVertexs) {
			if(!isPlane(map_outVertexs , i)) {
				vertexs_opt.remove(i);
				System.out.println(Integer.toString(i) + "  is not plane !!");
			}
		}
		System.out.println("filter succeed !!!");
	}
	/*
	 * 判断节点和邻域节点是否在同一平面上 
	 */
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
	 *  map_vertex .clone()
	 */
	public void init_vertexs(Map<Integer , Vertex> item , Map<Integer , Vertex> tmp) {
		for(Map.Entry<Integer, Vertex> entry : tmp.entrySet()) {
			item.put(entry.getKey(), entry.getValue());
		}
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
		Vertexs = null;
	}
	
	//Laplacian 平滑技术
	public Map<Integer , Vertex> Laplacian() throws IOException {
		Map<Integer , Vertex> Vertexs = new HashMap<Integer , Vertex>();
		init_vertexs(Vertexs , n_Vertexs);
		for(int i : vertexs_opt) {
			Vertex item = new Vertex(i);
			double sum_X = 0.0 , sum_Y = 0.0 ,sum_Z = 0.0;
			/*
			 * 只是根据邻接点求得 该点坐标，并未把该点本身算入
			 */
			for(int j : n_Vertexs.get(i).m_Vertexs ) {
				sum_X += getVertex_X(j);
				sum_Y += getVertex_Y(j);
				sum_Z += getVertex_Z(j);
			}
			int size = n_Vertexs.get(i).m_Vertexs.size();
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
		Vertexs = null;
	}
	/*
	 * 举例加权矩阵
	 */
	public Map<Integer , Vertex>  Laplacian_distance() throws IOException {
		Map<Integer , Vertex> Vertexs = new HashMap<Integer , Vertex>();
		init_vertexs(Vertexs , n_Vertexs);
		for(int i : vertexs_opt) {
			Vertex item = new Vertex(i);
			double sum_X = 0.0 , sum_Y = 0.0 ,sum_Z = 0.0;
			/*
			 * 只是根据邻接点求得 该点坐标，并未把该点本身算入
			 */
			double sum = 0.0;
			for(int j : n_Vertexs.get(i).m_Vertexs ) {
				double distance = Mesh_Math.computeDistance(n_Vertexs.get(i).m_point , n_Vertexs.get(j).m_point );
				sum += distance;
				sum_X += ( getVertex_X(j) - n_Vertexs.get(i).m_point.getX() ) * distance;
				sum_Y += ( getVertex_Y(j) - n_Vertexs.get(i).m_point.getY() ) * distance;
				sum_Z += ( getVertex_Z(j) - n_Vertexs.get(i).m_point.getZ() ) * distance;
			}
			item.m_point.setX(sum_X / sum + n_Vertexs.get(i).m_point.getX());
			item.m_point.setY(sum_Y / sum + n_Vertexs.get(i).m_point.getY());
			item.m_point.setZ(sum_Z / sum + n_Vertexs.get(i).m_point.getZ());
			
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
			//正负两次
			Vertexs = HCLaplacian(index);
			Vertexs = HCLaplacian(index * -1);
		}
		writeFile( Vertexs , fileName);
		
		Vertexs = null;
	}
	
	//Laplacian 平滑技术
	public Map<Integer , Vertex> HCLaplacian(double tmp) throws IOException {
		Map<Integer , Vertex> Vertexs = new HashMap<Integer , Vertex>();
		init_vertexs(Vertexs , n_Vertexs);
		for(int i : vertexs_opt) {
			Vertex item = new Vertex(i);
			double sum_X = 0.0 , sum_Y = 0.0 ,sum_Z = 0.0;
			/*
			 * 只是根据邻接点求得 该点坐标，并未把该点本身算入
			 */
			for(int j : n_Vertexs.get(i).m_Vertexs ) {
				sum_X += getVertex_X(j) * tmp;
				sum_Y += getVertex_Y(j) * tmp;
				sum_Z += getVertex_Z(j) * tmp;
			}
			int size = n_Vertexs.get(i).m_Vertexs.size();
			item.m_point.setX(sum_X / size + n_Vertexs.get(i).m_point.getX());
			item.m_point.setY(sum_Y / size + n_Vertexs.get(i).m_point.getX());
			item.m_point.setZ(sum_Z / size + n_Vertexs.get(i).m_point.getX());
			
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
			
			//---
			n_Vertexs.get(v0).m_Vertexs.add(v1);
			n_Vertexs.get(v0).m_Vertexs.add(v3);
			n_Vertexs.get(v0).m_Vertexs.add(v4);
			
			n_Vertexs.get(v1).m_Vertexs.add(v0);
			n_Vertexs.get(v1).m_Vertexs.add(v2);
			n_Vertexs.get(v1).m_Vertexs.add(v5);
			
			n_Vertexs.get(v2).m_Vertexs.add(v1);
			n_Vertexs.get(v2).m_Vertexs.add(v3);
			n_Vertexs.get(v2).m_Vertexs.add(v6);
			
			n_Vertexs.get(v3).m_Vertexs.add(v0);
			n_Vertexs.get(v3).m_Vertexs.add(v2);
			n_Vertexs.get(v3).m_Vertexs.add(v7);
			
			n_Vertexs.get(v4).m_Vertexs.add(v0);
			n_Vertexs.get(v4).m_Vertexs.add(v5);
			n_Vertexs.get(v4).m_Vertexs.add(v7);
			
			n_Vertexs.get(v5).m_Vertexs.add(v4);
			n_Vertexs.get(v5).m_Vertexs.add(v6);
			n_Vertexs.get(v5).m_Vertexs.add(v7);
			
			n_Vertexs.get(v6).m_Vertexs.add(v2);
			n_Vertexs.get(v6).m_Vertexs.add(v5);
			n_Vertexs.get(v6).m_Vertexs.add(v7);
			
			n_Vertexs.get(v7).m_Vertexs.add(v3);
			n_Vertexs.get(v7).m_Vertexs.add(v4);
			n_Vertexs.get(v7).m_Vertexs.add(v6);
		}
		/*
		 * -----------------------------------------------------------------------------过滤方式调控点----------------------
		 */
//		Vertexfilter();
//		virvalFilfer();
		opt_Filfer();
		
		
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
		n_Vertexs.put(v.m_id , v);
	}
	public void addHalfFace(HalfFace hf) {
		h_HalfFaces.put(hf.s_id, hf);
	}
	public void addHex(Hex h) {
		h_Hexs.put(h.id, h);
	}
}
