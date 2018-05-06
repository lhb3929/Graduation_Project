import java.io.File;
import java.io.IOException;

import Mesh.HexMesh;

public class test {
	public static void main(String args[]) throws IOException {
		
//		HexMesh demo = new HexMesh();
//		demo.readData();
//		demo.writeFileFirst();
//		demo.Laplacian("/Users/liuhongbin/Documents/Documents/毕业论文/Data/Laplacian.hex",1);
//		demo.Laplacian_distance("/Users/liuhongbin/Documents/Documents/毕业论文/Data/Laplacian_distance.hex",1);
//		demo.HCLaplacian("/Users/liuhongbin/Documents/Documents/毕业论文/Data/HCLaplacian.hex",1);
//		demo.Laplacian("/Users/liuhongbin/Documents/Documents/毕业论文/Data/Laplacian2.hex", 5);
//		demo.Laplacian_distance("/Users/liuhongbin/Documents/Documents/毕业论文/Data/Laplacian3.hex", 5);
		
//		init_file("/Users/liuhongbin/Desktop/file_test.hex");
		mkdir("/Users/liuhongbin/Desktop/file_test");
		
	}
	public static boolean rm(String fileName) {
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
	public static boolean mkdir(String fileName) throws IOException {
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
	public static void init_file(String fileName) throws IOException {
		rm(fileName);
		mkdir(fileName);
	}
}
