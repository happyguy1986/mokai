package org.mokai.web.admin.jogger.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jogger.http.Request;
import org.jogger.http.Response;
import org.mokai.Message;
import org.mokai.RoutingEngine;
import org.mokai.impl.camel.CamelRoutingEngine;
import org.mokai.persist.MessageCriteria;

/**
 * Dashboard controller.
 * 
 * @author German Escobar
 */
public class Dashboard {
	
	private RoutingEngine routingEngine;

	public void index(Request request, Response response) {
		
		List<ConnectorUI> connections = HelperUI.buildConnectorUIs( routingEngine.getConnections() );
		List<ConnectorUI> applications = HelperUI.buildConnectorUIs( routingEngine.getApplications() );
		
		int toConnections = 0;
		int toApplications = 0;
		if (CamelRoutingEngine.class.isInstance(routingEngine)) {
			CamelRoutingEngine camelRE = (CamelRoutingEngine) routingEngine;
			toConnections = camelRE.getNumQueuedConnectionsMsgs();
			toApplications = camelRE.getNunQueuedApplicationsMsgs();
		}
		
		MessageCriteria criteria = new MessageCriteria()
			.addStatus(Message.STATUS_FAILED)
			.addStatus(Message.STATUS_RETRYING);
		int failed = routingEngine.getMessageStore().list(criteria).size();
		
		Map<String,Object> root = new HashMap<String,Object>();
		root.put("connections", connections);
		root.put("applications", applications);
		root.put("failedMsgs", failed);
		root.put("toApplications", toApplications);
		root.put("toConnections", toConnections);
		
		response.render("dashboard.ftl", root);
		
	}

	public void setRoutingEngine(RoutingEngine routingEngine) {
		this.routingEngine = routingEngine;
	}
	
}