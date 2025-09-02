import React, {useState} from "react";
import {Button, Form, Modal} from "react-bootstrap";
import ProductsApi from "../../utils/ProductsApi";

interface AddProductModalProps {
    show: boolean;
    onClickOut: () => void;
    onProductCreated: () => void;
}

const AddProductModal: React.FC<AddProductModalProps> = (props: AddProductModalProps) => {
    const [newProductName, setNewProductName] = useState("");
    const [newProductDescription, setNewProductDescription] = useState("");

    const {
        show,
        onClickOut,
        onProductCreated,
    } = props;

    const handleSubmit = () => {
        try {
            ProductsApi.addProduct(newProductName, newProductDescription)
                .then(onProductCreated);
        } catch (error) {
            alert("Could not add product");
        }
    };


    return (
        <Modal show={show} onHide={onClickOut}>
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
    );
};

export default AddProductModal;