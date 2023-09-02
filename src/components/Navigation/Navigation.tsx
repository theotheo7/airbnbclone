import React from 'react';
import './Navigation.css';
import {DefaultButton, TextField} from "@fluentui/react";
import {useLocation} from "react-router-dom";


function Navigation() {
    const location = useLocation();

    return (
    <div className="navigation-container">
        <div className="logo">
            Airbnb Clone
        </div>
        {location.pathname === "/register" ?
            <></>
        :
            <div className="login">
                <TextField label="Username"/>
                <TextField type="password" label="Password"/>
                <div className="register-prompt">
                    Not registered? Click <a href="https://localhost:8080/register">here</a> to register!
                </div>
                <DefaultButton className="login-button" text="Login" label="Login"/>
            </div>
        }
    </div>
    )
}

export default Navigation;
