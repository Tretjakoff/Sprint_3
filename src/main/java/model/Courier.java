package model;

public class Courier {
    private String login;// опиши поля
    private String password;
    private String firstName;

    public Courier(String login, String password, String firstName){// добавь конструкторы — со всеми параметрами и без параметров
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
    public Courier(){

    }
    // добавь геттеры и сеттеры для всех полей
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}

