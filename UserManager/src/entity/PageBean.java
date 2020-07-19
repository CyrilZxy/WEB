package entity;

import java.util.List;

/**
 * @author ：ZXY
 * @date ：Created in 2020/7/15 18:53
 * @description： 分页对象
 */

public class PageBean<T> {
    private int totalCount;     //总记录数
    private int totalPage;      //总页码
    private List<T> list;       //每页中的数据
    private int currentPage;    //当前页码
    private int rows;            //每页记录数
    //get set toString 自己实现


    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", list=" + list +
                ", currentPage=" + currentPage +
                ", rows=" + rows +
                '}';
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
