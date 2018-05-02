package AboutData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	
	public void writeFile(HexMesh hm) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filePath)));
		Vertex[] vertexs = new Vertex[hm.h_Vertexs.size()];
		Hex[] hexs = new Hex[hm.h_Hexs.size()];
		for(Map.Entry<Integer, Vertex> entry : hm.h_Vertexs.entrySet()) {
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
		System.out.println("write succeed !!!");
		
	}
}
