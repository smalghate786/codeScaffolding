package com.ai.codeScaffolding;

import java.util.List;

public class EntityRequest {
    private String entityName;
    private List<FieldRequest> fields;
    private String packageName;

    // getters and setters
    public String getEntityName() { return entityName; }
    public void setEntityName(String entityName) { this.entityName = entityName; }
    public List<FieldRequest> getFields() { return fields; }
    public void setFields(List<FieldRequest> fields) { this.fields = fields; }
    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
}



