import "./Register.css"
import {ChoiceGroup, IChoiceGroupOption, IChoiceGroupStyles, PrimaryButton, TextField} from "@fluentui/react";
import React, {useState} from "react";
import {register, storeUserInfo} from "../../services/Authentication";
import {UserRegister} from "../../models/UserRegister";

const horizontalChoiceGroupStyles: IChoiceGroupStyles = {
    flexContainer: { display: "flex", flexDirection: "row", columnGap: "4px"},
};

const roleOptions: IChoiceGroupOption[] = [
    { key: "Guest", text: "Guest" },
    { key: "Host", text: "Host" },
    { key: "Both", text: "Both" },
];

function Register() {
    const [username, setUsername] = useState<string | undefined>("");
    const [email, setEmail] = useState<string | undefined>("");
    const [firstName, setFirstName] = useState<string | undefined>("");
    const [lastName, setLastName] = useState<string | undefined>("");
    const [password, setPassword] = useState<string | undefined>("");
    const [passwordConfirm, setPasswordConfirm] = useState<string | undefined>("");
    const [phoneNumber, setPhoneNumber] = useState<string | undefined>("");

    function _onChangeUsername(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setUsername(newValue);
    }

    function _onChangeEmail(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setEmail(newValue);
    }

    function _onChangeFirstName(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setFirstName(newValue);
    }

    function _onChangeLastName(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setLastName(newValue);
    }

    function _onChangePassword(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setPassword(newValue);
    }

    function _onChangePasswordConfirm(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setPasswordConfirm(newValue);
    }

    function _onChangePhoneNumber(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setPhoneNumber(newValue);
    }

    async function _onClickRegister() {
        if (username !== undefined && username !== "" && email !== undefined && firstName !== undefined && lastName !== undefined && password !== undefined && passwordConfirm !== undefined && phoneNumber !== undefined) {
            const userRegister = new UserRegister(username, email, firstName, lastName, password, phoneNumber);
            try {
                const response = await register(userRegister);
                storeUserInfo(username, response["token"]);
                window.alert("Successful registration! Please login.")
                window.location.href = "https://localhost:8080/"
            } catch (err) {
                console.log(err);
                window.alert("Something went wrong! Try again.")
            } finally {
            }
        }
    }

    return (
        <div className="register-container">
            <div className="register-box">
                <div>
                    <h3>Register</h3>
                </div>
                <div className="register-box-row">
                    <TextField
                        label="Username"
                        onChange={_onChangeUsername}
                    />
                    <TextField
                        label="Email"
                        onChange={_onChangeEmail}
                    />
                </div>
                <div className="register-box-row">
                    <TextField
                        label="First Name"
                        onChange={_onChangeFirstName}
                    />
                    <TextField
                        label="Last Name"
                        onChange={_onChangeLastName}
                    />
                </div>
                <div className="register-box-row">
                    <TextField
                        label="Password"
                        onChange={_onChangePassword}
                    />
                    <TextField
                        label="Confirm Password"
                        onChange={_onChangePasswordConfirm}
                    />
                </div>
                <div className="register-box-row">
                    <TextField
                        label="Phone Number"
                        onChange={_onChangePhoneNumber}
                    />
                    <ChoiceGroup
                        label="Role"
                        options={roleOptions}
                        styles={horizontalChoiceGroupStyles}/>
                </div>
                <div className="bottom-row-buttons">
                    <PrimaryButton
                        label="Register"
                        text="Register"
                        style={{float: "right"}}
                        onClick={_onClickRegister}
                    />
                </div>
            </div>
        </div>
    )
}

export default Register;