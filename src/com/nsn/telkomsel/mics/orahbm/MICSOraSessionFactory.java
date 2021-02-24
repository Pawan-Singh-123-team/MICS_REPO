package com.nsn.telkomsel.mics.orahbm;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class MICSOraSessionFactory {
	  /** Holds a single instance of Session */
	  @SuppressWarnings("rawtypes")
	private static final ThreadLocal threadLocal = new ThreadLocal();

	  /** The single instance of hibernate configuration */
	  private static final Configuration cfg = new Configuration();

	  /** The single instance of hibernate HibernateSessionFactory */
	  private static SessionFactory sessionFactory;
	  /**
	   * Returns the ThreadLocal Session instance.  Lazy initialize
	   * the <code>HibernateSessionFactory</code> if needed.
	   *
	   *  @return Session
	   *  @throws HibernateException
	   */
	 
	  @SuppressWarnings({ "deprecation", "unchecked" })
	public static Session currentSession() throws HibernateException {
		  logger.debug("currentSession()");
		    Session session = (Session) threadLocal.get();
		    
		    if (session != null && !session.isOpen()) session = null;

		    if (session == null) {
		      if (sessionFactory == null) {
		        try {
		          cfg.configure("micsora.cfg.xml");
		          sessionFactory = cfg.buildSessionFactory();
		          logger.debug("sessionFactory: " + sessionFactory);
		        } catch (Exception e) {
		        	logger.error("Failed creating Hibernate Session",e);
		        }
		      }
		      session = sessionFactory.openSession();
		      threadLocal.set(session);
		    }
		    return session;
		  }
	  /**
	   *  Close the single hibernate session instance.
	   *
	   *  @throws HibernateException
	   */
	  @SuppressWarnings("unchecked")
	public static void closeSession() throws HibernateException {
	    Session session = (Session) threadLocal.get();
	    threadLocal.set(null);

	    if (session != null) {
	      session.close();
	    }
	  }
	  /**
	   * Default constructor.
	   */
	  private MICSOraSessionFactory() {
	  }
	private static final Logger logger = Logger.getLogger(MICSOraSessionFactory.class);
}

