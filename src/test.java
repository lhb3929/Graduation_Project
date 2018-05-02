import java.io.IOException;

import Mesh.HexMesh;

public class test {
	public static void main(String args[]) throws IOException {
//		getData demo = new getData();
//		demo.Do(new fromFile().ReadFile());
//		for(Vertex vertex : demo.getVertexs()) {
//			System.out.print("Vertex ");
//			System.out.println(vertex);
//		}
//		for(Hex hex : demo.getHexs()) {
//			System.out.print("Hex ");
//			System.out.println(hex);
//		}
		HexMesh demo = new HexMesh();
		demo.readData();
		demo.writeFile();
		
	}
}
