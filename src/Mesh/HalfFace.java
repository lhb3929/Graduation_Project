package Mesh;
/*
 * 个人设计的表面数据结构
 * 因为一个 hex的八个顶点是 确定（即，八个顶点的相对关系的确定的）的 所以它六个面的相对关系也应该是确定的
 */
public class HalfFace {
	
	//每一个半面的编号  不是 一个id ；而是  hex_id  + "_" + 编号 。
	//即为每个六面体的编号加上 这个半面在六面体的编号
	public String s_id;
	
	//有顺序的四个顶点，顶点之间的顺序为  逆时针，用于求法向量
	public Vertex[] Vertexs = new Vertex[4];
	
	public Hex hex;
	
	//因为顺序规定，所以当读入六面体八个点的时候，halfFace的前后对应关系也形成了
	public HalfFace preHalfFace;
	public HalfFace nextHalfFace;
	
	public HalfFace(String m_id , Vertex[] m_Vertexs , Hex m_hex , HalfFace m_preHalfFace , HalfFace m_nextHalfFace) {
		s_id = m_id;
		Vertexs = m_Vertexs;
		hex = m_hex;
		preHalfFace = m_preHalfFace;
		nextHalfFace = m_nextHalfFace;
	}
	public HalfFace(String m_id , Vertex[] m_Vertexs , Hex m_hex) {
		this(m_id, m_Vertexs, m_hex, null, null);
	}
	public HalfFace(String m_id) {
		this(m_id, null, null , null , null);
	}
	
	
	public Vertex nextVertex( Vertex item ) {
		if( item == null ) {
			return null;
		}else {
			for(int i = 0 ; i < Vertexs.length ; i ++ ) {
				if( item.equals(Vertexs[i]) ) {
					//得到后一个结点
					return Vertexs[ (i + 1) % 4];
				}
			}
			return null;
		}
	}
	
	public Vertex preVertex(Vertex item) {
		if( item == null ) {
			return null;
		}else {
			for(int i = 0 ; i < Vertexs.length ; i ++ ) {
				if( item.equals(Vertexs[i]) ) {
					//得到前一个结点
					return Vertexs[ (i + 3) % 4];
				}
			}
			return null;
		}
	}
	
}
