package pl.netplus.appbase.asynctask;

import pl.netplus.appbase.database.DataBaseManager;
import pl.netplus.appbase.entities.ModelBase;
import pl.netplus.appbase.enums.ERepositoryException;
import pl.netplus.appbase.enums.ERepositoryManagerMethods;
import pl.netplus.appbase.enums.ExceptionErrorCodes;
import pl.netplus.appbase.exception.CommunicationException;
import pl.netplus.appbase.exception.RepositoryException;
import pl.netplus.appbase.httpconnection.IHttpRequestToAsyncTaskCommunication;
import pl.netplus.appbase.interfaces.IBaseRepository;
import pl.netplus.appbase.interfaces.IReadRepository;
import pl.netplus.appbase.repositories.CategoriesRepository;
import pl.netplus.appbase.repositories.ContentObjectRepository;
import android.os.AsyncTask;
import android.os.Bundle;

public class ObjectsAsycnTask extends AsyncTask<Void, Void, Void> implements
		IHttpRequestToAsyncTaskCommunication {

	private AsyncTaskResult response;
	private IReadRepository listener;
	private ERepositoryManagerMethods method;
	private IBaseRepository repository;
	// private ReadAllDataRepository repositoryItemContainer;
	private ModelBase item;
	private int categoryId;
	private String value;
	private Bundle bundle;

	public ObjectsAsycnTask(IReadRepository listener,
			ERepositoryManagerMethods method, IBaseRepository repository,
			ModelBase item) {
		this.listener = listener;
		this.method = method;
		this.repository = repository;
		this.item = item;

	}

	public ObjectsAsycnTask(IReadRepository listener,
			ERepositoryManagerMethods method, IBaseRepository repository,
			int categoryId, String value) {
		this.listener = listener;
		this.method = method;
		this.repository = repository;
		this.categoryId = categoryId;
		this.value = value;
	}

	public ObjectsAsycnTask(IReadRepository listener,
			ERepositoryManagerMethods method) {
		this.listener = listener;
		this.method = method;
	}

	public ObjectsAsycnTask(IReadRepository listener,
			ERepositoryManagerMethods method, Bundle bundle) {
		this.listener = listener;
		this.method = method;
		this.bundle = bundle;
	}

	@Override
	protected Void doInBackground(Void... params) {
		response = new AsyncTaskResult();
		try {
			if (method != ERepositoryManagerMethods.UpdateData
					&& repository == null)
				throw new CommunicationException("",
						ExceptionErrorCodes.NoRepository);
			switch (method) {

			case UpdateData: {
				long results = -1;
				DataBaseManager dbm = DataBaseManager.getInstance();
				dbm.checkIsOpen();
				dbm.getDataBase().beginTransaction();
				try {

					String addressCategory = bundle.getString("CategoryLink");
					String addressObjects = bundle.getString("ObjectsLink");
					System.out.println("Start Download");
					CategoriesRepository catrep = new CategoriesRepository();
					ContentObjectRepository objrep = new ContentObjectRepository();

					results = catrep.getFromServer(dbm, addressCategory, this);
					System.out.println("Categories Downloaded");
					results = objrep.getFromServer(dbm, addressObjects, this);
					// TODO: doko�czy� robienie tego w transakcji

					System.out.println("Objects Downlaoded");
					dbm.getDataBase().setTransactionSuccessful();
				} catch (Exception e) {
					throw new CommunicationException("",
							ExceptionErrorCodes.UpdateError);
				} finally {
					System.out.println("Finish actions");
					dbm.getDataBase().endTransaction();

				}
				dbm.close();
				System.out.println("After close");
				new CategoriesRepository().readAll();
				System.out.println("After readALL");
				if (results == -1)
					throw new CommunicationException("",
							ExceptionErrorCodes.UpdateError);
				response.bundle = results;
			}
				break;
			case InsertOrUpdate:
				response.bundle = repository.insertOrUpdate(item, null);
				break;
			case Read:
				break;
			case Search:
				if (repository instanceof ContentObjectRepository) {
					response.bundle = ((ContentObjectRepository) repository)
							.searchObjects(categoryId, value);
				} else
					throw new CommunicationException("",
							ExceptionErrorCodes.InvalidRepository);
				break;
			case ReadById:
				response.bundle = repository.readById(item.getId());
				break;
			case ReadAll:
				response.bundle = repository.readAll();
				break;
			case ReadFavorites:
				if (repository instanceof ContentObjectRepository) {
					response.bundle = ((ContentObjectRepository) repository)
							.readFavorites();
				} else
					throw new CommunicationException("",
							ExceptionErrorCodes.InvalidRepository);
			default:
				break;
			}

		} catch (Exception ex) {
			response.exception = new RepositoryException(
					ERepositoryException.AsyncTaskException, ex);
		}

		return null;
	}

	@Override
	public void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (listener != null) {
			listener.onTaskEnd();
			if (response.exception == null)
				listener.onTaskResponse(response);
			else
				listener.onTaskInvalidResponse(response.exception);
		}
	}

	@Override
	public void onPreExecute() {
		super.onPreExecute();
		if (listener != null) {
			listener.onTaskStart("");
		}
	}

	public void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
		if (listener != null) {
			listener.onTaskProgressUpdate(1);
		}
	}

	public class AsyncTaskResult {
		public Object bundle;
		public RepositoryException exception;
	}

	@Override
	public void onObjectsProgressUpdate(int progressPercent) {
		if (listener != null) {
			int actualProgress = 100 / progressPercent;
			actualProgress = actualProgress + progressPercent / 1;
			listener.onTaskProgressUpdate(actualProgress);
		}

	}

	@Override
	public boolean checkIsTaskCancled() {
		// TODO Auto-generated method stub
		return false;
	}

}
