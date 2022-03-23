package nl.han.resttest.domain.user.impl.model;

public class User
{
    private String username;
    
    private String fullName;
    
    private String password;
    
    private String token;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String firstName, String lastName)
    {
        this.fullName = firstName + " " + lastName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}
