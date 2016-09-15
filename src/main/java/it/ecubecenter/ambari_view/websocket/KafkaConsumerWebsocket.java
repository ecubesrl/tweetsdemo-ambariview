package it.ecubecenter.ambari_view.websocket;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class KafkaConsumerWebsocket extends WebSocketAdapter  {
	
	private static Set<Session> activeSessions = new CopyOnWriteArraySet<>(); 
	private static Thread kafkaListenerThread = null ; 
	private static Logger log = LoggerFactory.getLogger(KafkaConsumerWebsocket.class);
	
	
	
	synchronized static void startListeningKafkaTopic(final String kafkaBrokers, final String topic){
		log.info("Called startListeningKafkaTopic");
		if(kafkaListenerThread == null){
			log.info("Started listening the Kafka topic");
			kafkaListenerThread = new Thread(new Runnable(){

				@Override
				public void run() {
					Properties props = new Properties();
				    props.put("bootstrap.servers", kafkaBrokers);
				    props.put("group.id", "test");
				    props.put("enable.auto.commit", "true");
				    props.put("auto.commit.interval.ms", "1000");
				    props.put("session.timeout.ms", "30000");
				    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
				    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
				    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
				    consumer.subscribe(Arrays.asList(topic));
				    try{
					     while (true) {
					         ConsumerRecords<String, String> records = consumer.poll(1000);
					         for (ConsumerRecord<String, String> record : records){
								 JSONObject json = new JSONObject(record.value());

								 for (Session s : activeSessions) {
									 try {
										 s.getRemote().sendString(record.value());
									 } catch (IOException e) {
										 activeSessions.remove(s);
									 }
								 }

					         }
					     }
				     }finally{
				    	 consumer.close();
				     }
				}
				
			});
			kafkaListenerThread.start();
		}
	}

	
	
	

	@Override
	public void onWebSocketBinary(byte[] payload, int offset, int len) {
		// TODO Auto-generated method stub
		super.onWebSocketBinary(payload, offset, len);
	}

	@Override
	public void onWebSocketClose(int statusCode, String reason) {
		activeSessions.remove(getSession());
		super.onWebSocketClose(statusCode, reason);
	}

	@Override
	public void onWebSocketConnect(Session sess) {
		activeSessions.add(sess);
		super.onWebSocketConnect(sess);
	}

	@Override
	public void onWebSocketError(Throwable cause) {
		LoggerFactory.getLogger("KafkaConsumerWebsocket").warn("error "+cause.getMessage());
		super.onWebSocketError(cause);
	}

	@Override
	public void onWebSocketText(String message) {
		// TODO Auto-generated method stub
		super.onWebSocketText(message);
	}
	
	
	
	
}
