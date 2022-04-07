package model;

public class Login {
    private String login;// опиши поля
    private String password;

    public Login(String login, String password){// добавь конструкторы — со всеми параметрами и без параметров
        this.login = login;
        this.password = password;
    }
    public Login(){

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


}
