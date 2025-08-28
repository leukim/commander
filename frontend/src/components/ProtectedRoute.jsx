import {Navigate} from "react-router-dom";
import {useAuth} from "../hooks/useAuth.jsx";

export const ProtectedRoute = ({children}) => {
    const { user } = useAuth();
    if (user === null) {
        return <Navigate to="/login" />
    }
    return children;
}