import {useAuth} from "../hooks/useAuth.jsx";

export const ProductsPage = () => {
    const {logout} = useAuth();

    const handleLogout = () => {
        logout();
    };

    return(
        <div>
            <h1>This is the products page</h1>
            <button onClick={handleLogout}>Logout</button>
        </div>
    );
}