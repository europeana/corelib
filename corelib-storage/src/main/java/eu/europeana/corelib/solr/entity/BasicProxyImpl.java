package eu.europeana.corelib.solr.entity;

import dev.morphia.annotations.Entity;
import eu.europeana.corelib.definitions.edm.entity.BasicProxy;

@Entity("BasicProxy")
public class BasicProxyImpl extends PhysicalThingImpl implements BasicProxy {

	private String[] proxyIn;
	private String proxyFor;
	private String[] lineage;

	@Override
	public String[] getProxyIn() {
		return this.proxyIn;
	}

	@Override
	public void setProxyIn(String[] proxyIn) {
		this.proxyIn = proxyIn != null ? proxyIn.clone() : null;
	}

	@Override
	public String getProxyFor() {
		return this.proxyFor;
	}

	@Override
	public void setProxyFor(String proxyFor) {
	    this.proxyFor = proxyFor;
	}

	@Override
	public String[] getLineage() {
		return this.lineage;
	}

	@Override
	public void setLineage(String[] lineage) {
		this.lineage = lineage != null ? lineage.clone() : null;
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
