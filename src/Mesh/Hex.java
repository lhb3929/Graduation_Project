package Mesh;
/*
 * 六面体数据结构
 * 
 * 由于只是学术项目， 所以实例变量的权限  我设置为 public  便于修改和访问
 */
public class Hex {
	
	public int id;
	public HalfFace halfFace;
	public CVertex[] vertexs = new CVertex[8];
	
	public Hex(int m_Id , HalfFace m_HalfFace , CVertex[] m_Vertexs) {
		id = m_Id;
		halfFace = m_HalfFace;
		vertexs = m_Vertexs;
	}
	public Hex(int m_Id) {
		this(m_Id, null, null);
	}
	
	
	
	
	
}
