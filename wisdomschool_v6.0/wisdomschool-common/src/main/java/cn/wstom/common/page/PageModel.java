package cn.wstom.common.page;/*
package cn.wstom.common.page;


import java.util.List;

*/
/**
 * 分页Bean
 * @author Jeanzhou
 * @version
 *		1.0 2015年2月7日下午9:48:00
 *//*

public class PageModel<E> {

	*/
/**
	 * 结果集
	 *//*

	private List<E> result;

	*/
/**
	 * 总记录数
	 *//*

	private int totalRecords;

	*/
/**
	 * 每页记录数
	 *//*

	private int pageSize = Constants.DEFAULT_PAGE_SIZE;

	*/
/**
	 * 总页数
	 *//*

	private int totalPage;

	*/
/**
	 * 当前页
	 *//*

	private int currentPage;

	public PageModel() {
		this(Constants.DEFAULT_PAGE_SIZE, 1);
	}

	public PageModel(int currentPage) {
		this(Constants.DEFAULT_PAGE_SIZE, currentPage);
	}

	public PageModel(int pageSize, int currentPage) {
		this.pageSize = pageSize;
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize == 0 ? Constants.DEFAULT_PAGE_SIZE : pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage == 0 ? 1 : currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<E> getResult() {
		return result;
	}

	public void setResult(List<E> result) {
		this.result = result;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
		//计算总页数
		calculate();
	}

	*/
/**
	 * 计算总页数
	 *//*

	private void calculate() {
		this.setTotalPage((this.getTotalRecords() + this.getPageSize() - 1) / this.getPageSize());
	}
}
*/
