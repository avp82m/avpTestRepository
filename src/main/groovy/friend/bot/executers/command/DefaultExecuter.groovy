package friend.bot.executers.command


import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message

import friend.bot.executers.AccessLevels
import friend.bot.executers.IExecuters
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.cloud.dialogflow.v2beta1.DetectIntentResponse;
import com.google.cloud.dialogflow.v2beta1.QueryInput;
import com.google.cloud.dialogflow.v2beta1.QueryResult;
import com.google.cloud.dialogflow.v2beta1.SessionName;
import com.google.cloud.dialogflow.v2beta1.SessionsClient;
import com.google.cloud.dialogflow.v2beta1.TextInput;
import com.google.cloud.dialogflow.v2beta1.TextInput.Builder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class DefaultExecuter implements IExecuters {
	private Message message	=	null;
	private SendMessage outMessage	=	new SendMessage();
	private static final Logger log = LoggerFactory.getLogger(DefaultExecuter.class);
	
	@Override
	public void setMessage(Message message) {
		this.message=message;
	}

	public static String detectIntentTexts(String projectId, String text) {
		log.debug("Отправляем запрос в DialogFlow({}): {}",projectId,text);


	   
		SessionsClient sessionsClient =   null;
		try {
			   sessionsClient = SessionsClient.create();
			   try {
					  SessionName session = SessionName.of(projectId, UUID.randomUUID().toString());
					  log.debug("Session Path: {}",session.toString());

					  Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode("ru-RU");
					  log.debug("Session 1");
					  QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
					  log.debug("Session 2");
					  DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
					  log.debug("Session 3");
					  QueryResult queryResult = response.getQueryResult();
					  log.debug("Session 4");
					  log.debug("====================");
					  log.debug("Query Text: {}", queryResult.getQueryText());
					  log.debug("Detected Intent: {} (confidence: {})"
								   ,queryResult.getIntent().getDisplayName()
								   ,queryResult.getIntentDetectionConfidence());
					  log.debug("Fulfillment Text: {}", queryResult.getFulfillmentText());
			   }catch(Exception e) {
					  log.error("Не получили ответ от DialogFlow: {}",e);
			   }finally {
					  sessionsClient.close();
					  sessionsClient      =      null;
			   }
		}catch(Exception e) {
			   log.error("Не получили сессию DialogFlow: {}",e);
		}
		
		return "";
  }
  
  
	@Override
	public SendMessage getAnswer() {
		outMessage.setText("Не понимаю Вас\n. Воспользуйтесь командой /help для получения списка доступных команд.");
		return outMessage;
	}
	
	
	@Override
	public String getHelp() {
		return "";
	}
	

	public SendMessage getErrorAnswer(Message message,String error) {
		detectIntentTexts("facebot-d7869", error);
		
		outMessage.setText(error);
		return outMessage;
	}


	@Override
	public AccessLevels getAccessLevel() {
		return AccessLevels.ALL;
	}

}
