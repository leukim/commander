import React, {useEffect, useState} from "react";
import {addProduct, deleteProduct, getProducts} from "../utils/ProductsApi";
import {useAuth} from "../hooks/useAuth";
import {Button, Col, Container, Form, Modal, Row, Table} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

export const ProductsPage = () => {
    const [products, setProducts] = useState([]);
    const [showAdd, setShowAdd] = useState(false);
    const [newProductName, setNewProductName] = useState("");
    const [newProductDescription, setNewProductDescription] = useState("");
    const [showDelete, setShowDelete] = useState(false);
    const [productToDelete, setProductToDelete] = useState({name: null, id: null});

    const {user} = useAuth();

    const updateProducts = () => {
        getProducts(user.token)
            .then((data) => {
                setProducts(data);
            });
    }

    useEffect(updateProducts, []);

    const handleSubmit = () => {
        try {
            addProduct(user.token, newProductName, newProductDescription)
                .then(() => {
                    setShowAdd(false);
                    updateProducts();
                });
        } catch (error) {
            alert("Could not add product");
        }
    };

    const confirmDelete = (name: string, id: string) => {
        setProductToDelete({name, id});
        setShowDelete(true);
    }

    const performDelete = () => {
        setShowDelete(false);
        try {
            deleteProduct(user.token, productToDelete.id)
                .then((_) => {
                    updateProducts();
                })
        } catch (error) {
            alert("Could not delete product");
        }
    };

    return (
        <Container>
            <Row>
                <Col xs={11}>
                    <h1>Products</h1>
                </Col>
                <Col xs={1}>
                    <Button variant="primary" onClick={() => setShowAdd(true)}>
                        <FontAwesomeIcon icon="fa-solid fa-plus"/>
                    </Button>
                </Col>
            </Row>
            <Row>
                <Col xs={12}>
                    <Table striped bordered hover>
                        <thead>
                        <tr>
                            <th>Product</th>
                            <th>Description</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody className="table-group-divider">
                        {products.map((product) => (
                            <tr key={product.name}>
                                <td>{product.name}</td>
                                <td>{product.description}</td>
                                <td>
                                    <Button variant="danger" size="sm"
                                            onClick={() => confirmDelete(product.name, product.id)}>
                                        <FontAwesomeIcon icon="fa-solid fa-trash"/>
                                    </Button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>
                    <Modal show={showAdd} onHide={() => setShowAdd(false)}>
                        <Modal.Header closeButton>
                            <Modal.Title>Add Product</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <Form>
                                <Form.Group>
                                    <Form.Label>Name</Form.Label>
                                    <Form.Control type="input" onChange={(e) => setNewProductName(e.target.value)}/>
                                </Form.Group>
                                <Form.Group>
                                    <Form.Label>Description</Form.Label>
                                    <Form.Control type="input"
                                                  onChange={(e) => setNewProductDescription(e.target.value)}/>
                                </Form.Group>
                                <Button variant="outline-primary" onClick={handleSubmit}>Add</Button>
                            </Form>
                        </Modal.Body>
                    </Modal>
                    <Modal show={showDelete} onHide={() => setShowDelete(false)}>
                        <Modal.Header closeButton>
                            <Modal.Title>Confirm deletion</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <div>
                                Do you really want to delete {productToDelete.name}?
                            </div>
                            <Button variant="danger" color="danger" onClick={performDelete}>Delete</Button>
                        </Modal.Body>
                    </Modal>
                </Col>
            </Row>
        </Container>
    );
}