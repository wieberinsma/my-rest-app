// Core libs
import React, {useState} from "react";
import "static/external/css/bootstrap.min.css";

export default function Auth()
{
    let [authMode, setAuthMode] = useState("signin");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [url, setUrl] = useState("");

    const changeAuthMode = () =>
    {
        setAuthMode(authMode === "signin" ? "signup" : "signin");
    }

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
                if (response.status === 200)
                {
                    return Promise.all([response.json(), response.headers]);
                }
                else
                {
                    return Promise.reject("Invalid credentials.")
                }
            })
            .then(response =>
                {
                    let json = response[0];

                    setUsername(json.username);
                    if (json.action === 'LOGIN')
                    {
                        redirect(json);
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
        return undefined;
    }

    function redirect(json)
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
                                <input type="email" name="username" className="form-control mt-1" value={username}
                                       placeholder="Enter username"
                                       onChange={(event) => setUsername(event.target.value)}/>
                            </div>
                            <div className="form-group mt-3">
                                <label htmlFor="password">Password</label>
                                <input type="password" name="password" className="form-control mt-1" value={password}
                                       placeholder="Enter password"
                                       onChange={(event) => setPassword(event.target.value)}/>
                            </div>
                            <div className="d-grid gap-2 mt-3">
                                <button id="submit" type="button" className="btn btn-primary"
                                        onClick={() => sendLoginRequest()}>
                                    Login
                                </button>
                            </div>
                            <p className="text-center mt-2">
                                Forgot <a href="../.#">password</a>?
                            </p>
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
                                <label>Full Name</label>
                                <input
                                    type="email"
                                    className="form-control mt-1"
                                    placeholder="e.g Jane Doe"
                                />
                            </div>
                            <div className="form-group mt-3">
                                <label>Email address</label>
                                <input
                                    type="email"
                                    className="form-control mt-1"
                                    placeholder="Email Address"
                                />
                            </div>
                            <div className="form-group mt-3">
                                <label>Password</label>
                                <input
                                    type="password"
                                    className="form-control mt-1"
                                    placeholder="Password"
                                />
                            </div>
                            <div className="d-grid gap-2 mt-3">
                                <button id="submit" type="button" className="btn btn-primary"
                                        onClick={() => sendRegistrationRequest()}>
                                    Login
                                </button>
                            </div>
                            <p className="text-center mt-2">
                                Forgot <a href="../.#">password?</a>
                            </p>
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
