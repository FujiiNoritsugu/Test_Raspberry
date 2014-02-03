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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.gcm.model.Angle;
import test.gcm.model.Pressure;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
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
public class GraphServlet extends BaseServlet {

  /**
   * Displays the existing messages and offer the option to send a new one.
 * @throws ServletException
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
	  // グラフを表示する
	  getServletContext().getRequestDispatcher("/graph.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    doGet(req, resp);
  }

}
