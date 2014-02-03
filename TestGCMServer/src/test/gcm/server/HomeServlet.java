/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.gcm.server;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * Servlet that adds display number of devices and button to send a message.
 * <p>
 * This servlet is used just by the browser (i.e., not device) and contains the
 * main page of the demo app.
 */
@SuppressWarnings("serial")
public class HomeServlet extends BaseServlet {

  public static final String ATTRIBUTE_STATUS = "status";

  /**
   * Displays the existing messages and offer the option to send a new one.
 * @throws ServletException
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
	  // コマンドを受け取れば実行する
	  String angle = req.getParameter("angle");
	  String piston = req.getParameter("piston");

	  if(angle != null || piston != null){
		  List<String> devices = Datastore.getDevices();
		    if (!devices.isEmpty()) {
		        Queue queue = QueueFactory.getQueue("gcm");
		        // NOTE: check below is for demonstration purposes; a real application
		        // could always send a multicast, even for just one recipient
		        if (devices.size() == 1) {
		          // send a single message using plain post
		          String device = devices.get(0);
		          TaskOptions options = withUrl("/sendMessage");

		          options = options.param(SendMessageServlet.PARAMETER_DEVICE, device);

		          if(angle != null){
		        	  options = options.param("angle", angle);
		          }

		          if(piston != null){
		        	  options = options.param("piston", piston);
		          }

		          queue.add(options);

		          DatastoreService datastore =  DatastoreServiceFactory.getDatastoreService();
		          Entity entity = new Entity("angle");
		          entity.setProperty("time", System.currentTimeMillis());
		          entity.setProperty("angle", angle);
		          entity.setProperty("piston", piston);
		          datastore.put(entity);
		        }
		      }
	  }else{
		  getServletContext().getRequestDispatcher("/home.jsp").forward(req, resp);
	  }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    doGet(req, resp);
  }

}
