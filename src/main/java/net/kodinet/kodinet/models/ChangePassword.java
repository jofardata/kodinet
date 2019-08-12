package net.kodinet.kodinet.models;

import lombok.Data;

@Data
public class ChangePassword {
    private String username;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
