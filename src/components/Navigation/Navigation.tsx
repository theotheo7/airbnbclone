import React, {useState} from 'react';
import './Navigation.css';
import {DefaultButton, TextField} from "@fluentui/react";
import {useLocation, useNavigate} from "react-router-dom";
import {UserLogin} from "../../models/User/UserLogin";
import {authenticate, getRoles, getUsername, logout, storeUserInfo} from "../../services/UserService";

function Navigation() {
    const [Username, setUsername] = useState<string | undefined>("");
    const [Password, setPassword] = useState<string | undefined>("");
    const location = useLocation();
    const navigate = useNavigate();

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

                let arr = [];
                for (let role of response["roles"]) {
                    arr.push(role.name);
                }

                storeUserInfo(Username, response["token"], arr);

                if (getRoles()?.includes("ADMIN")) {
                    navigate("/admin");
                } else if (getRoles()?.includes("HOST")) {
                    navigate("/host");
                } else {
                    navigate("/");
                }

            } catch (err) {
                console.log(err);
            } finally {}
        }
    }

    function _onClickHome() {
        navigate("/");
    }

    function _onClickAdmin() {
        navigate("/admin");
    }

    function isAdmin() {
        return getRoles()?.includes("ADMIN");
    }

    function _onClickHost() {
        navigate("/host");
    }

    function isHost() {
        return getRoles()?.includes("HOST");
    }

    function _onClickProfile() {
        navigate("/myprofile");
    }

    function _onClickMessages() {
        navigate("/messages");
    }

    function _onClickLogout() {
        logout();
        navigate("/");
    }

    return (
        <div className="navigation-container">
            <div className="logo">
                <a href="https://localhost:8080"><img src={require("./logo.png")} alt="Logo"/></a>
            </div>
            {location.pathname === "/register" ?
                <></>
                :
                getUsername() !== null ?
                    <div className="navigation-buttons">
                        <DefaultButton
                            text="Home"
                            label="Home"
                            onClick={_onClickHome}
                        />
                        <DefaultButton
                            text="Admin"
                            label="Admin"
                            onClick={_onClickAdmin}
                            disabled={!isAdmin()}
                        />
                        <DefaultButton
                            text="Host"
                            label="Host"
                            onClick={_onClickHost}
                            disabled={!isHost()}
                        />
                        <DefaultButton
                            text="Profile"
                            label="Profile"
                            onClick={_onClickProfile}
                        />
                        <DefaultButton
                            text="Messages"
                            label="Messages"
                            onClick={_onClickMessages}
                        />
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
                            canRevealPassword={true}
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
                            style={{marginBottom: "5px"}}
                        />
                    </div>

            }
        </div>
    )
}

export default Navigation;
