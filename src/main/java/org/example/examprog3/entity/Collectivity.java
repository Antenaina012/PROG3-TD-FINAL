package org.example.examprog3.entity;

import java.util.List;
import java.util.Objects;

public class Collectivity {
    private String id;
    private String location;
    private boolean federationApproval;
    private CollectivityStructure structure;
    private List<Member> members;

    public Collectivity() {
    }

    public Collectivity(String id, String location, boolean federationApproval,
                        CollectivityStructure structure, List<Member> members) {
        this.id = id;
        this.location = location;
        this.federationApproval = federationApproval;
        this.structure = structure;
        this.members = members;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public boolean isFederationApproval() { return federationApproval; }
    public void setFederationApproval(boolean federationApproval) { this.federationApproval = federationApproval; }

    public CollectivityStructure getStructure() { return structure; }
    public void setStructure(CollectivityStructure structure) { this.structure = structure; }

    public List<Member> getMembers() { return members; }
    public void setMembers(List<Member> members) { this.members = members; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Collectivity that = (Collectivity) o;
        return Objects.equals(id, that.id) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location);
    }
}

