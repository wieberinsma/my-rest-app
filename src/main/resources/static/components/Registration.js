import {useState} from "react";

export default function Registration(props)
{
    const [username, setUsername] = useState(props.username);
    const [password, setPassword] = useState(props.password);
    const [firstName, setFirstName] = useState("");
    const [lastname, setLastname] = useState("");

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
                    //TODO: Idem, hoe hier de authmode / activecomponent beinvloeden?
                    setAuthMode("signin");
                    return Promise.resolve();
                }
                else
                {
                    //TODO: text() is hier nog Promise, hoe op te lossen?
                    return Promise.reject("Invalid registration provided: " + response.text())
                }
            })
            .catch(message =>
            {
                alert(message);
            });
    }

    //TODO: Idem, hoe hier de authmode / activecomponent beinvloeden?
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