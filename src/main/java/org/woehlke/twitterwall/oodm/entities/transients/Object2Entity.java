package org.woehlke.twitterwall.oodm.entities.transients;

import java.io.Serializable;

public class Object2Entity implements Serializable {

    private long objectId;

    private long entityId;

    private Object2EntityTable object2EntityTable;

    public Object2Entity(long objectId, long entityId, Object2EntityTable object2EntityTable) {
        this.objectId = objectId;
        this.entityId = entityId;
        this.object2EntityTable = object2EntityTable;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public Object2EntityTable getObject2EntityTable() {
        return object2EntityTable;
    }

    public void setObject2EntityTable(Object2EntityTable object2EntityTable) {
        this.object2EntityTable = object2EntityTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Object2Entity)) return false;

        Object2Entity that = (Object2Entity) o;

        if (objectId != that.objectId) return false;
        if (entityId != that.entityId) return false;
        return object2EntityTable == that.object2EntityTable;
    }

    @Override
    public int hashCode() {
        int result = (int) (objectId ^ (objectId >>> 32));
        result = 31 * result + (int) (entityId ^ (entityId >>> 32));
        result = 31 * result + (object2EntityTable != null ? object2EntityTable.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Object2Entity{" +
                "objectId=" + objectId +
                ", entityId=" + entityId +
                ", object2EntityTable=" + object2EntityTable +
                '}';
    }
}
