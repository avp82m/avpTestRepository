package com.telegram.bot.entity
import lombok.Getter;
import lombok.Setter;
import com.telegram.bot.cache.CacheManager

class FriendUser {
	private Long id;
	private String fname;
	private String name;
	private String sname;
	private String email;
	private Boolean isAutorized	=	false;
	private String sessionId;
	private String pin;
	
	FriendUser(Long userId){
		FriendUser	tmp	=	CacheManager.getUserFromCache(userId);
		if(tmp!=null) {
			setId(tmp.getId());
			setFname(tmp.getFname());
			setName(tmp.getName());
			setSname(tmp.getSname());
			setEmail(tmp.getEmail());
			setIsAutorized(tmp.getIsAutorized());
			setSessionId(tmp.getSessionId());
			setPin(tmp.getPin());
		}
	} 
	
	FriendUser(){}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFname() {
		return fname;
	}


	public void setFname(String fname) {
		this.fname = fname;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSname() {
		return sname;
	}


	public void setSname(String sname) {
		this.sname = sname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Boolean getIsAutorized() {
		return isAutorized;
	}


	public void setIsAutorized(Boolean isAutorized) {
		this.isAutorized = isAutorized;
		if(!isAutorized) {
			setSessionId(null);
			setPin(null);
		}
	}


	public String getSessionId() {
		return sessionId;
	}


	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}


	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getUserName() {
		return new String(fname.trim()+" "+name.trim()+" "+sname.trim()).trim();
	}
	
	
	public void save() {
		CacheManager.setUserToCache(getId(),this);
	}
}
