package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WhatsappService {
    @Autowired
    WhatsAppRepo whatsAppRepo=new WhatsAppRepo();
    public String createUser(String name, String mobile) {
        Optional<User> user=whatsAppRepo.findUserBYNumber(mobile);
        if(user.isPresent()){
            throw new UserExists();
        }
        whatsAppRepo.addUser(name,mobile);
        return "SUCCESS";
    }

    public Group createGroup(List<User> users) {
        Group gp=whatsAppRepo.createGroup(users);
        return gp;
    }

    public int createMessage(String content) {
        return whatsAppRepo.createMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group) {
        Optional<Group> grp=whatsAppRepo.FindGroupByname(group.getName());
        if(grp.isEmpty()){
            throw new RuntimeException("Group does not exist");
        }
        List<User> usrs=whatsAppRepo.FindUsersBygroupName(grp.get().getName());
        boolean isMember=false;
        for(User u:usrs){
            if(u.equals(sender)){
                isMember=true;
                break;
            }
        }
        if(isMember==false){
            throw new RuntimeException("You are not allowed to send message");
        }
       int cntmsg= whatsAppRepo.addMessage(sender.getName(),message,group.getName());
        return cntmsg;
    }

    public String changeAdmin(User approver, User user, Group group) {
        Optional<Group> grp=whatsAppRepo.FindGroupByname(group.getName());
        if(grp.isEmpty()){
            throw new RuntimeException("Group does not exist");
        }
        if(grp.get().getGroupAdmin().equals(approver.getName())==false){
            throw new RuntimeException("Approver does not have rights");
        }
        List<User> usrs=whatsAppRepo.FindUsersBygroupName(grp.get().getName());
        boolean isMember=false;
        for(User u:usrs){
            if(u.equals(user)){
                isMember=true;
                break;
            }
        }
        if(isMember==false){
            throw new RuntimeException("User is not a participant");
        }
        whatsAppRepo.changeAdmin(grp.get(),approver,user);
        return "SUCCESS";
    }

    public int removeUser(User user) {
        List<String> groups=whatsAppRepo.findAllgroup();
        boolean isPresent=false;
        String usersGpName="";
        for(String gpName:groups){
            List<User> users=whatsAppRepo.FindUsersBygroupName(gpName);
            boolean isFound=false;
            for(User u:users){
                if(u.equals(user)){
                    isFound=true;
                    isPresent=true;
                    break;
                }
            }
            if(isFound){
                usersGpName=gpName;
                whatsAppRepo.DeleteFromGroup(gpName,user);
                break;
            }
        }
        if(isPresent==false){
            throw new RuntimeException("User not found");
        }
        int cnt= whatsAppRepo.DeleteUser(user,usersGpName);
        return cnt;
    }
}
