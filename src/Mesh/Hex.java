package Mesh;
/*
 * 六面体数据结构
 * 
 * 由于只是学术项目， 所以实例变量的权限  我设置为 public  便于修改和访问
 */
public class Hex {
	
	public int id;
	public HalfFace halfFace;
	/*
	 * 顶点数组一般不会应用，一般都是使用int[] 然后在网格内的  map  通过id 获取对应vertex 
	 */
	public Vertex[] vertexs;
	//public CVertex[] vertexs = new CVertex[8] ;
	
	public int[] vertex_ids = new int[8];
	
	public Hex(int m_Id , HalfFace m_HalfFace , Vertex[] m_Vertexs) {
		id = m_Id;
		halfFace = m_HalfFace;
		vertexs = m_Vertexs;
	}
	public Hex(int m_Id) {
		this(m_Id, null, null);
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Integer.toString(id));
		stringBuilder.append("( ");
		stringBuilder.append(Integer.toString(vertex_ids[0]));
		stringBuilder.append(" , ");
		stringBuilder.append(Integer.toString(vertex_ids[1]));
		stringBuilder.append(" , ");
		stringBuilder.append(Integer.toString(vertex_ids[2]));
		stringBuilder.append(" , ");
		stringBuilder.append(Integer.toString(vertex_ids[3]));
		stringBuilder.append(" , ");
		stringBuilder.append(Integer.toString(vertex_ids[4]));
		stringBuilder.append(" , ");
		stringBuilder.append(Integer.toString(vertex_ids[5]));
		stringBuilder.append(" , ");
		stringBuilder.append(Integer.toString(vertex_ids[6]));
		stringBuilder.append(" , ");
		stringBuilder.append(Integer.toString(vertex_ids[7]));
		stringBuilder.append(" )");
		return stringBuilder.toString();
	}
	
	
	
}
