import {Route, Routes} from 'react-router-dom';
import {LoginPage} from "./pages/Login";
import {HomePage} from "./pages/Home";
import './App.css'
import {ProtectedRoute} from "./components/ProtectedRoute.jsx";
import {ProductsPage} from "./pages/Products.jsx";
import {AuthProvider} from "./hooks/useAuth.jsx";

function App() {
    return (
        <AuthProvider>
            <Routes>
                <Route path="/" element={<HomePage/>}/>
                <Route path="/login" element={<LoginPage/>}/>
                <Route path="/products" element={
                    <ProtectedRoute>
                        <ProductsPage/>
                    </ProtectedRoute>
                }/>
            </Routes>
        </AuthProvider>
    )
}

export default App
