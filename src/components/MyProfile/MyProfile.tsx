import './MyProfile.css'
import {fetchMyProfile, getToken, getUsername, updateMyProfile} from "../../services/UserService";
import React, {ChangeEvent, useEffect, useState} from "react";
import {IUserCompleteDetails} from "../../models/User/IUserCompleteDetails";
import {DefaultButton, Stack, StackItem, TextField} from "@fluentui/react";
import {UserUpdate} from "../../models/User/UserUpdate";
import {useNavigate} from "react-router-dom";

function MyProfile() {
    const [user, setUser] = useState<IUserCompleteDetails>();
    const [password, setPassword] = useState<string | undefined>("");
    const [passwordConfirm, setPasswordConfirm] = useState<string | undefined>("");
    const [image, setImage] = useState<File>();

    const username = getUsername();

    const navigate = useNavigate();

    useEffect(() => {
        (async function() {
            if (!user) {
                const result: IUserCompleteDetails = await fetchMyProfile(username as string).then();
                setUser(result);
                console.log(result.roles);
            }
        })();
    }, [user, username]);

    function _onChangeFirstName(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (user) {
            setUser({...user, firstName: newValue ? newValue : ""});
        }
    }

    function _onChangeLastName(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (user) {
            setUser({...user, lastName: newValue ? newValue : ""});
        }
    }

    function _onChangeEmail(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (user) {
            setUser({...user, email: newValue ? newValue : ""});
        }
    }

    function _onChangeUsername(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (user) {
            setUser({...user, username: newValue ? newValue : ""});
        }
    }

    function _onChangePhoneNumber(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (user) {
            setUser({...user, phoneNumber: newValue ? newValue : ""});
        }
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

    function _onChangeImage(
        event: ChangeEvent<HTMLInputElement>
    ) {
        if (event.target.files) {
            setImage(event.target.files[0]);
        }
    }

    async function _onClickUpdate() {
        if (user !== undefined) {
            const formData = new FormData();

            const userUpdate = new UserUpdate(
                user.username,
                user.email,
                user.firstName,
                user.lastName,
                password as string,
                user.phoneNumber
            );

            if (image !== undefined) {
                formData.append("image", image);
            } else {
                formData.append("image", "");
            }

            formData.append("user", JSON.stringify(userUpdate));

            try {
                await updateMyProfile(formData);
                window.alert("Successfully updated your profile!");
                navigate("/myprofile");
            } catch (err) {
                console.log(err);
                window.alert("Something went wrong! Try again.");
            }
        }
    }

    return (
        getToken() === null ?
        <div>
            You need to sign in first!
        </div>
        :
        <div>
            <Stack className="user-details-container">
                <h1>Welcome!</h1>
                <StackItem className="user-details">
                    <img
                        src={user?.image !== undefined ? require("../../../../airbnbclone-backend/" + user.image) : "../../../../public/PlaceholderImage.png"}
                        alt=""
                        key={username}
                        width={400}
                        height={300}
                    />
                    <TextField label="First Name" value={user?.firstName} onChange={_onChangeFirstName}/>
                    <TextField label="Last Name" value={user?.lastName} onChange={_onChangeLastName}/>
                    <TextField label="Email" value={user?.email} onChange={_onChangeEmail}/>
                    <TextField label="Username" value={user?.username} onChange={_onChangeUsername}/>
                    <TextField label="Phone Number" value={user?.phoneNumber} onChange={_onChangePhoneNumber}/>
                </StackItem>
                <StackItem className="passwords-container">
                    <TextField label="New Password" value={password} onChange={_onChangePassword}/>
                    <TextField label="Confirm Password" value={passwordConfirm} onChange={_onChangePasswordConfirm}/>
                </StackItem>
                <StackItem>
                    <input type="file" onChange={_onChangeImage}/>
                </StackItem>
                <StackItem>
                    <h4>Roles:</h4>
                    {user?.roles.map((role) => (
                        <div className="role-list" key={role.id}>{role.name}</div>
                    ))}
                </StackItem>
                <StackItem className="approve-button">
                    <DefaultButton text="Update" onClick={_onClickUpdate}/>
                </StackItem>
            </Stack>
        </div>
    );
}

export default MyProfile;