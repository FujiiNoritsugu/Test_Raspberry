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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import upload.UploadData;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

/**
 * Servlet that adds display number of devices and button to send a message.
 * <p>
 * This servlet is used just by the browser (i.e., not device) and contains the
 * main page of the demo app.
 */
@SuppressWarnings("serial")
public class DataServlet extends BaseServlet {

  public static final String ATTRIBUTE_STATUS = "status";

  /**
   * Displays the existing messages and offer the option to send a new one.
 * @throws ServletException
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {

      String modelStr = (String) req.getParameter("READING_DATA");

      if(modelStr != null){
    	  if(!upload(modelStr)){
    	  resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    	  }
      }else{
		  getServletContext().getRequestDispatcher("/home.jsp").forward(req, resp);
	  }
  }

  /**
  * Postフォームの値をデシリアライズし、オブジェクトをアップロードする
  * @param フォームの文字列(アップロード文字列をシリアライズ化し16進表記したもの)
  */
 private boolean upload(String modelStr){
     Logger log = Logger.getLogger("ReaderWallController");

     boolean result = false;

     try {
         // デシリアライズ
         ByteArrayInputStream byteIn =
             new ByteArrayInputStream(toByteArray(modelStr));
         ObjectInputStream in = new ObjectInputStream(byteIn);
         UploadData data = (UploadData) in.readObject();

         // 保存
         DatastoreService datastore =  DatastoreServiceFactory.getDatastoreService();
         Entity entity = new Entity("pressure");
         entity.setProperty("time", System.currentTimeMillis());
         entity.setProperty("pressure1", data.getPressure1());
         entity.setProperty("pressure2", data.getPressure2());
         entity.setProperty("pressure3", data.getPressure3());
         entity.setProperty("pressure4", data.getPressure4());
         datastore.put(entity);

         result = true;
     } catch (Exception e) {
         log.log(Level.ALL, e.getMessage());
         e.printStackTrace();
     }

     return result;
 }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    doGet(req, resp);
  }

  /**
   * 16進数の文字列⇒バイト配列変換
   *
   * @param hex
   *            16進数の文字列
   * @return バイト配列
   */
  public static byte[] toByteArray(String hex) {
      byte[] bytes = new byte[hex.length() / 2];
      for (int index = 0; index < bytes.length; index++) {
          bytes[index] =
              (byte) Integer.parseInt(hex.substring(
                  index * 2,
                  (index + 1) * 2), 16);
      }
      return bytes;
  }

}
