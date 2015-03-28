/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nikolao.clasy;

import us.jubat.classifier.ClassifierClient;

/**
 *
 * @author nikolao {Ioannis Nikolaou AM 4504 mailto:Gianhspc@gmail.com}
 * 9/3/2015 Created{First write}
 */
public class client {
   private final String host;
   private final int port;
   private final String name;
   private final int timeout;
   
   public client(String host,int port,String name,int timeout){
   this.host=host;
   this.port=port;
   this.name=name;
   this.timeout=timeout;
   }
   public client(client ls)
   {
   this.host=ls.getHost();
   this.port=ls.getPort();
   this.name=ls.getName();
   this.timeout=ls.getTimeout();
   }
   public String getHost()
   {
   return host;
   }
   public int getPort()
   {
   return port;
   }
   public String getName()
   {
   return name;
   }
   public int getTimeout()
   {
   return timeout;
   }
}
