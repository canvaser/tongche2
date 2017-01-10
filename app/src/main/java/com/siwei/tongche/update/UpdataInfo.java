package com.siwei.tongche.update;

public class UpdataInfo {
	private String version;
	private String url;
	private String isForce;//是否强制更新
	
	public String getIsForce() {
		return isForce;
	}
	public void setIsForce(String isForce) {
		this.isForce = isForce;
	}
	public UpdataInfo() {
		super();
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "UpdataInfo [version=" + version + ", url=" + url + ", isForce="
				+ isForce + "]";
	}
	 
	
}
