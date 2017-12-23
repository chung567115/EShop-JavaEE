package com.taotao.common.pojo;

/**
 * EasyUI 树形控件节点
 * 
 * @author Charle Chung created on 2017年11月26日
 */
public class EUTreeNode {
	private long id;

	private long parentId;

	private String text;

	private String state;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
