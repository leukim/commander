import React, {useState} from "react";
import {getProducts} from "../utils/ProductsApi";
import {useAuth} from "../hooks/useAuth";
import {Table} from "react-bootstrap";

export const ProductsPage = () => {
    const [products, setProducts] = useState([]);

    const {user} = useAuth();
    getProducts(user.token)
        .then((data) => {
            setProducts(data);
        })

    return (
        <div>
            <h1>Products</h1>
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>Product</th>
                    <th>Description</th>
                </tr>
                </thead>
                <tbody>
                {products.map((product) => (
                    <tr key={product.name}>
                        <td>{product.name}</td>
                        <td>{product.description}</td>
                    </tr>
                ))}
                </tbody>
            </Table>
        </div>
    );
}