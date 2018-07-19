package eu.europeana.corelib.solr.entity;

import org.mongodb.morphia.annotations.Entity;
import eu.europeana.corelib.definitions.edm.entity.BasicProxy;

import java.util.Locale;

@Entity("BasicProxy")
public class BasicProxyImpl extends PhysicalThingImpl implements BasicProxy {

	private String[] proxyIn;
	private String proxyFor;

	@Override
	public String[] getProxyIn() {
		return this.proxyIn;
	}

	@Override
	public void setProxyIn(String[] proxyIn) {
		this.proxyIn = proxyIn!=null?proxyIn.clone():null;
	}

	@Override
	public String getProxyFor() {
        // 2018-07-19 PE: startsWith check can most likely be omitted for Metis,
        // but it's added for now in case we want to test this version with our UIM Mongo cluster
        if (!proxyFor.toLowerCase(Locale.getDefault()).startsWith("/item")) {
            return ("/item" + proxyFor);
        }
		return this.proxyFor;
	}

	@Override
	public void setProxyFor(String proxyFor) {
	    this.proxyFor = proxyFor;
	}


//	@Override
//	public boolean equals(Object o) {
//		if (o == null) {
//			return false;
//		}
//		if (o.getClass() == this.getClass()) {
//			return (this.getProxyIn() != null && ((ProxyImpl) o).getProxyIn() != null) 
//				? this.getProxyIn().equals(((ProxyImpl) o).getProxyIn()) 
//				: this.getAbout().equals(((ProxyImpl) o).getAbout());
//		}
//		return false;
//	}
//
//	@Override
//	public int hashCode() {
//		return this.getAbout()!=null?this.getAbout().hashCode():this.id.hashCode();
//	}
}
