package pl.netplus.appbase.managers;

import pl.netplus.appbase.asynctask.ObjectsAsycnTask;
import pl.netplus.appbase.interfaces.IReadRepository;
import pl.netplus.appbase.repositories.ReadAllDataRepository;

public class ItemContainerManager {

	public void readAllData(IReadRepository listener) {
		startTask(listener);
	}

	private void startTask(IReadRepository listener) {
		ObjectsAsycnTask task;
		task = new ObjectsAsycnTask(listener, new ReadAllDataRepository());
		task.execute((Void) null);
	}
}
