package com.akki.producer;

import com.akki.constant.ApplicationConstant;
import com.akki.dto.Thing;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class KafkaProducer {
	ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private KafkaTemplate<String, Thing> kafkaTemplate;

	@PostMapping(path = "/produce", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public String sendMessage(@RequestBody Thing message) {
		try {
			var num =message.getField();
			/*if(num==10){
				kafkaTemplate.send(ApplicationConstant.TOPIC_NAME, "junk");
			}else{*/
				kafkaTemplate.send(ApplicationConstant.TOPIC_NAME, message);
			//}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Message Sent";
	}

}
