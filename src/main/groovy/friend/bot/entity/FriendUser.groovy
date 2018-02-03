package friend.bot.entity
import friend.bot.cache.CacheManager

class FriendUser {
	private Integer id;
	private String fname;
	private String name;
	private String sname;
	private String email;
	private Boolean isAutorized	=	false;
	private String sessionId;
	private String pin;
	private userData	=	null;
	
	FriendUser(Integer userId){
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
	
	
	public void refresh(def newData) {
		userData	=	newData;
		if(userData!=null) {
			setSname(userData.kadr.secondName.toString());
			setName(userData.kadr.middleName.toString());
			setFname(userData.kadr.firstName.toString());
			save();
		}
	}
	
	public def getUserData() {
		return userData;
	}
			
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
		this.email = email.toLowerCase();
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
	
	
	public String toString() {
		return "Id: "+getId()+
				"\nFname: "+getFname()+
				"\nName: "+getName()+
				"\nSname: "+getSname()+
				"\nEmail: "+getEmail()+
				"\nIsAutorized: "+getIsAutorized()+
				"\nSessionId: "+getSessionId()+
				"\nPin: "+getPin();
	}
}
