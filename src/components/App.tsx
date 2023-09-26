import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Navigation from "./Navigation/Navigation";
import Search from "./Search/Search";
import UserList from "./AdminPage/UserList/UserList";
import Register from "./Register/Register";
import UserDetails from "./AdminPage/UserDetails/UserDetails";

function App() {
    return (
        <BrowserRouter>
            <div className="app-container">
                <Navigation/>
                <Routes>
                    <Route path="/" Component={Search}/>
                    <Route path="/register" Component={Register}/>
                    <Route path="/admin" Component={UserList}/>
                    <Route path="/admin/user/:username" Component={UserDetails}/>
                </Routes>
            </div>
        </BrowserRouter>
    );
}

export default App;
