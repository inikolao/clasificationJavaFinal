/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nikolao.clasy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static nikolao.clasy.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import us.jubat.classifier.ClassifierClient;

/**
 *
 * @author nikolao {Ioannis Nikolaou AM 4504 mailto:Gianhspc@gmail.com}
 * 9/3/2015 Created{First write}
 */

@ComponentScan
@EnableAutoConfiguration
public class main {
public static void main(String[] args) throws UnknownHostException, IOException, FileNotFoundException {
   InetAddress thisIp = null;
   String filename="trainDat/all.dat";
   //String host="83.212.112.190";
   String host="192.168.2.5";
   //try{
   //host=thisIp.getHostAddress().toString();}
   //catch (NullPointerException ex){}
   int port=9230;
   String name="clasy";
   int timeout=10;
   boolean t;
   //do{
   //t=checkPort(host,port);
    //if(t==true)
    //{
    //   try {
    //       startServer(port);
    //   } catch (InterruptedException ex) {
    //       Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
    //   }
    //}
    //else
    //{
    //port++;
    //}
   //}while(t==false);
//open clients
    //client myclient= new client("83.212.112.190",9200,"test",10);
    client myclient= new client(host,port,name,timeout);
    ClassifierClient cl=openClient(myclient);
    trainCl(filename,cl);//train what
    SpringApplication.run(main.class, args);
    
    }    
}