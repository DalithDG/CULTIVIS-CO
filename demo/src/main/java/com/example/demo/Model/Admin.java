package com.example.demo.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_admin")
    private int idAdmin;

    @Column(name = "user_adm", length = 50, nullable = false, unique = true)
    private String userAdm;

    @Column(name = "password", length = 50, nullable = false)
    private String password;

    public Admin() {
    }

    public Admin(int idAdmin, String userAdm, String password) {
        this.idAdmin = idAdmin;
        this.userAdm = userAdm;
        this.password = password;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getUserAdm() {
        return userAdm;
    }

    public void setUserAdm(String userAdm) {
        this.userAdm = userAdm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}