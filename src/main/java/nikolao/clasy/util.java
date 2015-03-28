/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nikolao.clasy;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import us.jubat.anomaly.AnomalyClient;
import us.jubat.classifier.ClassifierClient;
import us.jubat.classifier.EstimateResult;
import us.jubat.classifier.LabeledDatum;
import us.jubat.common.Datum;

/**
 *
 * @author nikolao {Ioannis Nikolaou AM 4504 mailto:Gianhspc@gmail.com}
 * 27/2/2015 Created{First write}
 */

public class util {
    public static client clsf;
    public static ClassifierClient openClient(client clt) throws UnknownHostException {
        util.clsf=clt;
        ClassifierClient client = new ClassifierClient(clt.getHost(),clt.getPort(),clt.getName(),clt.getTimeout());
        return client;
    }
    
    public static void trainCl(final String fileName,ClassifierClient client)throws FileNotFoundException, IOException{
        List<LabeledDatum> trainData;
        trainData = new ArrayList();
        String[] elemval;
        int i=0;
        String line;
        System.out.println("Start training....");
        //for (final String line : readLines(fileName)) {
        final BufferedReader br = new BufferedReader(new FileReader(fileName));
        while ((line = br.readLine()) != null) {
            //lines.add(line);
            i++;
            System.out.println("file line: "+i+" line contents: "+line+"");
            elemval=line.split(",");
            System.out.println("Label: "+elemval[0]+" - Value: "+elemval[1]+"");
            trainData.add(makeTrainDatum(elemval[0],"value", Double.parseDouble(elemval[1])));
            System.out.println("");
        }
        System.out.println("Sending Datum....");
        client.train(trainData);
        System.out.println("OK");
        System.out.println(client.getStatus());
        System.out.println("OK");
        System.out.println("Starting Service....");
    }
    public static Datum makeDatum(String key,double value) {
        return new Datum().addNumber(key, value);
    }
    public static Datum makeNumberDatum(final String label, double reading) {
        return new Datum().addNumber(label, reading);
    }
    public static LabeledDatum makeTrainDatum(String label, String key, double value) {
        return new LabeledDatum(label, makeDatum(key,value));
    }
    
    public static List<String> readLines(final String fileName) throws IOException {
        final List<String> lines;
        lines = new ArrayList();
        String line = "";
        final BufferedReader br = new BufferedReader(new FileReader(fileName));
        while ((line = br.readLine()) != null) {
            lines.add(line);
            //System.out.println(line);
        }
        br.close();
        return lines;
    }
    public static List<String> getTrainedLabels(client clt){
        List<String> labels;
        ClassifierClient client = null;
        try {
            client=openClient(clt);
        } catch (UnknownHostException ex) {
            
        }
        labels=client.getLabels();
        return labels;
    }
    public static int getNumLabels(client clt){
        List<String> labels;
        ClassifierClient cl=null;
        try {
            cl=openClient(clt);
        } catch (UnknownHostException ex) {
            
        }
        labels=cl.getLabels();
        return labels.size();
    }
    public static List<List<EstimateResult>> clasfy(double value)  throws UnknownHostException
    {
        List<List<EstimateResult>> results;
        ClassifierClient cl;
        cl=openClient(util.clsf);
        Datum[] testData = {
        makeDatum("value", value)};
        results = cl.classify(Arrays.asList(testData));
        return results;
    }
    public static boolean checkPort(String host,int port) throws UnknownHostException
    { //otan einai available epistrefei true
        boolean result = true;
        try {
            System.out.println("Checking Port : "+port);
            (new Socket(host, port)).close();
            
            Socket s=new Socket(host, port);
            System.out.println("listening on port: " + s.getLocalPort());
            System.out.println("listening on port: " + s.getPort());
            s.close();
            System.out.println("Port is Taken!");
        // Successful connection means the port is taken.
            result = false;
            }
        catch(IOException e) {
        // Could not connect.
            System.out.println("Port is free!");
            }
        
        return result;
    }
    public static void startServer(int port) throws IOException, InterruptedException
    {
        String distro = null;
        String  s="";
        int i=0;
        Properties props = System.getProperties();
         System.out.println("Os name:"+props.getProperty("os.name"));
        System.out.println("Os version:"+props.getProperty("os.version"));
        String[] cmd = {"/bin/sh", "-c", "cat /etc/*-release" };
        Process releaseDs=Runtime.getRuntime().exec(cmd);
        releaseDs.waitFor();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(releaseDs.getInputStream()));
        System.out.println("Reading distro version.....");
        while ((s = stdInput.readLine()) != null) {
           //System.out.println(s);//get distro<--name
           i++;
           if(i==1)
           {
               //get version name
               distro=s;
               
           break;
           }
        }
        stdInput.close();
        PrintWriter writer = new PrintWriter("clasf.sh", "UTF-8");
        Runtime term = null;
        Process clasif = null;
        if(distro.contains("Ubuntu"))
        {//writing script and execute
            System.out.println("Disto : Ubuntu");
            writer.println("#!/bin/bash");
            writer.println("source /opt/jubatus/profile");
            writer.println("jubaclassifier --configpath clasfy.json --rpc-port $1&");
            writer.close();
            //runing server
            System.out.println("Start runing server on port "+port+".....");
            term.getRuntime().exec("chmod 775 clasf.sh");
            //clasif=term.getRuntime().exec("/bin/bash clasf.sh "+port+" >server2.log");
            clasif=term.getRuntime().exec("/bin/bash clasf.sh "+port+"");
            System.out.println("Script startred....");
            //term.getRuntime().exec("rm clasf.sh");
        }
        else if(distro.contains("Slackware"))
        {//writing script and execute
            System.out.println("Disto : Slackware");
            writer.println("#!/bin/bash");
            writer.println("source /$USER/local/share/jubatus/jubatus.profile");
            writer.println("jubaclassifier --configpath clasy.json --rpc-port $1&");
            writer.close();
            //start runing server
            System.out.println("Start runing server on port "+port+".....");
            term.getRuntime().exec("chmod 775 clasf.sh");
            clasif=term.getRuntime().exec("sh clasf.sh "+port+"");
            //term.getRuntime().exec("rm clasf.sh");
        }
        /*
        BufferedReader stdError = new BufferedReader(new InputStreamReader(clasif.getErrorStream()));
        //errors
        BufferedReader stdInput2 = new BufferedReader(new InputStreamReader(clasif.getInputStream()));
        s="";
        PrintWriter log = new PrintWriter("server.log", "UTF-8");
        while ((s = stdInput2.readLine()) != null) {
           log.println(s);           
        }
        stdInput2.close();
        log.close();
        clasif.destroy();
        /*s="";
        System.out.println("Keeping log file in \"server.log\" for the good starting......");
            while ((s = stdError.readLine()) != null) {
                if(s!=null)
                {
                    log.println(s);
                    System.out.println("Error. check \"server.log\" file for more.");
                }
                else
                {
                    
                    
                    System.out.println("ALL OK!");
                    break;
                }
            }
        stdError.close();*/
    }
    
}