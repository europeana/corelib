package eu.europeana.corelib.definitions.edm.entity;

import java.util.Date;

public interface ChangeLog{

    String getType();

    void setType(String type);

    String getContext();

    void setContext(String context);

    Date getEndTime();

    void setEndTime(Date endTime);
}
