import {createContext, useContext, useMemo} from "react";
import {useNavigate} from "react-router-dom";
import { useLocalStorage } from "./useLocalStorage";
import {AuthType} from "../types/AuthType";

const AuthContext = createContext<AuthType>(null);

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useLocalStorage("user", null);
    const navigate = useNavigate();

    // call this function when you want to authenticate the user
    const login = (data: string, ref = "/") => {
        setUser(data);
        navigate(ref);
    };

    // call this function to sign out logged in user
    const logout = () => {
        setUser(null);
        navigate("/login", { replace: true });
    };

    const value : AuthType = useMemo(
        () => ({
            user,
            login,
            logout,
        }),
        [user]
    );
    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () : AuthType => {
    return useContext(AuthContext);
};