package com.challenge.got.client.auth.repo;

import java.io.Serializable;
import java.util.List;

public interface MainRepository<T, E extends Serializable> {

	/**
	 * This method creates/saves a new model
	 * 
	 * @param model
	 *            the model to be added/saved
	 * @return true if the given model is created successfully, otherwise it will return false
	 */
	public abstract boolean create(T model);

	/**
	 * This method updates a model, given the updated model
	 * 
	 * @param model
	 *            the updated model
	 * @return true if the given model is updated successfully, otherwise it will return false
	 */
	public abstract boolean update(T model);

	/**
	 * This method removes a model, given the model to be removed
	 * 
	 * @param model
	 *            the model to be removed
	 * @return the removed model, if the given model is removed successfully, otherwise it will return <b>null</b>
	 */
	public abstract T remove(T model);

	/**
	 * This method finds a list of saved models
	 * 
	 * @return list of models if the selection is successful, otherwise it will return <b>null</b>
	 */
	public abstract List<T> findAll();

	/**
	 * This method finds a model, given the model's id
	 * 
	 * @param id
	 *            the model's id
	 * @return the model mapped to the given id, or <b>null</b> if model doesn't exist
	 */
	public abstract T find(E id);
}
