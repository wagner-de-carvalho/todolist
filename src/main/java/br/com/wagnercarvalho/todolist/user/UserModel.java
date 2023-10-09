package br.com.wagnercarvalho.todolist.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {
    private String username;
    private String name;
    private String password;

    @Override
    public String toString() {
        return String.format("UserModel [userName: %s, name: %s]", this.username, this.name);
    }
}
