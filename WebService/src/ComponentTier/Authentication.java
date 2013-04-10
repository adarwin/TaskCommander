package com.adarwin.csc435.ejb;

import com.adarwin.logging.Logbook;
import com.adarwin.csc435.User;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Remote
public interface Authentication {
    public boolean isLoggedIn(User user);
}
