package com.nsn.telkomsel.mics.orahbm;

// Generated May 9, 2013 1:29:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Micsmenu.
 * @see com.nsn.telkomsel.mics.orahbm.Micsmenu
 * @author Hibernate Tools
 */
public class MicsmenuHome {

	private static final Log log = LogFactory.getLog(MicsmenuHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Micsmenu transientInstance) {
		log.debug("persisting Micsmenu instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Micsmenu instance) {
		log.debug("attaching dirty Micsmenu instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Micsmenu instance) {
		log.debug("attaching clean Micsmenu instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Micsmenu persistentInstance) {
		log.debug("deleting Micsmenu instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Micsmenu merge(Micsmenu detachedInstance) {
		log.debug("merging Micsmenu instance");
		try {
			Micsmenu result = (Micsmenu) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Micsmenu findById(java.lang.String id) {
		log.debug("getting Micsmenu instance with id: " + id);
		try {
			Micsmenu instance = (Micsmenu) sessionFactory.getCurrentSession()
					.get("com.nsn.telkomsel.mics.orahbm.Micsmenu", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Micsmenu instance) {
		log.debug("finding Micsmenu instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.nsn.telkomsel.mics.orahbm.Micsmenu")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
