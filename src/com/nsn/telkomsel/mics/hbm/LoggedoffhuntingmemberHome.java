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
 * Home object for domain model class Loggedoffhuntingmember.
 * @see com.nsn.telkomsel.mics.hbm.Loggedoffhuntingmember
 * @author Hibernate Tools
 */
public class LoggedoffhuntingmemberHome {

	private static final Log log = LogFactory
			.getLog(LoggedoffhuntingmemberHome.class);

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

	public void persist(Loggedoffhuntingmember transientInstance) {
		log.debug("persisting Loggedoffhuntingmember instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Loggedoffhuntingmember instance) {
		log.debug("attaching dirty Loggedoffhuntingmember instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(Loggedoffhuntingmember instance) {
		log.debug("attaching clean Loggedoffhuntingmember instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Loggedoffhuntingmember persistentInstance) {
		log.debug("deleting Loggedoffhuntingmember instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Loggedoffhuntingmember merge(Loggedoffhuntingmember detachedInstance) {
		log.debug("merging Loggedoffhuntingmember instance");
		try {
			Loggedoffhuntingmember result = (Loggedoffhuntingmember) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Loggedoffhuntingmember findById(java.lang.String id) {
		log.debug("getting Loggedoffhuntingmember instance with id: " + id);
		try {
			Loggedoffhuntingmember instance = (Loggedoffhuntingmember) sessionFactory
					.getCurrentSession()
					.get("com.nsn.telkomsel.mics.hbm.Loggedoffhuntingmember",
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
	public List findByExample(Loggedoffhuntingmember instance) {
		log.debug("finding Loggedoffhuntingmember instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.nsn.telkomsel.mics.hbm.Loggedoffhuntingmember")
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
