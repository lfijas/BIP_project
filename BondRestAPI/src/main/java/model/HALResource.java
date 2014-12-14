package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class HALResource extends ResourceSupport {

    private final Map<String, List<PurchaseHistory>> embedded = new HashMap<String, List<PurchaseHistory>>();

    @JsonProperty("_embedded")
    public Map<String, List<PurchaseHistory>> getEmbeddedResources() {
        return embedded;
    }

    public void embedResource(String relationship, List<PurchaseHistory> resources) {
        embedded.put(relationship, resources);
    }  
}