package it.ecubecenter.ambari_view.websocket;

import java.util.Iterator;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.ambari.view.ViewContext;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class KafkaWebsocketServlet extends WebSocketServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger("KafkaWebsocketServlet");
	
	
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try{
			ViewContext viewContext = (ViewContext) config.getServletContext().getAttribute(ViewContext.CONTEXT_ATTRIBUTE);
/*			Iterator<String> it = viewContext.getProperties().keySet().iterator();
			while(it.hasNext()){ String p = it.next();log.info("Property : " + p + ": "+viewContext.getProperties().get(p));}
			log.info("After while cicle");
			log.info("HDFS site : " +viewContext.getCluster().getConfigurationValue("hdfs-site", "dfs.namenode.http-address") );
			log.info("Kafka broker : " +viewContext.getCluster().getConfigurationValue("kafka-broker", "listeners") );*/

			KafkaConsumerWebsocket.startListeningKafkaTopic(viewContext.getProperties().get("kafka.brokers"), 
					viewContext.getProperties().get("kafka.topic"));
		}catch(Throwable t){
			log.severe(t.getMessage());
		}
	}




	@Override
	public void configure(WebSocketServletFactory wsf) {
		wsf.getPolicy().setIdleTimeout(1000 * 3600); // 1 hour
		wsf.register(KafkaConsumerWebsocket.class);
		
	}

}
