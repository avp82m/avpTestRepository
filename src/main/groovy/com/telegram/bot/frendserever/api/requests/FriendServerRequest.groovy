package com.telegram.bot.frendserever.api.requests

class FriendServerRequest implements IFriendServerRequest{
	private String url				=	"";
	private String body				=	"";
	private String requestMethod	= 	"POST";
	private String contentType		=	"application/json";
	
	@Override
	public String toString() {
		return "URL: "+getUrl()+" \nBODY: "+getBody()+" \nrequestMethod: "+getRequestMethod()+" \ncontentType: "+getContentType();	
	}


	@Override
	public String getBody() {
		return body;
	}
	
	@Override
	public void setBody(String body) {
		this.body = body;
	}
	
	@Override
	public String getUrl() {
		return url;
	}
	
	@Override
	public String getRequestMethod() {
		return requestMethod;
	}
	
	@Override
	public String getContentType() {
		return contentType;
	}


	@Override
	public void setUrl(String url) {
		this.url=url;
	}


	@Override
	public void setRequestMethod(String requestMethod) {
		this.requestMethod=requestMethod;
	}


	@Override
	public void setcContentType(String contentType) {
		this.contentType=contentType;
	}
}
