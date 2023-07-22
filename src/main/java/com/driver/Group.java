package com.driver;

public class Group {
    private String name;
    private int numberOfParticipants;
    private String groupAdmin;
    private int  noOfmsg;

    public int getNoOfmsg() {
        return noOfmsg;
    }

    public void setNoOfmsg(int noOfmsg) {
        this.noOfmsg = noOfmsg;
    }

    public Group(String name, int numberOfParticipants) {
        this.name = name;
        this.numberOfParticipants = numberOfParticipants;
    }

    public String getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(String groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public Group() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }
}
