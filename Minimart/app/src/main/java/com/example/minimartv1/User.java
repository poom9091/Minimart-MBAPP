package com.example.minimartv1;

public class User {
    private String Name;
    private String Email;
    private String password;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }



    public String getName(){return Name;}
    public void setName(String username){this.Name = username;}

    public String getPassword() {return password;}
    public void setPassword(String password){this.password = password;}

    public User(String username,String password){
        this.Name = username;
        this.password = password;
    }
    public User(){

    }

}
