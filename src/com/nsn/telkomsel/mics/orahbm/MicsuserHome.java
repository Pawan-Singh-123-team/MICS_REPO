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
 * Home object for domain model class Micsuser.
 * @see com.nsn.telkomsel.mics.orahbm.Micsuser
 * @author Hibernate Tools
 */
public class MicsuserHome {

	private static final Log log = LogFactory.getLog(MicsuserHome.class);

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

	public void persist(Micsuser transientInstance) {
		log.debug("persisting Micsuser instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Micsuser instance) {
		log.debug("attaching dirty Micsuser instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Micsuser instance) {
		log.debug("attaching clean Micsuser instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Micsuser persistentInstance) {
		log.debug("deleting Micsuser instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Micsuser merge(Micsuser detachedInstance) {
		log.debug("merging Micsuser instance");
		try {
			Micsuser result = (Micsuser) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Micsuser findById(java.lang.String id) {
		log.debug("getting Micsuser instance with id: " + id);
		try {
			Micsuser instance = (Micsuser) sessionFactory.getCurrentSession()
					.get("com.nsn.telkomsel.mics.orahbm.Micsuser", id);
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

	public List findByExample(Micsuser instance) {
		log.debug("finding Micsuser instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.nsn.telkomsel.mics.orahbm.Micsuser")
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
