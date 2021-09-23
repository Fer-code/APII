package com.example.api.models;

public class UserClass {

        // string variables for our name and job
        public int ID;
    public String name;
    public String email;
    public String password;

        public UserClass(){}

    public UserClass(int _Id, String _name, String _email, String _password) {
            this.ID = _Id;
        this.name = _name;
        this.email = _email;
        this.password = _password;
    }

        public UserClass(String _name, String _email, String _password) {
            this.name = _name;
            this.email = _email;
            this.password = _password;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
