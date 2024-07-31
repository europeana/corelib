package eu.europeana.corelib.definitions.edm.entity;

import java.util.Date;

public interface ChangeLog{

    String getType();

    void setType(String type);

    String getActivityContext();

    void setActivityContext(String activityContext);

    String getActivityObject();

    void setActivityObject(String activityObject);

    Date getActivityEndTime();

    void setActivityEndTime(Date activityEndTime);
}
