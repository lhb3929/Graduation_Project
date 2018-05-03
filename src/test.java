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
//		demo.writeFile();
//		if(new CQuad(1,2,3,4).equals(new CQuad(5,3,2,1)) ) {
//			System.out.println("true");
//		}else {
//			System.out.println("false");
//		}
	}
}
