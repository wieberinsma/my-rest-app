// Core libs
import React, {useState} from 'react';
import ReactDOM from 'react-dom';

// Components
import Login from "./static/components/Login";
import Registration from "./static/components/Registration";
import Private from "./static/components/Private";

// Styling
import "./App.css";

export default function App()
{
    const [authMode, setAuthMode] = useState("signin");
    const [url, setUrl] = useState("");
    const [user, setUser] = useState({firstName: '', lastName: '', username: '', password: ''})

    const changeAuthMode = () =>
    {
        setAuthMode(authMode === "signin" ? "signup" : "signin");
    }

    if (!url)
    {
        if (authMode === "signin")
        {
            return <Login/>;
        } else if (authMode === "signup")
        {
            return <Registration username={user.username} password={user.password}/>;
        }
    } else
    {
        return <Private username={user.username}/>;
    }
}

ReactDOM.render(<Login/>, document.getElementById("root"));