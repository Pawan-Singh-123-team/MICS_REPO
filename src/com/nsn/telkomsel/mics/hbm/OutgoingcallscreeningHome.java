package com.nsn.telkomsel.mics.hbm;

// Generated Mar 28, 2013 6:12:06 AM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Outgoingcallscreening.
 * @see com.nsn.telkomsel.mics.hbm.Outgoingcallscreening
 * @author Hibernate Tools
 */
public class OutgoingcallscreeningHome {

	private static final Log log = LogFactory
			.getLog(OutgoingcallscreeningHome.class);

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

	public void persist(Outgoingcallscreening transientInstance) {
		log.debug("persisting Outgoingcallscreening instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Outgoingcallscreening instance) {
		log.debug("attaching dirty Outgoingcallscreening instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(Outgoingcallscreening instance) {
		log.debug("attaching clean Outgoingcallscreening instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Outgoingcallscreening persistentInstance) {
		log.debug("deleting Outgoingcallscreening instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Outgoingcallscreening merge(Outgoingcallscreening detachedInstance) {
		log.debug("merging Outgoingcallscreening instance");
		try {
			Outgoingcallscreening result = (Outgoingcallscreening) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Outgoingcallscreening findById(java.lang.String id) {
		log.debug("getting Outgoingcallscreening instance with id: " + id);
		try {
			Outgoingcallscreening instance = (Outgoingcallscreening) sessionFactory
					.getCurrentSession().get(
							"com.nsn.telkomsel.mics.hbm.Outgoingcallscreening",
							id);
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

	@SuppressWarnings("rawtypes")
	public List findByExample(Outgoingcallscreening instance) {
		log.debug("finding Outgoingcallscreening instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.nsn.telkomsel.mics.hbm.Outgoingcallscreening")
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
