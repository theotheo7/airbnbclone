import React, {useState} from 'react';
import './Navigation.css';
import {DefaultButton, TextField} from "@fluentui/react";
import {useLocation} from "react-router-dom";
import {UserLogin} from "../../models/UserLogin";
import {authenticate} from "../../services/Authentication";



function Navigation() {
    const [Username, setUsername] = useState<string | undefined>("");
    const [Password, setPassword] = useState<string | undefined>("")
    const location = useLocation();

    function _onChangeUsername(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setUsername(newValue);
    }

    function _onChangePassword(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setPassword(newValue);
    }

    async function _onClickLogin() {
        if (Username !== undefined && Password !== undefined) {
            const userLogin = new UserLogin(Username, Password);
            try {
                const response = await authenticate(userLogin);

                console.log(response);
            } catch (err) {
                console.log(err);
            } finally {
                console.log("Success!");
            }
        }
    }

    return (
        <div className="navigation-container">
            <div className="logo">
                Airbnb Clone
            </div>
            {location.pathname === "/register" ?
                <></>
                :
                <div className="login">
                    <TextField
                        label="Username"
                        value={Username}
                        onChange={_onChangeUsername}
                    />
                    <TextField
                        type="password"
                        label="Password"
                        value={Password}
                        onChange={_onChangePassword}
                    />
                    <div className="register-prompt">
                        Not registered? Click <a href="https://localhost:8080/register">here</a> to register!
                    </div>
                    <DefaultButton
                        className="login-button"
                        text="Login"
                        label="Login"
                        onClick={_onClickLogin}
                    />
                </div>
            }
        </div>
    )
}

export default Navigation;
