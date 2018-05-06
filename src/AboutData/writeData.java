package AboutData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Mesh.Hex;
import Mesh.HexMesh;
import Mesh.Vertex;

public class writeData {
	// 默认数据文件位置  。。。。 文件更换时需修改的变量
	static final String defaultFilePath = "/Users/liuhongbin/Documents/Documents/毕业论文/Data/output.hex";
	
	// 代码读取文件的实际地址
	String filePath ;
	
	public writeData(String item) {
	    this.filePath = item;
	}
	public writeData() {
	    this(defaultFilePath);
	}
	public void writeFile(HexMesh hm , Map<Integer , Vertex> h_Vertexs) throws IOException {
		writeFile(hm , h_Vertexs , filePath);
	}
	
	public void writeFile(HexMesh hm , Map<Integer , Vertex> h_Vertexs , String fileName) throws IOException {
		/*
		 * 删除原来文件并创建新文件
		 */
		init_file(fileName);
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName)));
		Vertex[] vertexs = new Vertex[h_Vertexs.size()];
		Hex[] hexs = new Hex[hm.h_Hexs.size()];
		for(Map.Entry<Integer, Vertex> entry : h_Vertexs.entrySet()) {
			vertexs[entry.getKey()] = entry.getValue();
		}
		for(Map.Entry<Integer, Hex> entry : hm.h_Hexs.entrySet()) {
			hexs[entry.getKey()] = entry.getValue();
		}
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0 ; i < vertexs.length ; i ++) {
			stringBuilder.append("Vertex ");
			stringBuilder.append(Integer.toString(vertexs[i].m_id));
			stringBuilder.append(" ");
			stringBuilder.append(Double.toString(vertexs[i].m_point.getX()));
			stringBuilder.append(" ");
			stringBuilder.append(Double.toString(vertexs[i].m_point.getY()));
			stringBuilder.append(" ");
			stringBuilder.append(Double.toString(vertexs[i].m_point.getZ()));
			stringBuilder.append("\n");
		}
		for(int i = 0 ; i < hexs.length ; i ++ ) {
			stringBuilder.append("Hex ");
			stringBuilder.append(Integer.toString(hexs[i].id));
			stringBuilder.append(" ");
			stringBuilder.append(Integer.toString(hexs[i].vertex_ids[0]));
			stringBuilder.append(" ");
			stringBuilder.append(Integer.toString(hexs[i].vertex_ids[1]));
			stringBuilder.append(" ");
			stringBuilder.append(Integer.toString(hexs[i].vertex_ids[2]));
			stringBuilder.append(" ");
			stringBuilder.append(Integer.toString(hexs[i].vertex_ids[3]));
			stringBuilder.append(" ");
			stringBuilder.append(Integer.toString(hexs[i].vertex_ids[4]));
			stringBuilder.append(" ");
			stringBuilder.append(Integer.toString(hexs[i].vertex_ids[5]));
			stringBuilder.append(" ");
			stringBuilder.append(Integer.toString(hexs[i].vertex_ids[6]));
			stringBuilder.append(" ");
			stringBuilder.append(Integer.toString(hexs[i].vertex_ids[7]));
			stringBuilder.append("\n");
		}
		bw.write(stringBuilder.toString());
		
		bw.close();
		System.out.println(fileName + "write succeed !!!");
		
	}
	public void writeFileFirst(HexMesh hm , String fileName) throws IOException {
		/*
		 * 删除原来文件并创建新文件
		 */
		init_file(fileName);
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName)));
		
		Hex[] hexs = new Hex[hm.h_Hexs.size()];
		Map<Integer , Vertex> Vertexs = new HashMap<Integer , Vertex>();
		
		for(int i : hm.vertexs_opt) {
			Vertexs.put(i, hm.h_Vertexs.get(i));
		}
		Vertex[] vertexs = new Vertex[hm.h_Vertexs.size()];
		
		for(int i = 0 ; i < vertexs.length ; i ++ ) {
			vertexs[i] = null;
		}
		for(Map.Entry<Integer, Vertex> entry : Vertexs.entrySet()) {
			vertexs[entry.getKey()] = entry.getValue();
		}
		for(Map.Entry<Integer, Hex> entry : hm.h_Hexs.entrySet()) {
			hexs[entry.getKey()] = entry.getValue();
		}
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0 ; i < vertexs.length ; i ++) {
			if(vertexs[i] == null) {
				//
			}else {
				stringBuilder.append("Vertex ");
				stringBuilder.append(Integer.toString(vertexs[i].m_id));
				stringBuilder.append(" ");
				stringBuilder.append(Double.toString(vertexs[i].m_point.getX()));
				stringBuilder.append(" ");
				stringBuilder.append(Double.toString(vertexs[i].m_point.getY()));
				stringBuilder.append(" ");
				stringBuilder.append(Double.toString(vertexs[i].m_point.getZ()));
				stringBuilder.append("\n");
			}
		}
		for(int i = 0 ; i < hexs.length ; i ++ ) {
			stringBuilder.append("Hex ");
			stringBuilder.append(Integer.toString(hexs[i].id));
			stringBuilder.append(" ");
			stringBuilder.append(Integer.toString(hexs[i].vertex_ids[0]));
			stringBuilder.append(" ");
			stringBuilder.append(Integer.toString(hexs[i].vertex_ids[1]));
			stringBuilder.append(" ");
			stringBuilder.append(Integer.toString(hexs[i].vertex_ids[2]));
			stringBuilder.append(" ");
			stringBuilder.append(Integer.toString(hexs[i].vertex_ids[3]));
			stringBuilder.append(" ");
			stringBuilder.append(Integer.toString(hexs[i].vertex_ids[4]));
			stringBuilder.append(" ");
			stringBuilder.append(Integer.toString(hexs[i].vertex_ids[5]));
			stringBuilder.append(" ");
			stringBuilder.append(Integer.toString(hexs[i].vertex_ids[6]));
			stringBuilder.append(" ");
			stringBuilder.append(Integer.toString(hexs[i].vertex_ids[7]));
			stringBuilder.append("\n");
		}
		bw.write(stringBuilder.toString());
		
		bw.close();
		System.out.println(fileName + "write succeed !!!");
		
	}
	public boolean rm(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
	            if (file.delete()) {
	                System.out.println("删除单个文件" + fileName + "成功！");
	                return true;
	            } else {
	                System.out.println("删除单个文件" + fileName + "失败！");
	                return false;
	            }
	    } else {
	            System.out.println("删除单个文件失败：" + fileName + "不存在！");
	            return false;
	    }
	}
	public boolean mkdir(String fileName) throws IOException {
		File file = new File(fileName);
		if(!file.exists()) {
			if(file.createNewFile()) {
				System.out.println("创建单个文件" + fileName + "成功！");
				return true;
			}else {
				System.out.println("创建单个文件" + fileName + "成功！");
				return false;
			}
		}else {
			 System.out.println("创建单个文件失败：" + fileName + "已存在！");
	            return false;
		}
	}
	public void init_file(String fileName) throws IOException {
		rm(fileName);
		mkdir(fileName);
	}
}
