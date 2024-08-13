package eu.europeana.corelib.solr.entity;

import dev.morphia.annotations.Embedded;
import eu.europeana.corelib.definitions.edm.entity.ChangeLog;
import java.util.Date;

@Embedded(useDiscriminator = false)
public class ChangeLogImpl implements ChangeLog {

    private String type;
    private String context;
    private Date endTime;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getContext() {
        return context;
    }

    @Override
    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public Date getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
