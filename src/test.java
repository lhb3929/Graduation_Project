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
		demo.writeFileFirst();
//		demo.Laplacian("/Users/liuhongbin/Documents/Documents/毕业论文/Data/Laplacian.hex",1);
		demo.Laplacian_distance("/Users/liuhongbin/Documents/Documents/毕业论文/Data/Laplacian_distance.hex",1);
		demo.HCLaplacian("/Users/liuhongbin/Documents/Documents/毕业论文/Data/HCLaplacian.hex",1);
//		demo.Laplacian("/Users/liuhongbin/Documents/Documents/毕业论文/Data/Laplacian2.hex", 5);
		demo.Laplacian_distance("/Users/liuhongbin/Documents/Documents/毕业论文/Data/Laplacian3.hex", 5);
		
	}
}
