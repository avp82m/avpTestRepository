package com.telegram.bot.frendserever.api.requests

interface IFriendServerRequest {
	String getBody();
	void setBody(String body);
	String getUrl();
	void setUrl(String url);
	String getRequestMethod();
	void setRequestMethod(String requestMethod);
	String getContentType();
	void setcContentType(String contentType);
	String toString();
}
