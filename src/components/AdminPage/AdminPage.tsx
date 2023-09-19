import {useEffect, useState} from "react";
import {fetchUsers} from "../../services/AdminService";
import {IUserBasicDetails} from "../../models/IUserBasicDetails";

function AdminPage() {
    const [users, setUsers] = useState<IUserBasicDetails[]>([]);

    useEffect(() => {
        (async function() {
            const result = await fetchUsers(1);
            setUsers(result);
            console.log(result);
        })();
    }, []);

    return (
        <div className="admin-container">
            {users.map((user) => (
                <div>
                    <img src={require("../../../../airbnbclone-backend/" + user.image)} alt={user.username} width={100} height={100}/>
                    {user.username}{user.firstName}{user.lastName}{user.phoneNumber}
                </div>
            ))}
        </div>
    );
}

export default AdminPage;