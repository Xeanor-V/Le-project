import java.net.URL;
import java.util.HashMap;

public abstract class ResourceCache {

	protected HashMap resources;
	
	public ResourceCache() {
		resources = new HashMap();
	}
	
	protected Object getResource(String name) {
		Object res = resources.get(name);
		if (res == null) {
			res = loadResource(name);
			resources.put(name,res);
		}
		return res;
	}

	public abstract Object loadResource(String name);
}