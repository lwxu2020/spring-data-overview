package com.xlw.springdataoverview.repository;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityManager;

public class DeleteByOriginRepositoryImpl implements DeleteByOriginRepository {

	@Autowired
	private EntityManager entityManager;
	
	public DeleteByOriginRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void deleteByOrigin(String orgin) {
		entityManager.createNativeQuery("DELETE FROM flight WHERE origin = ?")
			.setParameter(1, orgin)
			.executeUpdate();
	}
}
