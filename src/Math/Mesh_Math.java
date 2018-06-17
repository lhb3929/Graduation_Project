package Math;

import Geometry.CPoint;
import Mesh.Equation;
import Mesh.HalfFace;
import Mesh.Hex;
import Mesh.Vertex;

public class Mesh_Math {
	
	/*
	 * 参数半面求解半面法向量
	 */
	public static Equation CalNormalVector (HalfFace halfFace) {
		return CalNormalVector(halfFace.Vertexs[0] , halfFace.Vertexs[1] , halfFace.Vertexs[2]);
	}
	/*
	 * 参数三个点（按顺序的三个点） ，使用右手法则求出 三点确定平面的	法向量
	 */
	public static Equation CalNormalVector(Vertex v1 , Vertex v2  , Vertex v3) {
		
		double a1 = v2.m_point.getX() -  v1.m_point.getX();
		double a2 = v2.m_point.getY() -  v1.m_point.getY();
		double a3 = v2.m_point.getZ() -  v1.m_point.getZ();
		
		double b1 = v3.m_point.getX() -  v2.m_point.getX();
		double b2 = v3.m_point.getY() -  v2.m_point.getY();
		double b3 = v3.m_point.getZ() -  v2.m_point.getZ();
		
		double a = a2 * b3 - a3 * b2;
		double b = a3 * b1 - a1 * b3;
		double c = a1 * b2 - a2 * b1;
		double d = (v2.m_point.getX() * a + v2.m_point.getY() * b + v2.m_point.getZ() * c ) * (-1);
		Equation cp = new Equation( a , b , c , d);
		return cp;
	}
	public static boolean perpendicular( Equation equation , Vertex item) {
//		double temp = (p1.getX() - p0.getX()) * p2.getX() + (p1.getY() - p0.getY()) * p2.getY() + (p1.getZ() - p0.getZ()) * p2.getZ();
		if(equation.isPanel(item)) {
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
	
	public static void test() {
		Vertex v1 = new Vertex(0,	 new CPoint(1, 2, 0));
		Vertex v2 = new Vertex(0,	 new CPoint(2, 2, 0));
		Vertex v3 = new Vertex(0,	 new CPoint(1, 1, 1));
		Vertex v4 = new Vertex(0,	 new CPoint(112312312, 2121, 0));
		
		Equation equation = Mesh_Math.CalNormalVector(v1, v2, v3);
		if(Mesh_Math.perpendicular(equation, v4)) {
			System.out.println("true");
		}else {
			System.out.println("false");
		}
	}
	
	
}
