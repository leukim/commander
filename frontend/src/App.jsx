import {Route, Routes} from 'react-router-dom';
import {LoginPage} from "./pages/Login";
import {HomePage} from "./pages/Home";
import {ProtectedRoute} from "./components/ProtectedRoute.jsx";
import {ProductsPage} from "./pages/Products.jsx";
import {AuthProvider} from "./hooks/useAuth.jsx";

import 'bootstrap/dist/css/bootstrap.min.css';
import {Layout} from "./components/Layout.jsx";

function App() {
    return (
        <AuthProvider>
            <Routes>
                <Route element={<Layout />}>
                    <Route path="/" element={<HomePage/>}/>
                    <Route path="/login" element={<LoginPage/>}/>
                    <Route path="/products" element={
                        <ProtectedRoute>
                            <ProductsPage/>
                        </ProtectedRoute>
                    }/>
                </Route>
            </Routes>
        </AuthProvider>
    )
}

export default App
