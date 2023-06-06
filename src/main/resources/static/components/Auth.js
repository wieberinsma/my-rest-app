// Core libs
import React, {useState} from "react";
import "static/external/css/bootstrap.min.css";

export default function Auth()
{
    let [authMode, setAuthMode] = useState("signin");

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastname, setLastname] = useState("");
    const [url, setUrl] = useState("");

    const changeAuthMode = () =>
    {
        setAuthMode(authMode === "signin" ? "signup" : "signin");
    }

    /**
     * Use of form data object due to auto-generated form-login process of Spring Security, instead of JSON.stringify().
     */
    function sendLoginRequest()
    {
        const payload = new URLSearchParams();
        const formData = new FormData(document.getElementById("login-form"));

        for (let pair of formData)
        {
            payload.append(pair[0], pair[1]);
        }

        fetch("/login", {
            method: 'post',
            body: payload
        })
            .then(response =>
            {
                if (response.ok)
                {
                    return Promise.all([response.json(), response.headers]);
                }
                else
                {
                    return Promise.reject("Invalid credentials: " + + response.text())
                }
            })
            .then(response =>
                {
                    let json = response[0];

                    setUsername(json.username);
                    if (json.action === 'LOGIN')
                    {
                        doRedirect(json);
                    }
                }
            )
            .catch(message =>
            {
                alert(message);
            });
    }

    function sendRegistrationRequest()
    {
        fetch("/register", {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                'firstName': firstName,
                'lastName': lastname,
                'username': username,
                'password': password
            })
        })
            .then(response =>
            {
                if (response.ok)
                {
                    setAuthMode("signin");
                    return Promise.resolve();
                }
                else
                {
                    return Promise.reject("Invalid registration provided: " + response.text())
                }
            })
            .catch(message =>
            {
                alert(message);
            });
    }

    function doRedirect(json)
    {
        let url = json.redirectUrl;

        fetch(url, {
            method: 'get'
        })
            .then(response =>
            {
                if (response.status === 200)
                {
                    setUrl(url);
                    return Promise.resolve();
                }
                else
                {
                    return Promise.reject("Insufficient rights.")
                }
            })
    }

    if (!url)
    {
        if (authMode === "signin")
        {
            return (
                <div className="Auth-form-container">
                    <form id="login-form" className="Auth-form" acceptCharset="utf-8">
                        <div className="Auth-form-content">
                            <h3 className="Auth-form-title">Sign In</h3>
                            <div className="text-center">
                                Not registered yet?{" "}
                                <span className="link-primary" role="button" onClick={changeAuthMode}>Sign Up</span>
                            </div>
                            <div className="form-group mt-3">
                                <label htmlFor="username">Username</label>
                                <input type="email" name="username" className="form-control mt-1" placeholder="Enter username"/>
                            </div>
                            <div className="form-group mt-3">
                                <label htmlFor="password">Password</label>
                                <input type="password" name="password" className="form-control mt-1" placeholder="Enter password"/>
                            </div>
                            <div className="d-grid gap-2 mt-3">
                                <button id="submit" type="button" className="btn btn-primary"
                                        onClick={() => sendLoginRequest()}>
                                    Login
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            );
        }
        else if (authMode === "signup")
        {
            return (
                <div className="Auth-form-container">
                    <form id="registration-form" className="Auth-form" acceptCharset="utf-8">
                        <div className="Auth-form-content">
                            <h3 className="Auth-form-title">Sign Up</h3>
                            <div className="text-center">
                                Already registered?{" "}
                                <span className="link-primary" role="button" onClick={changeAuthMode}>Sign In</span>
                            </div>
                            <div className="form-group mt-3">
                                <label htmlFor="firstName">First name</label>
                                <input name="firstName" className="form-control mt-1" value={firstName}
                                       placeholder="First name"
                                       onChange={(event) => setFirstName(event.target.value)}
                                />
                            </div>
                            <div className="form-group mt-3">
                                <label htmlFor="lastname">Lastname</label>
                                <input name="lastname" className="form-control mt-1" value={lastname}
                                       placeholder="Lastname"
                                       onChange={(event) => setLastname(event.target.value)}
                                />
                            </div>
                            <div className="form-group mt-3">
                                <label htmlFor="username">Username</label>
                                <input name="username" className="form-control mt-1" value={username}
                                       placeholder="Username"
                                       onChange={(event) => setUsername(event.target.value)}/>
                            </div>
                            <div className="form-group mt-3">
                                <label htmlFor="password">Password</label>
                                <input name="password" type="password" className="form-control mt-1" value={password}
                                       placeholder="Password"
                                       onChange={(event) => setPassword(event.target.value)}
                                />
                            </div>
                            <div className="d-grid gap-2 mt-3">
                                <button id="submit" type="button" className="btn btn-primary"
                                        onClick={() => sendRegistrationRequest()}>
                                    Register
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            );
        }
    }
    else
    {
        return (
            <div className="Private-container">
                <h3 className="Private-title">Welcome {username}, you have reached a private resource!</h3>
            </div>
        )
    }
}
