package com.adarwin.csc435.ejb;

import com.adarwin.logging.Logbook;
import com.mainchip.Java.addMilk.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@Singleton
@Stateless
public class RTMConnectionBean implements RTMConnection {

    private static Logbook logbook = new Logbook("../logs/TaskCommander.log");
    private final String logHeader = "RTMConnectionBean";

    private static final String API_KEY = "48f4eca00d151e09f4b8ca74c52cb425";
    private static final String SHARED_SECRET = "1dfa98c49acd35a2";
    private static final String API_KEY_HEADER = "?api_key=";
    private static final String FROB_HEADER = "&frob=";
    private static final String METHOD_HEADER = "&method=";
    private static final String PERMS_READ = "&perms=read";
    private static final String PERMS_WRITE = "&perms=write";
    private static final String PERMS_DELETE = "&perms=delete";
    private static final String API_SIG_HEADER = "&api_sig=";
    private static final String AUTH_URL = "http://www.rememberthemilk.com/services/auth/";
    private static final String API_URL =  "http://www.rememberthemilk.com/services/rest/";
    private static final String GET_FROB = "rtm.auth.getFrob";
    //private static final String API_SIG = "&api_sig=";
    //private static String frob;
    private static String token;
    private RTM rtm;
    //private Token token;

    public void log(Exception ex) {
        logbook.log(logHeader, ex);
    }
    public void log(String level, String message) {
        logbook.log(level, logHeader, message);
    }

    @PostConstruct
    public void init() {
        String frob = getFrob();
        log(Logbook.INFO, "Frob = " + frob);
        String authURL = generateAuthURL(frob);
        log(Logbook.INFO, "AuthURL = " + authURL);
        /*
        try {
            log(Logbook.INFO, "Initializing rtm");
            //rtm = new RTM(API_KEY, SHARED_SECRET);
            //log(Logbook.INFO, "sig for rtm.test.login = " + rtm.parseSig("rtm.test.echo"));
            //log(Logbook.INFO, "Generating test auth url");
            //log(Logbook.INFO, rtm.genAuthURL("", "delete"));
            log(Logbook.INFO, "Getting frob");
            //String frob = rtm.getFrob();
            log(Logbook.INFO, "Getting Token");
            //token = rtm.getToken(frob);
        } catch (MilkException ex) {
            log(ex);
        }
        */
    }

    @Override
    public void login() {
        /*
        try {
            //User testUser = rtm.testLogin();
            log(Logbook.INFO, "rtm.testLogin() returned " + testUser);
        } catch (MilkException ex) {
            log(ex);
        }
        */
    }


    public String md5(String message) {
        String temp = message;
        if (!message.startsWith(SHARED_SECRET)) {
            message = SHARED_SECRET + message;
        }
        //message = message.replace("[?&=]", "");
        message = message.replace("?", "");
        message = message.replace("&", "");
        message = message.replace("=", "");
        if (!temp.equals(message)) {
            log(Logbook.INFO, "md5 conditioned the incoming message from " + temp +
                              " to " + message);
        }
        log(Logbook.INFO, "Beginning md5 hash of " + message);
        String hashedOutput = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            // Convert byte array to hexadecimal string
            StringBuilder sb = new StringBuilder(2*hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b&0xff));
            }
            hashedOutput = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            log(ex);
        } catch (NoSuchAlgorithmException ex) {
            log(ex);
        }
        return hashedOutput;
    }


    public String getFrob() {
        String output = null;
        String md5Input = SHARED_SECRET + "api_key" + API_KEY + "method" + GET_FROB;
        String api_sig = API_SIG_HEADER + md5(md5Input);
        String urlText = API_URL + "?api_key=" + API_KEY + "&method=" + GET_FROB + api_sig;
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder domParser = domFactory.newDocumentBuilder();
            URL url = new URL(urlText);
            URLConnection urlConn = url.openConnection();
            InputStream inputStream = urlConn.getInputStream();
            Document xmlDocument = domParser.parse(inputStream);
            NodeList nodes = xmlDocument.getElementsByTagName("frob");
            output = nodes.item(0).getTextContent();
        } catch (ParserConfigurationException ex) {
            log(ex);
        } catch (SAXException ex) {
            log(ex);
        } catch (MalformedURLException ex) {
            log(ex);
        } catch (IOException ex) {
            log(ex);
        }
        return output;
    }
    private String generateAuthURL(String frob) {
        String parameters = API_KEY_HEADER + API_KEY + FROB_HEADER + frob + PERMS_DELETE;
        //String parametersB = API_KEY_HEADER + API_KEY + PERMS_DELETE + FROB_HEADER + frob;
        String apiSig = API_SIG_HEADER + md5(SHARED_SECRET + parameters);
        String output = AUTH_URL + parameters + apiSig;
        return output;
    }
}
