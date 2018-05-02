package Geometry;

public class CPoint {
	/*
	 *  \f$(x,y,z)\f$ value
	 */
   public double[] v = new double[3];
   
   public CPoint(double x , double y , double z) {
	   v[0] = x;
	   v[1] = y;
	   v[2] = z;
   }
   public CPoint() {
	   this(0, 0, 0);
   }
   
   public double getX() {
	   return v[0];
   }
   public double getY() {
	   return v[1];
   }
   public double getZ() {
	   return v[2];
   }
   public void setX(double item) {
	   v[0] = item;
   }
   public void setY(double item) {
	   v[1] = item;
   }
   public void setZ(double item) {
	   v[2] = item;
   }
   /*
    * 四则运算，也可以修改成 return Point
    */
   public void add(CPoint p) {
	   this.v[0] += p.getX();
	   this.v[1] += p.getY();
	   this.v[2] += p.getZ();
   }
   public void minus(CPoint p) {
	   this.v[0] -= p.getX();
	   this.v[1] -= p.getY();
	   this.v[2] -= p.getZ();
   }
   public void multiply(double s) {
	   this.v[0] *= s;
	   this.v[1] *= s;
	   this.v[2] *= s;
   }
   public void divide(double s) {
	   this.v[0] /= s;
	   this.v[1] /= s;
	   this.v[2] /= s;
   }
   public void multiply(CPoint p) {
	   this.v[0] *= p.getX();
	   this.v[1] *= p.getY();
	   this.v[2] *= p.getZ();
   }
   
   
   @Override
   public boolean equals(Object obj) {
	   if(obj == null || obj instanceof CPoint) {
		   return false;
	   }else {
		   CPoint item = (CPoint) obj;
		   if(getX() == item.getX() && getY() == item.getY() && getZ() == item.getZ()) {
			   return true;
		   }else {
			return false;
		}
	   }
   }
   
}
