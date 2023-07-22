package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class WhatsAppRepo {

    Map<String,User> users=new HashMap<>();
    Map<String,Group> groups=new HashMap<>();
    Map<String,List<User>> groupWithusers=new HashMap<>();
    Map<String,Integer> userWithMsg=new HashMap<>();
    Map<String,List<Message>> groupWithMsg=new HashMap<>();
    Map<Integer,Message> Messeges=new HashMap<>();

    public Optional<User> findUserBYNumber(String number){
        if(users.containsKey(number)) {
           return Optional.of(users.get(number));
        }
        else{
            return Optional.empty();
        }
    }

    public void addUser(String name, String mobile) {
        User user=new User(name,mobile);
        users.put(user.getMobile(),user);
    }

    public Group createGroup(List<User> users) {
        Group gp=new Group();
        List<User> usrs=users;
        User user=users.get(0);
        gp.setGroupAdmin(user.getName());
        gp.setNumberOfParticipants(users.size());
        String groupName=users.get(1).getName();
        if(users.size()>2){
            groupName="Group "+(groups.entrySet().size()+1);
        }
        gp.setName(groupName);

        groups.put(gp.getName(),gp);
        groupWithusers.put(gp.getName(),users);
        return gp;
    }

    public int createMessage(String content) {
        Message msg=new Message();

        msg.setId(Messeges.size()+1);
        msg.setContent(content);
        Messeges.put(msg.getId(),msg);
        return msg.getId();
    }

    public Optional<Group> FindGroupByname(String name) {
        if(groups.containsKey(name)){
            return Optional.of(groups.get(name));
        }
        return Optional.empty();
    }

    public List<User> FindUsersBygroupName(String name) {
        if(groupWithusers.containsKey(name)){
            return groupWithusers.get(name);
        }
        return new ArrayList<>();
    }

    public int addMessage(String name, Message message,String groupName) {

        userWithMsg.put(name,userWithMsg.getOrDefault(name,0)+1);
        int msgcnt=0;
        List<User> users=groupWithusers.getOrDefault(groupName,new ArrayList<>());
         for(User u:users){
             msgcnt+=userWithMsg.getOrDefault(u.getName(),0);
         }
         groupWithMsg.getOrDefault(groupName,new ArrayList<>()).add(message);
        return msgcnt;
    }

    public void changeAdmin(Group grp,User approver, User user) {
        groups.get(grp.getName()).setGroupAdmin(user.getName());
    }

    public List<String> findAllgroup() {
        return new ArrayList<>(groups.keySet());
    }



    public void DeleteFromGroup(String gpName, User user) {
        groupWithusers.get(gpName).remove(user);
    }

    public int DeleteUser(User user, String usersGpName) {
        users.remove(user.getMobile());
        userWithMsg.remove(user.getName());
        int msgcnt=0;
        List<User> users=groupWithusers.getOrDefault(usersGpName,new ArrayList<>());
        for(User u:users){
            msgcnt+=userWithMsg.getOrDefault(u.getName(),0);
        }
        int cnt=groupWithMsg.get(usersGpName).size()+msgcnt;
        return cnt;
    }
}
