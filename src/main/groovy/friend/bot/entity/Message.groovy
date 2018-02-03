package friend.bot.entity

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Message {

	@Id
	@GeneratedValue
	Integer id
	
    String text

	String toString() {
		return "[text: '"+text+"']"
	}
}
