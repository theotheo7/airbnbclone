import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Navigation from "./Navigation/Navigation";
import Search from "./Search/Search";
import AdminPage from "./AdminPage/AdminPage";
import Register from "./Register/Register";

function App() {
    return (
        <BrowserRouter>
            <div className="app-container">
                <Navigation/>
                <Routes>
                    <Route path="/" Component={Search}/>
                    <Route path="/register" Component={Register}/>
                    <Route path="/admin" Component={AdminPage}/>
                </Routes>
            </div>
        </BrowserRouter>
    );
}

export default App;
