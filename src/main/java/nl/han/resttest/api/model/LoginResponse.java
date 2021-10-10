package nl.han.resttest.api.model;

public class LoginResponse
{
    private String token;

    private String user;

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }
}
