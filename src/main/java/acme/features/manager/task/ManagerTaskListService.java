/*
 * ManagerShoutListService.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.manager.task;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Manager;
import acme.framework.services.AbstractListService;

@Service
public class ManagerTaskListService implements AbstractListService<Manager, Task> {


	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerTaskRepository repository;


	// AbstractListService<Administrator, Task> interface --------------
	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;
		
		Collection<Task> collection;
		final int id = request.getPrincipal().getActiveRoleId();
		collection = this.repository.findTasksByManager(id);
		final Manager manager = this.repository.findManagerById(request.getPrincipal().getActiveRoleId());
		boolean res = manager!=null;

		for (final Task t: collection) {
			if (t.getManagerId().getId()!=manager.getId()&&res)
				res=false;
			}

		return res;
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "executionPeriodInit", "executionPeriodEnd", "description", "optionalLink", "isPublic");
	}

	@Override
	public Collection<Task> findMany(final Request<Task> request) {
		assert request != null;

		Collection<Task> result;
		final int id = request.getPrincipal().getActiveRoleId();
		result = this.repository.findTasksByManager(id);


		return result;
	}

}
