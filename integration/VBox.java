package integration;

import org.virtualbox_5_0.*;

public class VBox {
	public void init() {
		VirtualBoxManager vbm = VirtualBoxManager.createInstance(null);
		vbm.connect("http://localhost:18083","","");
		IVirtualBox vb = vbm.getVBox();
		IMachine vm = vb.createMachine(null, "Test", null, "Other", null);
		vm.addStorageController("test",StorageBus.SATA);
		vm.saveSettings();
		vbm.getVBox().registerMachine(vm);
	}

}
