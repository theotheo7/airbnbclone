import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {fetchUser} from "../../../services/AdminService";
import {IUserCompleteDetails} from "../../../models/IUserCompleteDetails";

function UserDetails() {
    const { username } = useParams();
    const [user, setUser] = useState<IUserCompleteDetails>();

    useEffect(() => {
        (async function() {
            if (username !== undefined) {
                const result: IUserCompleteDetails = await fetchUser(username).then();
                setUser(result);
                console.log(result);
            }
        })();
    }, [username]);
    return (
        <div>Hello! {user?.username}</div>
    );
}

export default UserDetails;