package com.hrh.kmanual.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * @author huangrenhao
 * @date 2018/8/7
 */
public class BootstrapTreeViewNode {

	private String id;

	private String text;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String icon;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean selectable;

	/**
	 *  右侧线束
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> tags = new ArrayList<>(1);

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<BootstrapTreeViewNode> nodes = Collections.emptyList();

	public BootstrapTreeViewNode() {}

	public BootstrapTreeViewNode(String id, String text) {
		this.id = id;
		this.text = text;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getSelectable() {
		return selectable;
	}

	public void setSelectable(Boolean selectable) {
		this.selectable = selectable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<BootstrapTreeViewNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<BootstrapTreeViewNode> nodes) {
		this.nodes = nodes;
	}
}
