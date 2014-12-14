package model;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class CollectionList extends ResourceSupport {
	
	private List<? extends ResourceSupport> data;
	private boolean success;
	private int status;
	
	
	public List<? extends ResourceSupport> getData() {
		return data;
	}
	public void setData(List<? extends ResourceSupport> data) {
		this.data = data;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
