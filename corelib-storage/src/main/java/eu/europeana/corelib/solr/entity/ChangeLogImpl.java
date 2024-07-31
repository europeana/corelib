package eu.europeana.corelib.solr.entity;

import dev.morphia.annotations.Embedded;
import eu.europeana.corelib.definitions.edm.entity.ChangeLog;
import java.util.Date;

@Embedded(useDiscriminator = false)
public class ChangeLogImpl implements ChangeLog {

    private String type;
    private String activityContext;
    private String activityObject;
    private Date activityEndTime;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getActivityContext() {
        return activityContext;
    }

    @Override
    public void setActivityContext(String activityContext) {
        this.activityContext = activityContext;
    }

    @Override
    public String getActivityObject() {
        return activityObject;
    }

    @Override
    public void setActivityObject(String activityObject) {
        this.activityObject = activityObject;
    }

    @Override
    public Date getActivityEndTime() {
        return activityEndTime;
    }

    @Override
    public void setActivityEndTime(Date activityEndTime) {
        this.activityEndTime = activityEndTime;
    }
}
