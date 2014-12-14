package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.hateoas.Link;

public class Curies {
	
	private String name;
	private String href;
	private boolean templated;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public boolean isTemplated() {
		return templated;
	}
	public void setTemplated(boolean templated) {
		this.templated = templated;
	}
	
	public Curies(String name, String href, boolean templated) {
		super();
		this.name = name;
		this.href = href;
		this.templated = templated;
	}

}
