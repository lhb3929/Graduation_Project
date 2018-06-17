import java.io.IOException;

import Mesh.HexMesh;

public class test {
	public static void main(String args[]) throws IOException {
		
		HexMesh demo = new HexMesh();
		demo.readData();
		demo.writeFileFirst();
		
		demo.Laplacian("/Users/liuhongbin/Documents/Documents/毕业论文/Data/Laplacian.hex",1);
		demo.Laplacian_distance("/Users/liuhongbin/Documents/Documents/毕业论文/Data/Laplacian_distance.hex",1);
		demo.HCLaplacian("/Users/liuhongbin/Documents/Documents/毕业论文/Data/HCLaplacian.hex",1);
		/*
		 * 迭代算法
		 */
		demo.Laplacian("/Users/liuhongbin/Documents/Documents/毕业论文/Data/Laplacian2.hex", 5);
		demo.Laplacian_distance("/Users/liuhongbin/Documents/Documents/毕业论文/Data/Laplacian3.hex", 5);
		
		
		
		
		
	}
}
