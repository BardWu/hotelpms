package com.networks.pms.common.util;

import java.util.List;

/**
 * 分页工具类
 * @author Administrator
 *
 */
public class Pager {

	private int curPage; //当前页
	private int pageSize;//每页多少行
	private int totalRow;//共多少行
	private int start;// 当前起始行
	private int end;//结束行
	private int totalPage;//共多少页
	
	/**
	 * 分页所需要的参数
	 * @param curPager	当前页
	 * @param pageSize	每页能显示多少行
	 * @param totalRow	数据量
	 */
	public Pager(int curPager, int pageSize, int totalRow) {
		this.setPageSize(pageSize);
		this.setCurPage(curPager);
		//this.setStart(this.getStart());
		this.setTotalRow(totalRow);
		
		calculate();
	}
	
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		if(curPage < 1){
			curPage = 1;
		}
		this.curPage = curPage;
		if (this.curPage != 0 && this.pageSize != 0 && this.totalRow != 0) {
			this.calculate();
		}
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if (this.curPage != 0 && this.pageSize != 0 && this.totalRow != 0) {
			this.calculate();
		}
	}
	public int getTotalRow() {
		return totalRow;
	}
	//计算共多少行
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
		if (this.curPage != 0 && this.pageSize != 0 && this.totalRow != 0) {
			this.calculate();
		}
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	private void calculate() {
		this.start = this.pageSize * (this.curPage - 1);
		this.totalPage = (this.totalRow + this.pageSize - 1) / this.pageSize;
		this.end = start + pageSize > totalRow ? totalRow : start+pageSize;
	}
	
	/**  
     * 对list集合进行分页处理  
	 * @param <E>
     * @return  
     */  
    public <E> List<E> listSplit(List<E> list) {  
        return list.subList(this.start, this.end);  
    }
    

}
