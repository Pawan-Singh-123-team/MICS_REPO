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
 * Home object for domain model class Huntinggroup.
 * @see com.nsn.telkomsel.mics.hbm.Huntinggroup
 * @author Hibernate Tools
 */
public class HuntinggroupHome {

	private static final Log log = LogFactory.getLog(HuntinggroupHome.class);

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

	public void persist(Huntinggroup transientInstance) {
		log.debug("persisting Huntinggroup instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Huntinggroup instance) {
		log.debug("attaching dirty Huntinggroup instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(Huntinggroup instance) {
		log.debug("attaching clean Huntinggroup instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Huntinggroup persistentInstance) {
		log.debug("deleting Huntinggroup instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Huntinggroup merge(Huntinggroup detachedInstance) {
		log.debug("merging Huntinggroup instance");
		try {
			Huntinggroup result = (Huntinggroup) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Huntinggroup findById(java.lang.String id) {
		log.debug("getting Huntinggroup instance with id: " + id);
		try {
			Huntinggroup instance = (Huntinggroup) sessionFactory
					.getCurrentSession().get(
							"com.nsn.telkomsel.mics.hbm.Huntinggroup", id);
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
	public List findByExample(Huntinggroup instance) {
		log.debug("finding Huntinggroup instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.nsn.telkomsel.mics.hbm.Huntinggroup")
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
