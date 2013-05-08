package com.adarwin.csc435.test;

import com.adarwin.csc435.ejb.Authentication;
import com.adarwin.csc435.User;
import com.adarwin.csc435.Task;
import org.w3c.dom.*;
import org.xml.sax.*;
/*
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
*/
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.parsers.*;

public class TestClient {

    private static String frob;

    public static String md5(String message) {
        String output = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            StringBuilder ab = new StringBuilder(2*hash.length);
            for (byte b : hash) {
                ab.append(String.format("%02x", b&0xff));
            }
            output = ab.toString();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return output;
    }




    public static void main(String[] args) {
        System.out.println("Started Main Method");
        Authentication authenticationBean = null;
        /*
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("jndi.properties"));
            InitialContext initialContext = new InitialContext();
            authenticationBean = (Authentication)initialContext.lookup("java:global/TaskCommander/ejb/AuthenticationBean");
            System.out.println("Created Authentication Bean");
            //Authentication authenticationBean = (Authentication)initialContext.lookup("AuthenticationBean");
        } catch (NamingException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        */

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String username = "";
        String password = "";
        String command = "";
        User user = null;
        String apiKey = "48f4eca00d151e09f4b8ca74c52cb425";
        String secret = "1dfa98c49acd35a2";
        String authURL = "http://www.rememberthemilk.com/services/auth/";
        String apiURL = "http://www.rememberthemilk.com/services/rest/";
        while (!command.equals("quit")) {
            try {
                if (command.equals("md5")) {
                    System.out.println("Enter message to hash: ");
                    String message = reader.readLine();
                    System.out.println("Result = " + md5(message));
                } else if (command.equals("getFrob")) {
                    //System.out.println("Enter method name");
                    //String method = reader.readLine();
                    //System.out.println("Enter 
                    String params = secret + "api_key" + apiKey + "methodrtm.auth.getFrob";
                    System.out.println("params = " + params);
                    String api_sig = "&api_sig=" + md5(params);
                    String urlText = apiURL + "?api_key=" + apiKey + "&method=rtm.auth.getFrob" + api_sig;
                    System.out.println(urlText);
                    BufferedReader bfInput = null;
                    //SAXParserFactory saxFactory = SAXParserFactory.newInstance();
                    //SAXParser xmlParser = saxFactory.newSAXParser();
                    try {
                        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder xmlParser = domFactory.newDocumentBuilder();
                        URL url = new URL(urlText);
                        URLConnection urlConn = url.openConnection();
                        InputStream inputStream = urlConn.getInputStream();
                        Document xmlDocument = xmlParser.parse(inputStream);
                        NodeList nodes = xmlDocument.getElementsByTagName("frob");
                        System.out.println("localName = " + nodes.item(0).getLocalName());
                        System.out.println("value = " + nodes.item(0).getNodeValue());
                        System.out.println("textContent = " + nodes.item(0).getTextContent());
                        //System.out.println(tempFrob);
                        inputStream.close();
                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                    }
                } else if (command.equals("login")) {
                    System.out.print("Username: ");
                    username = reader.readLine();
                    System.out.print("Password: ");
                    password = reader.readLine();
                    user = authenticationBean.logUserIn(username, password);
                    if (user == null) {
                        System.out.println("Failed to log " + username + " in");
                    } else {
                        System.out.println("Successfully logged in: " + user.getUsername());
                    }
                } else if (command.equals("logout")) {
                    authenticationBean.logUserOut(user);
                    user = null;
                    System.out.println("Logged Out");
                } else if (command.equals("isRegistered")) {
                    System.out.print("Username: ");
                    username = reader.readLine();
                    System.out.print("Password: ");
                    password = reader.readLine();
                    System.out.println(authenticationBean.isRegisteredUser(username, password));
                } else if (command.equals("register")) {
                    System.out.print("Username: ");
                    username = reader.readLine();
                    System.out.print("Password: ");
                    password = reader.readLine();
                    System.out.println(authenticationBean.registerUser(username, password));
                } else if (command.equals("loggedIn")) {
                    System.out.println(authenticationBean.isLoggedIn(user));
                } else if (command.equals("tasks")) {
                    if (user != null) {
                        List<Task> tasks = user.getTasks();
                        for (Task task : tasks) {
                            System.out.println("Name: " + task.getName() + ", DueDate: " + task.getDueDate());
                        }
                    } else {
                        System.out.println("User object is null");
                    }
                } else if (command.equals("addTask")) {
                    if (user != null) {
                        List<Task> tasks = user.getTasks();
                        tasks.add(new Task("NewTaskName"));
                    } else {
                        System.out.println("User object is null");
                    }
                }
                System.out.print("Enter Command: ");
                command = reader.readLine();
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }
        }
    }
}
