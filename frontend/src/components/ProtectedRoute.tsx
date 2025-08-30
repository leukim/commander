import React, {ReactElement} from "react";
import {Navigate, useLocation} from "react-router-dom";
import {useAuth} from "../hooks/useAuth.js";

export const ProtectedRoute = ({children}: { children: ReactElement }) => {
    const location = useLocation();
    const {user} = useAuth();

    if (user === null) {
        return <Navigate to={"/login?ref=" + location.pathname}/>
    }
    return children;
}