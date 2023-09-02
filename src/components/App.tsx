import {Stack} from '@fluentui/react';
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Navigation from "./Navigation/Navigation";
import Search from "./Search/Search";
import AdminPage from "./AdminPage/AdminPage";

function App() {
    return (
        <BrowserRouter>
            <div className="app-container">
                <Navigation/>
                <Stack>
                    <Routes>
                        <Route path="/" Component={Search}/>
                        <Route path="/admin" Component={AdminPage}/>
                    </Routes>
                </Stack>
            </div>
        </BrowserRouter>
    );
}

export default App;
