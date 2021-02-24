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
 * Home object for domain model class Callscreening.
 * @see com.nsn.telkomsel.mics.hbm.Callscreening
 * @author Hibernate Tools
 */
public class CallscreeningHome {

	private static final Log log = LogFactory.getLog(CallscreeningHome.class);

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

	public void persist(Callscreening transientInstance) {
		log.debug("persisting Callscreening instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Callscreening instance) {
		log.debug("attaching dirty Callscreening instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(Callscreening instance) {
		log.debug("attaching clean Callscreening instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Callscreening persistentInstance) {
		log.debug("deleting Callscreening instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Callscreening merge(Callscreening detachedInstance) {
		log.debug("merging Callscreening instance");
		try {
			Callscreening result = (Callscreening) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Callscreening findById(java.lang.String id) {
		log.debug("getting Callscreening instance with id: " + id);
		try {
			Callscreening instance = (Callscreening) sessionFactory
					.getCurrentSession().get(
							"com.nsn.telkomsel.mics.hbm.Callscreening", id);
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
	public List findByExample(Callscreening instance) {
		log.debug("finding Callscreening instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.nsn.telkomsel.mics.hbm.Callscreening")
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
