/**
 * @author ：ZXY
 * @date ：Created in 2020/7/31 16:11
 * @description：
 */

import java.io.*;
import java.util.*;

public class test1 {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        //String input = sc.next();

        String input="-3+6-(2*4)=4+2-3+2*3+x";

        String[] a = input.split("=");  // 方程式以 = 分割


        String[] b = a[0].split("\\+");  // 等号左边的以 + 分割
        List<String> list = new ArrayList<String>();
        for (int j = 0; j < b.length; j++) {
            String[] c = b[j].split("-"); // 以 - 分割
            //
            list.add(c[0]);
            for (int k = 1; k < c.length; k++) {
                c[k] = "-" + c[k];
                list.add(c[k]);
            }
        }

        String[] d = a[1].split("\\+");  // 等号右边的以 + 分割
        for (int j = 0; j < d.length; j++) {
            String[] e = d[j].split("-");  // 再以 - 分割
            //
            list.add("-" + e[0]);
            for (int k = 1; k < e.length; k++) {
                e[k] = "+" + e[k];
                list.add(e[k]);
            }
        }

        double cSum = 0; // 所有常数的和
        double xSum = 0; // 所有系数的和
        double changshu=0.0;
        double xishu=0;
        for (int i = 0; i < list.size(); i++) {
            String f = list.get(i);
            if (f.indexOf('x') == -1) { // 常数
                changshu = Double.valueOf(f);
                cSum += changshu;
            } else { // 如果含有x，就去掉x，求出系数的和
                // 对 x 和 -x 特别处理
                xishu = 0.0;
                if ("x".equals(f)) {
                    xishu = 1.0f;
                } else if ("-x".equals(f)) {
                    xishu = -1.0f;
                } else {
                    xishu = Double.valueOf(f.replace("x", ""));
                }
                xSum += xishu;
            }
        }
        if (cSum == 0 && xSum == 0) {
            System.out.println("Infinite solutions");
        } else if (cSum != 0 && xSum == 0) {
            System.out.println("No solution");
        } else {
            double jieguo = cSum / xSum * (-1);  // 常数除以系数乘以-1，就得到方程式的解
            System.out.println("x=" + jieguo);
        }
    }
}