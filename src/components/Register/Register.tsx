import "./Register.css"
import {Checkbox, PrimaryButton, Stack, TextField} from "@fluentui/react";
import React, {ChangeEvent, useState} from "react";
import {register} from "../../services/Authentication";
import {UserRegister} from "../../models/UserRegister";


function Register() {
    const [username, setUsername] = useState<string | undefined>(undefined);
    const [email, setEmail] = useState<string | undefined>(undefined);
    const [firstName, setFirstName] = useState<string | undefined>(undefined);
    const [lastName, setLastName] = useState<string | undefined>(undefined);
    const [password, setPassword] = useState<string | undefined>(undefined);
    const [passwordConfirm, setPasswordConfirm] = useState<string | undefined>(undefined);
    const [phoneNumber, setPhoneNumber] = useState<string | undefined>(undefined);
    const [guest, setGuest] = useState<boolean>(false);
    const [host, setHost] = useState<boolean>(false);
    const [image, setImage] = useState<File>();

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

    function _onChangeGuest() {
        setGuest(!guest);
    }

    function _onChangeHost() {
        setHost(!host);
    }

    function _onChangeImage(
        event: ChangeEvent<HTMLInputElement>
    ) {
        if (event.target.files) {
            setImage(event.target.files[0]);
        }
    }

    async function _onClickRegister() {
        let roles: number[] = [];

        if (guest) {
            roles.push(2);
        }

        if (host) {
            roles.push(3);
        }

        console.log(image);

        if (username !== undefined && username !== "" && email !== undefined && firstName !== undefined && lastName !== undefined && password !== undefined && passwordConfirm !== undefined && phoneNumber !== undefined && image != null) {
            const userRegister = new UserRegister(username, email, firstName, lastName, password, phoneNumber, roles);
            const formData = new FormData();

            formData.append("image", image, image.name);
            formData.append("user", JSON.stringify(userRegister));

            try {
                await register(formData);
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
                        type="password"
                        onChange={_onChangePassword}
                    />
                    <TextField
                        label="Confirm Password"
                        type="password"
                        onChange={_onChangePasswordConfirm}
                    />
                </div>
                <div className="register-box-row">
                    <TextField
                        label="Phone Number"
                        onChange={_onChangePhoneNumber}
                    />
                    <div className="role-container">
                        Select Role:
                        <Stack className="role-checkboxes">
                            <Checkbox className="left-checkbox" label="Guest" onChange={_onChangeGuest}/>
                            <Checkbox label="Host" onChange={_onChangeHost}/>
                        </Stack>
                    </div>
                </div>
                <div className="bottom-row-buttons">
                    <input type="file" onChange={_onChangeImage}/>
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