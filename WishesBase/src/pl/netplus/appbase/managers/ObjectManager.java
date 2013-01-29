package pl.netplus.appbase.managers;

import pl.netplus.appbase.asynctask.ObjectsAsycnTask;
import pl.netplus.appbase.enums.ERepositoryManagerMethods;
import pl.netplus.appbase.enums.ERepositoryTypes;
import pl.netplus.appbase.interfaces.IBaseRepository;
import pl.netplus.appbase.interfaces.IReadRepository;
import pl.netplus.appbase.repositories.CategoriesRepository;

public class ObjectManager {

	public void readAll(IReadRepository listener, ERepositoryTypes type) {
		IBaseRepository repository = null;
		switch (type) {
		case Categories:
			repository = new CategoriesRepository();
			break;

		default:
			break;
		}
		startTask(listener, repository, ERepositoryManagerMethods.ReadAll);
	}

	private void startTask(IReadRepository listener,
			IBaseRepository repository, ERepositoryManagerMethods method) {
		ObjectsAsycnTask task;
		task = new ObjectsAsycnTask(listener, method, repository);
		task.execute((Void) null);
	}
}
