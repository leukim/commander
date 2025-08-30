import React from "react";
import {Route, Routes} from 'react-router-dom';
import {AuthProvider} from "./hooks/useAuth";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Layout} from "./components/Layout.js";

import {ProtectedRoute} from "./components/ProtectedRoute";
import {HomePage} from "./pages/Home";
import {LoginPage} from "./pages/Login";
import {ProductsPage} from "./pages/Products";
import {CheckInstall} from "./components/CheckInstall";
import {InstallPage} from "./pages/Install";

const App = () => {
    return (
        <AuthProvider>
            <Routes>
                <Route element={<Layout/>}>
                    <Route path="/" element={<HomePage/>}/>
                    <Route path="/install" element={<InstallPage/>}/>
                    <Route element={<CheckInstall/>}>
                        <Route path="/login" element={<LoginPage/>}/>
                        <Route path="/products" element={<ProtectedRoute><ProductsPage/></ProtectedRoute>}/>
                    </Route>
                </Route>
            </Routes>
        </AuthProvider>
    );
}

export default App;
