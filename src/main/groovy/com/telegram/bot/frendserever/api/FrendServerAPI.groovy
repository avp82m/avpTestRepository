package com.telegram.bot.frendserever.api

import com.telegram.bot.entity.FriendUser
import com.telegram.bot.service.MessageService
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired


class FrendServerAPI {
	private static final Logger log = LoggerFactory.getLogger(FrendServerAPI.class);
	
	
	
	public static Boolean authenticate(FriendUser friendUser){
		try {
			String postResult;
			HttpURLConnection conn	=	(HttpURLConnection)new URL("http://sbt-oapou-034.sigma.sbrf.ru:9080/FriendServerDev/rest/state/json/auth.rpc/authenticate").openConnection();
			conn.requestMethod = "POST";
			conn.doOutput = true;
			conn.setRequestProperty("Content-Type", "application/json");
			String requestStr	=	"{\"arg0\":\""+friendUser.getEmail()+"\",\"arg1\":\""+friendUser.getPin()+"\"}";
			log.debug("requestStr: {}",requestStr);
			conn.outputStream.write(requestStr.getBytes("UTF-8"));
			postResult = conn.inputStream.text;
			log.debug("responseCode: {}",conn.responseCode);
			def jsonResp =      new groovy.json.JsonSlurper().parseText(postResult);
			log.debug("FrendServer answer: {}",jsonResp);
			
			
			if(conn.responseCode==200) {
				friendUser.setSessionId(conn.getHeaderField("Set-Cookie"));
				friendUser.save();
				log.debug("FrendServer Cookie: {}",friendUser.getSessionId());
				return true;
			}
			return false;
		}catch(Exception e) {
			log.debug("PIN не подтвержден: {}",e.getLocalizedMessage());
			return false;
		}
	}
	
	
	
	public static def getCurrentUser(FriendUser friendUser){
		try {
			String postResult;
			HttpURLConnection conn	=	(HttpURLConnection)new URL("http://sbt-oapou-034.sigma.sbrf.ru:9080/FriendServerDev/rest/state/json/friendservice.rpc/getCurrentUser").openConnection();
			conn.requestMethod = "POST";
			conn.doOutput = true;
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Cookie", friendUser.getSessionId());
			String requestStr	=	"{}";
			log.debug("getCurrentUser: {}",requestStr);
			conn.outputStream.write(requestStr.getBytes("UTF-8"));
			postResult = conn.inputStream.text;
			log.debug("responseCode: {}",conn.responseCode);
			def jsonResp =      new groovy.json.JsonSlurper().parseText(postResult);
			log.debug("FrendServer answer: {}",jsonResp);
			
			if(conn.responseCode==200) {
				friendUser.save();
				log.debug("FrendServer Cookie: {}",friendUser.getSessionId());
				return jsonResp;
			}
		}catch(Exception e) {
			log.debug("Информация о пользователе не получена: {}",e.getLocalizedMessage());
		}
		
		return null;
	}
}
