import React, {useState} from 'react';
import './Navigation.css';
import {DefaultButton, TextField} from "@fluentui/react";
import {useLocation} from "react-router-dom";
import {UserLogin} from "../../models/UserLogin";
import {authenticate, getUsername, logout, storeUserInfo} from "../../services/Authentication";

function Navigation() {
    const [Username, setUsername] = useState<string | undefined>("");
    const [Password, setPassword] = useState<string | undefined>("");
    const [isAuthenticated, setIsAuthenticated] = useState(false);
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
                storeUserInfo(Username, response["token"]);
                setIsAuthenticated(true);
            } catch (err) {
                console.log(err);
            } finally {
                console.log("Bye!");
            }
        }
    }

    function _onClickLogout() {
        logout();
        setIsAuthenticated(false);
    }

    return (
        <div className="navigation-container">
            <div className="logo">
                <a href="https://localhost:8080"><img src={require("./logo.png")} alt="Logo"/></a>
            </div>
            {location.pathname === "/register" ?
                <></>
                :
                getUsername() !== null && isAuthenticated ?
                    <div>
                        Hello {getUsername()}!
                        <DefaultButton
                            text="Logout"
                            label="Logout"
                            onClick={_onClickLogout}
                        />
                    </div>
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