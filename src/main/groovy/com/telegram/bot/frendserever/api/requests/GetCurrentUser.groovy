package com.telegram.bot.frendserever.api.requests

class GetCurrentUser extends FriendServerRequest implements IFriendServerRequest{
	GetCurrentUser(){
		this.setBody("{}");
		this.setUrl("/rest/state/json/friendservice.rpc/getCurrentUser");
	}
}
