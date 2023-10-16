package ca.cmpt213.as2;

import java.util.List;

public class Tokimon {
    private String name;
    private String id;
    private Compatibility compatibility;

    private String extraComment;
    private List<Compatibility> compatibilities;


    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Compatibility> getCompatibilities() {
        return compatibilities;
    }

    public void setCompatibilities(List<Compatibility> compatibilities) {
        this.compatibilities = compatibilities;
    }

    public String getExtraComment() {
        return extraComment;
    }

    public void setExtraComment(String extraComment) {
        this.extraComment = extraComment;
    }

    public void setCompatibility(Compatibility compatibility) {
        this.compatibility = compatibility;
    }

    public Compatibility getCompatibility() {
        return compatibility;
    }
}

class Compatibility {
    // private String name;
    // private String id;
    private double score;
    private String comment;

    // Getters and setters
    // public String getName() {
    //     return name;
    // }

    // public void setName(String name) {
    //     this.name = name;
    // }

    // public String getId() {
    //     return id;
    // }

    // public void setId(String id) {
    //     this.id = id;
    // }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
