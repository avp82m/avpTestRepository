package friend.bot.cache


import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import friend.bot.entity.FriendUser





public class CacheManager {
       private static final Logger log = LoggerFactory.getLogger(CacheManager.class);
	   private static HashMap <Long,FriendUser> FriendUsersCache = new HashMap <Long,FriendUser>();
	   
	   
       public static FriendUser getUserFromCache(Long userId) {
		   log.debug("Ищем пользователя в кеше: {}",userId);
		   if(FriendUsersCache.containsKey(userId)) {
			   log.debug("Извлекаем пользователя из кеша: {}",userId);
			   return FriendUsersCache.get(userId);
		   }
		   
		   log.debug("Пользователь не найден в кеше: {}",userId);
		   return null;
	   }
	   
	   
	   public static HashMap <Long,FriendUser> getUsers() {
		   return FriendUsersCache;
	   }
	   
	   
	   public static void setUserToCache(Long userId,FriendUser) {
		   log.debug("Добавляем пользователя в кеш: {}",userId);
		   FriendUsersCache.put(userId,FriendUser);
	   }
	   
	   
	   public static FriendUser removeUserFromCache(Long userId) {
		   log.debug("Удаляем пользователя из кеша: {}",userId);
		   return FriendUsersCache.remove(userId);
	   }
       
       
       
}


