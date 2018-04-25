import java.io.IOException;

import Mesh.CQuad;
import Mesh.CVertex;
import fromData.fromFile;
import fromData.getData;

public class test {
	public static void main(String args[]) throws IOException {
		getData demo = new getData();
		demo.Do(new fromFile().ReadFile());
		for(CVertex vertex : demo.getVertexs()) {
			System.out.print("vertex   ");
			System.out.println(vertex);
		}
		for(CQuad quad : demo.getCQuads()) {
			System.out.print("quad     ");
			System.out.println(quad);
		}
	}
}
