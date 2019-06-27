package com.example.demo.util;

import com.example.demo.vo.Circle;
import com.example.demo.vo.Gps;
import com.example.demo.vo.Polygon;
import com.example.demo.vo.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 *  用于判断进出区的java代码
 */
public class GeoUtil {

    /**
     * 地球半径
     */
    private static double EARTHRADIUS = 6370996.81;

    /***
     * 判断点是否在多边形内
     * @param point
     * @param polygon
     * @return
     */
    public static boolean isPointInPolygon(Gps point, Polygon polygon) {

        List<Gps> pts = polygon.getPath();

        int N = pts.size();
        boolean boundOrVertex = true;
        //cross points count of x
        int intersectCount = 0;
        //浮点类型计算时候与0比较时候的容差
        double precision = 2e-10;
        //neighbour bound vertices
        Gps p1, p2;
        //测试点
        Gps p = point;

        p1 = pts.get(0);
        for (int i = 0; i <= N; i++) {
            if (p.equals(p1)) {
                return boundOrVertex;
            }

            p2 = pts.get(i % N);
            if (p.getLat() < Math.min(p1.getLat(), p2.getLat()) || p.getLat() > Math.max(p1.getLat(), p2.getLat())) {
                p1 = p2;
                continue;
            }

            if (p.getLat() > Math.min(p1.getLat(), p2.getLat()) && p.getLat() < Math.max(p1.getLat(), p2.getLat())) {
                //ray is crossing over by the algorithm (common part of)
                if (p.getLng() <= Math.max(p1.getLng(), p2.getLng())) {
                    //x is before of ray
                    if (p1.getLat() == p2.getLat() && p.getLng() >= Math.min(p1.getLng(), p2.getLng())) {
                        //overlies on a horizontal ray
                        return boundOrVertex;
                    }

                    if (p1.getLng() == p2.getLng()) {
                        //ray is vertical
                        if (p1.getLng() == p.getLng()) {
                            //overlies on a vertical ray
                            return boundOrVertex;
                        } else {
                            //before ray
                            ++intersectCount;
                        }
                    } else {
                        //cross point on the left side
                        //cross point of getLng()
                        double xinters = (p.getLat() - p1.getLat()) * (p2.getLng() - p1.getLng()) / (p2.getLat() - p1.getLat()) + p1.getLng();
                        if (Math.abs(p.getLng() - xinters) < precision) {
                            //overlies on a ray
                            return boundOrVertex;
                        }

                        if (p.getLng() < xinters) {
                            //before ray
                            ++intersectCount;
                        }
                    }
                }
            } else {
                //special case when ray is crossing through the vertex
                if (p.getLat() == p2.getLat() && p.getLng() <= p2.getLng()) {
                    //p crossing over p2
                    //next vertex
                    Gps p3 = pts.get((i + 1) % N);
                    if (p.getLat() >= Math.min(p1.getLat(), p3.getLat()) && p.getLat() <= Math.max(p1.getLat(), p3.getLat())) {
                        //p.getLat() lies between p1.getLat() & p3.getLat()
                        ++intersectCount;
                    } else {
                        intersectCount += 2;
                    }
                }
            }
            //next ray left point
            p1 = p2;
        }

        if (intersectCount % 2 == 0) {
            //偶数在多边形外
            return false;
        } else {
            //奇数在多边形内
            return true;
        }
    }

    /***
     * 判断点是否在矩形内
     * @param gps 判断点
     * @param rectangle 矩形
     * @return
     */

    public static boolean isPointInRect(Gps gps, Rectangle rectangle) {
        //西南脚点
        Gps sw = rectangle.getSouthWest();
        //东北脚点
        Gps ne = rectangle.getNorthEast();

        return gps.getLng() >= sw.getLng() && gps.getLng() <= ne.getLng()
                && gps.getLat() >= sw.getLat() && gps.getLat() <= ne.getLat();
    }

    /***
     * 判断点是否在圆内
     * @param gps 判断点
     * @param circle 圆形
     * @return
     */

    public static boolean isPointInCircle(Gps gps, Circle circle) {
        double dis = getDistance(gps, circle.getCenter());
        if (dis <= circle.getRadius()) {
            return true;
        } else {
            return false;
        }
    }

    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 通过经纬度获取距离(单位：米)
     *
     * @return
     */
    public static double getDistance1(Gps point1, Gps point2) {
        double radLat1 = rad(point1.getLat());
        double radLat2 = rad(point2.getLat());
        double a = radLat1 - radLat2;
        double b = rad(point1.getLng()) - rad(point2.getLng());
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

    /**
     * 计算两点之间的距离,两点坐标必须为经纬度
     *
     * @param {point1} Point 点对象
     * @param {point2} Point 点对象
     * @returns {Number} 两点之间距离，单位为米
     */
    public static double getDistance(Gps point1, Gps point2) {

        point1.setLng(_getLoop(point1.getLng(), -180, 180));
        point1.setLat(_getRange(point1.getLat(), -74, 74));
        point2.setLng(_getLoop(point2.getLng(), -180, 180));
        point2.setLat(_getRange(point2.getLat(), -74, 74));

        double x1, x2, y1, y2;
        x1 = degreeToRad(point1.getLng());
        y1 = degreeToRad(point1.getLat());
        x2 = degreeToRad(point2.getLng());
        y2 = degreeToRad(point2.getLat());

        return EARTHRADIUS * Math.acos((Math.sin(y1) * Math.sin(y2) + Math.cos(y1) * Math.cos(y2) * Math.cos(x2 - x1)));
    }

    /**
     * 将弧度转化为度
     *
     * @param {radian} Number 弧度
     * @returns {Number} 度
     */
    public static double radToDegree(double rad) {
        return (180 * rad) / Math.PI;
    }

    /**
     * 将度转化为弧度
     *
     * @param {degree} Number 度
     * @returns {Number} 弧度
     */
    public static double degreeToRad(double degree) {
        return Math.PI * degree / 180;
    }

    /**
     * 将v值限定在a,b之间，纬度使用
     */
    public static double _getRange(Double v, int a, int b) {
        if (a != 0) {
            v = Math.max(v, a);
        }
        if (b != 0) {
            v = Math.min(v, b);
        }
        return v;
    }

    /**
     * 将v值限定在a,b之间，经度使用
     */
    public static double _getLoop(Double v, int a, int b) {
        while (v > b) {
            v -= b - a;
        }
        while (v < a) {
            v += b - a;
        }
        return v;
    }

    public static List<Gps> buildList(String strGps) {
        List<Gps> list = new ArrayList<Gps>();
        String[] strs = strGps.split(";");
        for (String str : strs) {
            String[] gpsStr = str.split(",");
            Gps gps = new Gps(Double.valueOf(gpsStr[0]), Double.valueOf(gpsStr[1]));
            list.add(gps);
        }
        return list;
    }

    public static void main(String[] args) {
        String gpsStr = "116.301654,40.059709;116.404593,39.92456;116.304528,40.05739";
        for (String str : gpsStr.split(";")){
            String[] val = str.split(",");
            // 假设点
            Gps gps = new Gps(Double.parseDouble(val[0]), Double.parseDouble(val[1]));
            // 圆内圆外判断
            Circle circle = new Circle();
            circle.setCenter(new Gps(116.300396,40.060124));
            circle.setRadius(579.0213931731851);
            if (isPointInCircle(gps, circle)){
                System.out.println(str+"--------在圆内");
            }else{
                System.out.println(str+"--------在圆外");
            }
            // 矩形内外判断
            Rectangle rectangle = new Rectangle(buildList("116.301869,40.058853;116.315883,40.058853;116.315883,40.053441;116.301869,40.053441;"));

            if (isPointInRect(gps,rectangle)){
                System.out.println(str+"--------在矩形内");
            }else{
                System.out.println(str+"--------在矩形外");
            }
        }



    }
}
