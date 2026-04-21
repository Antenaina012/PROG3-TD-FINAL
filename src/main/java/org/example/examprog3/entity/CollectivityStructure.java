package org.example.examprog3.entity;

import java.util.Objects;

public class CollectivityStructure {
    private Member president;
    private Member vicePresident;
    private Member treasurer;
    private Member secretary;

    public CollectivityStructure() {
    }

    public CollectivityStructure(Member president, Member vicePresident, Member treasurer, Member secretary) {
        this.president = president;
        this.vicePresident = vicePresident;
        this.treasurer = treasurer;
        this.secretary = secretary;
    }

    public Member getPresident() { return president; }
    public void setPresident(Member president) { this.president = president; }

    public Member getVicePresident() { return vicePresident; }
    public void setVicePresident(Member vicePresident) { this.vicePresident = vicePresident; }

    public Member getTreasurer() { return treasurer; }
    public void setTreasurer(Member treasurer) { this.treasurer = treasurer; }

    public Member getSecretary() { return secretary; }
    public void setSecretary(Member secretary) { this.secretary = secretary; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CollectivityStructure that = (CollectivityStructure) o;
        return Objects.equals(president, that.president) &&
                Objects.equals(vicePresident, that.vicePresident) &&
                Objects.equals(treasurer, that.treasurer) &&
                Objects.equals(secretary, that.secretary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(president, vicePresident, treasurer, secretary);
    }
}
