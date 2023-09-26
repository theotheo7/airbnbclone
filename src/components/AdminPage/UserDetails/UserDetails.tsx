import './UserDetails.css'
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {approveHost, fetchUser} from "../../../services/AdminService";
import {IUserCompleteDetails} from "../../../models/IUserCompleteDetails";
import {DefaultButton, Stack, StackItem, TextField} from "@fluentui/react";

function UserDetails() {
    const { username } = useParams();
    const navigate = useNavigate();
    const [user, setUser] = useState<IUserCompleteDetails>();

    useEffect(() => {
        (async function() {
            if (username !== undefined && !user) {
                const result: IUserCompleteDetails = await fetchUser(username).then();
                setUser(result);
                console.log(result.roles);
            }
        })();
    }, [user, username]);
    
    async function _onClickApprove() {
        if (user?.username) {
            try {
                await approveHost(user.username);
                window.alert("Successfully approved the host!");
                navigate("/admin/user/" + user.username);
            } catch (err) {
                console.log(err);
                window.alert("Something went wrong! Try again.")
            }
        }
    }

    return (
        <Stack className="user-details-container">
            <StackItem className="user-details">
                <img
                    src={user?.image !== undefined ? require("../../../../../airbnbclone-backend/" + user.image) : "../../../../public/PlaceholderImage.png"}
                    alt=""
                    key={user?.username}
                    width={400}
                    height={300}
                />
                <TextField label="First Name" value={user?.firstName || ''} readOnly/>
                <TextField label="Last Name" value={user?.lastName || ''} readOnly/>
                <TextField label="Email" value={user?.email || ''} readOnly/>
                <TextField label="Username" value={user?.username || ''} readOnly/>
            </StackItem>
            <StackItem>
                <h4>Roles:</h4>
                {user?.roles.map((role) => (
                    <div className="role-list" key={role.id}>{role.name}</div>
                ))}
            </StackItem>
            <StackItem className="approve-button">
                <DefaultButton text="Approve host" disabled={user?.hostApproved} onClick={_onClickApprove}/>
            </StackItem>
        </Stack>
    );
}

export default UserDetails;