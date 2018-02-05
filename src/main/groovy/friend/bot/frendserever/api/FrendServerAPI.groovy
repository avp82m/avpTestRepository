package friend.bot.frendserever.api

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import friend.bot.entity.FriendUser
import friend.bot.executers.command.LogoutExecuter
import friend.bot.frendserever.api.requests.IFriendServerRequest

@Component
class FrendServerAPI {
	private static final Logger log = LoggerFactory.getLogger(FrendServerAPI.class);
	private static final String urlApi	=	"https://friend-st.sbrf.ru:9443/FriendServerDev"
	public static Boolean authenticate(FriendUser friendUser){
		
		try {
			String postResult;
			HttpURLConnection conn	=	(HttpURLConnection)new URL(urlApi+"rest/state/json/auth.rpc/authenticate").openConnection();
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
			}else if (conn.responseCode==401) {
				LogoutExecuter.logout(friendUser.getId()) ;
			}
			return false;
		}catch(Exception e) {
			log.error("PIN не подтвержден: {}",e.getLocalizedMessage());
			return false;
		}
	}
	
	
	
	public static def sendRequest(IFriendServerRequest request, FriendUser friendUser) {
		try {
			String postResult;
			HttpURLConnection conn	=	(HttpURLConnection)new URL(urlApi+request.getUrl()).openConnection();
			conn.requestMethod = request.getRequestMethod();
			conn.doOutput = true;
			conn.setRequestProperty("Content-Type", request.getContentType());
			conn.setRequestProperty("Cookie", friendUser.getSessionId());
			log.debug("FriendUser:\n{} \n\nsend request:\n{}\n",friendUser.toString(),request.toString());
			conn.outputStream.write(request.getBody().getBytes("UTF-8"));
			postResult = conn.inputStream.text;
			log.debug("responseCode: {}",conn.responseCode);
			def jsonResp =      new groovy.json.JsonSlurper().parseText(postResult);
			log.debug("response: {}",jsonResp);
			
			if(conn.responseCode==200) {
				return jsonResp.object;
			}else if (conn.responseCode==401) {
				LogoutExecuter.logout(friendUser.getId()) ;
			}
		}catch(Exception e) {
			log.debug("Исключение при обращении к серверной части: {}\n{}",request.toString(),e.getLocalizedMessage());
		}
		
		return null;
	}
	
}
