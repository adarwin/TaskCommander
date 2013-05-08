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
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@Singleton
@Stateless
//@WebService(endpointInterface = "com.adarwin.csc435.ejb.RTMConnection")
public class RTMConnectionBean implements RTMConnection {

    private static Logbook logbook = new Logbook("../logs/TaskCommander.log");
    private final String logHeader = "RTMConnectionBean";

    private static final String API_KEY = "48f4eca00d151e09f4b8ca74c52cb425";
    private static final String SHARED_SECRET = "1dfa98c49acd35a2";
    private static final String API_KEY_HEADER = "?api_key=";
    private static final String AUTH_TOKEN = "&auth_token=";
    private static final String FROB_HEADER = "&frob=";
    private static final String METHOD_HEADER = "&method=";
    private static final String NAME_HEADER = "&name=";
    private static final String PERMS_READ = "&perms=read";
    private static final String PERMS_WRITE = "&perms=write";
    private static final String PERMS_DELETE = "&perms=delete";
    private static final String TIMELINE_HEADER = "&timeline=";
    private static final String API_SIG_HEADER = "&api_sig=";
    private static final String AUTH_URL = "http://www.rememberthemilk.com/services/auth/";
    private static final String API_URL =  "http://www.rememberthemilk.com/services/rest/";
    private static final String ADD_TASK = "rtm.tasks.add";
    private static final String GET_FROB = "rtm.auth.getFrob";
    private static final String GET_TOKEN = "rtm.auth.getToken";
    private static final String CREATE_TIMELINE = "rtm.timelines.create";
    private static final String API_KEY_COMB = API_KEY_HEADER + API_KEY;
    //private static final String API_SIG = "&api_sig=";
    private static String frob;
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
    }

    @Override
    public void login() {
    }

    @Override
    public String getAuthenticationURL() {
        frob = getFrob();
        log(Logbook.INFO, "Frob = " + frob);
        String authURL = generateAuthURL(frob);
        log(Logbook.INFO, "AuthURL = " + authURL);
        return authURL;
    }


    @Override
    public String getToken() {
        log(Logbook.INFO, "Attempting to get token");
        String parameters = API_KEY_COMB + FROB_HEADER + frob + METHOD_HEADER + GET_TOKEN;
        Document xmlDocument = callAPI(API_URL, parameters, true);
        NodeList nodes = xmlDocument.getElementsByTagName("token");
        String output = null;
        if (nodes != null) {
            output = nodes.item(0).getTextContent();
            token = output;
        }
        log(Logbook.INFO, "Token = " + output);
        return output;
    }


    @Override
    public void addTask(String taskName) {
        String timeline = createTimeline();
        String parameters = API_KEY_COMB + AUTH_TOKEN + token + METHOD_HEADER + ADD_TASK + NAME_HEADER + taskName + TIMELINE_HEADER + timeline;
        Document xmlDocument = callAPI(API_URL, parameters, true);
        //getDesiredValue(xmlDocument, 
    }

    private String createTimeline() {
        Document xmlDocument = callAPI(API_URL, API_KEY_COMB + AUTH_TOKEN + token + METHOD_HEADER + CREATE_TIMELINE, true);
        return getDesiredValue(xmlDocument, "timeline");
    }
    
    private String getDesiredValue(Document xmlDocument, String valueName) {
        NodeList nodes = xmlDocument.getElementsByTagName(valueName);
        String output = null;
        if (nodes != null) {
            output = nodes.item(0).getTextContent();
        }
        return output;
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
        Document xmlDocument = callAPI(API_URL, API_KEY_COMB + METHOD_HEADER + GET_FROB, true);
        NodeList nodes = xmlDocument.getElementsByTagName("frob");
        String output = nodes.item(0).getTextContent();
        return output;
    }
    private String generateAuthURL(String frob) {
        String parameters = API_KEY_COMB + FROB_HEADER + frob + PERMS_DELETE;
        //String parametersB = API_KEY_HEADER + API_KEY + PERMS_DELETE + FROB_HEADER + frob;
        String apiSig = API_SIG_HEADER + md5(SHARED_SECRET + parameters);
        String output = AUTH_URL + parameters + apiSig;
        return output;
    }


    private Document callAPI(String rootURL, String parameters, boolean useAPI_SIG) {
        String urlText = rootURL + parameters;
        if (useAPI_SIG) {
            String api_sig = API_SIG_HEADER + md5(SHARED_SECRET + parameters);
            urlText += api_sig;
        }
        log(Logbook.INFO, "callAPI produced urlText: " + urlText);
        Document xmlDocument = null;
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder domParser = domFactory.newDocumentBuilder();
            URL url = new URL(urlText);
            URLConnection urlConn = url.openConnection();
            InputStream inputStream = urlConn.getInputStream();
            xmlDocument = domParser.parse(inputStream);
            //NodeList nodes = xmlDocument.getElementsByTagName("frob");
            //output = nodes.item(0).getTextContent();
        } catch (ParserConfigurationException ex) {
            log(ex);
        } catch (SAXException ex) {
            log(ex);
        } catch (MalformedURLException ex) {
            log(ex);
        } catch (IOException ex) {
            log(ex);
        }
        return xmlDocument;
    }
}
