import './UserList.css'
import {useEffect, useState} from "react";
import {fetchUsers} from "../../../services/AdminService";
import {IUserBasicDetails} from "../../../models/User/IUserBasicDetails";
import {DefaultButton, Stack, StackItem} from "@fluentui/react";
import {useNavigate} from "react-router-dom";

function UserList() {
    const [users, setUsers] = useState<IUserBasicDetails[]>([]);
    const [page, setPage] = useState<number>(1);
    const navigate = useNavigate();

    useEffect(() => {
        setPage(1);
        (async function() {
            const result = await fetchUsers(page);
            setUsers(result);
        })();
    }, [page]);

    function _onClickPrev() {
        if (page > 1) {
            setPage(page - 1);
        }
    }

    function _onClickNext() {
        setPage(page + 1);
    }

    return (
        <div>
            <Stack>
                <StackItem className="user-list-container">
                    <div>

                    </div>
                    {users.map((user) => (
                        <div className="user-details-row" key={user.username}>
                            <img src={require("../../../../../airbnbclone-backend/" + user.image)} alt={user.username} width={100} height={100}/>
                            <div>{user.username}</div>
                            <div>{user.firstName}</div>
                            <div>{user.lastName}</div>
                            <div>{user.phoneNumber}</div>
                            <DefaultButton text="Details" onClick={() => {navigate("/admin/user/" + user.username, )}}/>
                        </div>
                    ))}
                </StackItem>
                <StackItem className="page-buttons">
                    <DefaultButton text="Previous page" onClick={_onClickPrev}/>
                    <div>{page}</div>
                    <DefaultButton text="Next page" onClick={_onClickNext}/>
                </StackItem>
            </Stack>
        </div>
    );
}

export default UserList;