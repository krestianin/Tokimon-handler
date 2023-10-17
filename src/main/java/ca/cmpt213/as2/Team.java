package ca.cmpt213.as2;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<Tokimon> team;
    private String extra_comments;
    public List<Tokimon> getTeam() {
        return team;
    }
    public String getExtra_comments() {
        return extra_comments;
    }
    public void setTeam(List<Tokimon> team) {
        this.team = team;
    }
    public void setExtra_comments(String extra_comments) {
        this.extra_comments = extra_comments;
    }

    public void addTokimon(Tokimon t)
    {
        this.team.add(t);
    }
    public Team() {
        team = new ArrayList<>();
    }
    
}
