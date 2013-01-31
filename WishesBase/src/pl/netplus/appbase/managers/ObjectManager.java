package pl.netplus.appbase.managers;

import pl.netplus.appbase.asynctask.ObjectsAsycnTask;
import pl.netplus.appbase.entities.ModelBase;
import pl.netplus.appbase.enums.ERepositoryManagerMethods;
import pl.netplus.appbase.enums.ERepositoryTypes;
import pl.netplus.appbase.interfaces.IBaseRepository;
import pl.netplus.appbase.interfaces.IReadRepository;
import pl.netplus.appbase.repositories.CategoriesRepository;
import pl.netplus.appbase.repositories.ContentObjectRepository;
import pl.netplus.appbase.repositories.FavoritesRepository;

public class ObjectManager {

	private IBaseRepository getRepository(ERepositoryTypes type) {
		IBaseRepository repository = null;
		switch (type) {
		case Categories:
			repository = new CategoriesRepository();
			break;
		case ContentObject:
			repository = new ContentObjectRepository();
			break;
		case Favorite:
			repository = new FavoritesRepository();
			break;
		default:
			break;
		}
		return repository;
	}

	public void getDataWithoutSendObject(IReadRepository listener,
			ERepositoryTypes type, ERepositoryManagerMethods method) {
		startTask(listener, getRepository(type), method, null);
	}

	public void readById(IReadRepository listener, ERepositoryTypes type,
			ModelBase item) {
		startTask(listener, getRepository(type),
				ERepositoryManagerMethods.ReadById, item);
	}

	public void readTotalCount(IReadRepository listener, ERepositoryTypes type) {
		startTask(listener, getRepository(type),
				ERepositoryManagerMethods.TotalCount, null);
	}

	public void readFromServer(IReadRepository listener, ERepositoryTypes type) {

		startTask(listener, getRepository(type),
				ERepositoryManagerMethods.ReadFromServer, null);
	}

	public void readAll(IReadRepository listener, ERepositoryTypes type) {

		startTask(listener, getRepository(type),
				ERepositoryManagerMethods.ReadAll, null);
	}

	public void insertOrUpdate(IReadRepository listener, ERepositoryTypes type,
			ModelBase item) {

		startTask(listener, getRepository(type),
				ERepositoryManagerMethods.InsertOrUpdate, item);
	}

	private void startTask(IReadRepository listener,
			IBaseRepository repository, ERepositoryManagerMethods method,
			ModelBase item) {
		ObjectsAsycnTask task;
		task = new ObjectsAsycnTask(listener, method, repository, item);
		task.execute((Void) null);
	}
}
