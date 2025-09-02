import React, {useState} from "react";
import {Button, Card, Col, Container, Modal, Row} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {Product} from "../../types/Product";
import ProductsApi from "../../utils/ProductsApi";

interface ProductCardProps {
    product: Product;
    onProductDeleted: () => void;
}

const ProductCard: React.FC<ProductCardProps> = (props: ProductCardProps) => {
    const {
        product,
        onProductDeleted,
    } = props;

    const [showDelete, setShowDelete] = useState(false);
    const [productToDelete, setProductToDelete] = useState({name: null, id: null});

    const confirmDelete = (name: string, id: string) => {
        setProductToDelete({name, id});
        setShowDelete(true);
    }

    const performDelete = () => {
        setShowDelete(false);
        try {
            ProductsApi.deleteProduct(productToDelete.id).then(onProductDeleted)
        } catch (error) {
            alert("Could not delete product");
        }
    };

    return (
        <Card>
            <Card.Body>
                <Card.Title>
                    <Container>
                        <Row>
                            <Col xs={9}>{product.name}</Col>
                            <Col xs={1}>
                                <Button variant="danger" size="sm"
                                        onClick={() => confirmDelete(product.name, product.id)}>
                                    <FontAwesomeIcon icon="trash"/>
                                </Button>
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
                </Card.Title>
                <Card.Subtitle>{product.description}</Card.Subtitle>
            </Card.Body>
        </Card>
    );
};

export default ProductCard;