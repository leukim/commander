import React, {useEffect, useState} from "react";
import {Button, Col, Container, Row} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import AddProductModal from "../components/modals/AddProductModal";
import ProductCard from "../components/products/ProductCard";
import ProductApi from "../utils/ProductsApi";

export const ProductsPage = () => {
    const [products, setProducts] = useState([]);
    const [showAdd, setShowAdd] = useState(false);

    const updateProducts = () => {
        ProductApi.getProducts()
            .then((data) => {
                setProducts(data);
                //setLoading(false);
            });
    }

    useEffect(updateProducts, []);

    return (
        <Container>
            <Row>
                <Col xs={11}>
                    <h1>Products</h1>
                </Col>
                <Col xs={1}>
                    <Button variant="primary" onClick={() => setShowAdd(true)}>
                        <FontAwesomeIcon icon="plus"/>
                    </Button>
                </Col>
            </Row>
            <Row>
                <Col>
                    <Row xs={2} sm={2} md={3} lg={4} className="g-4">
                        {products.map((product) => (
                            <Col><ProductCard product={product} onProductDeleted={updateProducts}
                                              key={product.id}/></Col>
                        ))}
                        <AddProductModal
                            show={showAdd}
                            onClickOut={() => setShowAdd(false)}
                            onProductCreated={() => {
                                setShowAdd(false);
                                updateProducts();
                            }}
                        />
                    </Row>
                </Col>
            </Row>
        </Container>
    );
}