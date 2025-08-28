import {Navigate, useLocation} from "react-router-dom";
import {useAuth} from "../hooks/useAuth.jsx";

export const ProtectedRoute = ({children}) => {
    const location = useLocation();
    const { user } = useAuth();

    if (user === null) {
        return <Navigate to={"/login?ref=" + location.pathname} />
    }
    return children;
}