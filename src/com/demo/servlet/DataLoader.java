package com.demo.servlet;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.demo.util.LoggerFactory;

public class DataLoader {
	
	private static Logger LOG = LoggerFactory.make();
	private EntityManager em;
	private ClassLoader cl;

	public DataLoader(EntityManager em) {
		this.em = em;
		this.cl = Thread.currentThread().getContextClassLoader();
	}
	
	public <T> T read(String fileName, Class<T> clazz) {
		LOG.info("Loading: " +  fileName);
		T t = null;
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
			t = clazz.cast(unmarshaller.unmarshal(getInputStream(fileName)));
		} catch (JAXBException e) {
			LOG.info("Exception: " +  e.getMessage());
		}
		return t;
	}
	
	public <U> void print(List<U> list) {
		for (U u : list) {
			LOG.info(u.toString());
		}
	}
	
	public <U> void persist(List<U> list) {
		LOG.info("Persisting: " +  list.get(0).getClass().getSimpleName());
		for (U u : list) {
			persist(u);
		}
	}
	
	public <U> void persist(U u) {
			try {
				em.persist(u);
			} catch (Exception e) {
				LOG.info("Exception: " +  e.getMessage());
			}
	}
	
	private InputStream getInputStream(String fileName) {
		return cl.getResourceAsStream(fileName);
	}

}
