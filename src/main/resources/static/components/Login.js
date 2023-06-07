// Core libs
import {useState} from "react";
import "static/external/css/bootstrap.min.css";

export default function Login()
{
    //TODO: Hoe de data-binding terug naar App.js doen (user object)?
    const [username, setUsername] = useState("");
    const [url, setUrl] = useState("");

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
                } else
                {
                    //TODO: text() is hier nog Promise, hoe op te lossen?
                    return Promise.reject("Invalid credentials: " + response.text())
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
                } else
                {
                    return Promise.reject("Insufficient rights.")
                }
            })
    }

    //TODO: Idem, hoe hier de authmode / activecomponent beinvloeden?
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
                        <input type="password" name="password" className="form-control mt-1"
                               placeholder="Enter password"/>
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
