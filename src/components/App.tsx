import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Navigation from "./Navigation/Navigation";
import Search from "./Search/Search";
import UserList from "./AdminPage/UserList/UserList";
import Register from "./Register/Register";
import UserDetails from "./AdminPage/UserDetails/UserDetails";
import MyListings from "./HostPage/MyListings/MyListings";
import CreateListing from "./HostPage/CreateListing/CreateListing";
import MyListing from "./HostPage/MyListing/MyListing";
import MyProfile from "./MyProfile/MyProfile";

function App() {
    return (
        <BrowserRouter>
            <div className="app-container">
                <Navigation/>
                <Routes>
                    <Route path="/" Component={Search}/>
                    <Route path="/register" Component={Register}/>
                    <Route path="/myprofile" Component={MyProfile}/>
                    <Route path="/admin" Component={UserList}/>
                    <Route path="/admin/user/:username" Component={UserDetails}/>
                    <Route path="/host" Component={MyListings}/>
                    <Route path="/host/create" Component={CreateListing}/>
                    <Route path="/host/listing/:name" Component={MyListing}/>
                </Routes>
            </div>
        </BrowserRouter>
    );
}

export default App;
