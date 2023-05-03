import React, {useState} from "react";
import "static/external/css/bootstrap.min.css";

export default function Auth(props)
{
    let [authMode, setAuthMode] = useState("signin");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const changeAuthMode = () =>
    {
        setAuthMode(authMode === "signin" ? "signup" : "signin");
    }

    function sendLoginRequest()
    {
        const reqBody = {
            username: username,
            password: password
        }

        fetch("login", {
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            method: "POST",
            body: JSON.stringify(reqBody)
        })
            .then((response) =>
            {
                if (response.status === 200)
                {
                    return Promise.all([response.json(), response.headers]);
                } else
                {
                    return Promise.reject("Invalid credentials.")
                }
            })
            .then(() =>
            {
                window.location.href = "dashboard";
            })
            .catch(message =>
            {
                alert(message);
            });
    }

    if (authMode === "signin")
    {
        return (
            <div className="Auth-form-container">
                <form className="Auth-form" acceptCharset="utf-8">
                    <div className="Auth-form-content">
                        <h3 className="Auth-form-title">Sign In</h3>
                        <div className="text-center">
                            Not registered yet?{" "}
                            <span className="link-primary" role="button" onClick={changeAuthMode}>Sign Up</span>
                        </div>
                        <div className="form-group mt-3">
                            <label htmlFor="username">Username</label>
                            <input type="email" name="username" className="form-control mt-1" value={username}
                                   placeholder="Enter username" onChange={(event) => setUsername(event.target.value)}/>
                        </div>
                        <div className="form-group mt-3">
                            <label htmlFor="password">Password</label>
                            <input type="password" name="password" className="form-control mt-1" value={password}
                                   placeholder="Enter password" onChange={(event) => setPassword(event.target.value)}/>
                        </div>
                        <div className="d-grid gap-2 mt-3">
                            <button id="submit" type="button" className="btn btn-primary" onClick={() => sendLoginRequest()}>
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

    return (
        <div className="Auth-form-container">
            <form className="Auth-form" acceptCharset="utf-8">
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
                        <button type="submit" className="btn btn-primary">
                            Submit
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