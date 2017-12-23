package com.taotao.common.pojo;
/**
 * @author Charle Chung 
 * created on 2017年11月23日 
 */

import java.util.List;

public class EUDataGridResult {
	private long total;
	private List<?> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
