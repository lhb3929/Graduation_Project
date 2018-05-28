package Math;

import Geometry.CPoint;
import Mesh.HalfFace;
import Mesh.Hex;
import Mesh.Vertex;

public class Mesh_Math {
	
	/*
	 * 参数半面求解半面法向量
	 */
	public static CPoint CalNormalVector (HalfFace halfFace) {
		return CalNormalVector(halfFace.Vertexs[0] , halfFace.Vertexs[1] , halfFace.Vertexs[2]);
	}
	/*
	 * 参数三个点（按顺序的三个点） ，使用右手法则求出 三点确定平面的	法向量
	 */
	public static CPoint CalNormalVector(Vertex v1 , Vertex v2  , Vertex v3) {
		
		double a1 = v2.m_point.getX() -  v1.m_point.getX();
		double a2 = v2.m_point.getY() -  v1.m_point.getY();
		double a3 = v2.m_point.getZ() -  v1.m_point.getZ();
		
		double b1 = v3.m_point.getX() -  v2.m_point.getX();
		double b2 = v3.m_point.getY() -  v2.m_point.getY();
		double b3 = v3.m_point.getZ() -  v2.m_point.getZ();
		CPoint cp = new CPoint(a2 * b3 - a3 * b2 , a3 * b1 - a1 * b3 , a1 * b2 - a2 * b1);
		return cp;
	}
	public static boolean perpendicular( CPoint p1 ,CPoint p0 ,  CPoint p2) {
		double temp = (p1.getX() - p0.getX()) * p2.getX() + (p1.getY() - p0.getY()) * p2.getY() + (p1.getZ() - p0.getZ()) * p2.getZ();
		if(temp == 0 ) {
			return true;
		}else {
			return false;
		}
	}
	/*
	 * 计算两点间距离
	 */
	public static double computeDistance(CPoint p1 , CPoint p2) {
		return Math.sqrt( Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2) + Math.pow(p1.getZ() - p2.getZ(), 2) );
	}
	/*
	 * 通过一个形状矩阵计算 ， 矩阵是否规则
	 */
	public double Jacobi(Hex hex) {
		
		return 0.0;
	}
	
	
}
