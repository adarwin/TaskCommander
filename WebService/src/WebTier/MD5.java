package com.adarwin.csc435;

import com.adarwin.logging.Logbook;
import com.adarwin.csc435.ejb.RTMConnection;
import com.adarwin.csc435.ejb.RTMConnectionBean;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Path("md5")
public class MD5 {
    private Logbook logbook = new Logbook("../logs/TaskCommander.log");
    private final String logHeader = "MD5";

    public void log(Exception ex)
    {
      logbook.log(logHeader, ex);
    }
    public void log(String level, String message)
    {
      logbook.log(level, logHeader, message);
    }

    private RTMConnection rtmConnectionBean;
    public MD5() {
        log(Logbook.INFO, "Creating MD5");
        InitialContext initialContext;
      try {
        initialContext = new InitialContext();
        rtmConnectionBean = (RTMConnection)initialContext.lookup("java:app/ejb/RTMConnectionBean");
      } catch (NamingException ex) {
        log(ex);
        if (rtmConnectionBean == null) {
            log(Logbook.WARNING, "RTMConnectionBean is null and therefore " +
                                 "can't be used for anything.");
        }
      }
    }
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String generateMD5(@PathParam("message") String message) {
        return rtmConnectionBean.md5(message);
    }
}
