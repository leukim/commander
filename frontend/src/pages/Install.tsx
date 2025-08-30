import React, {useState} from "react";
import {Button, Container, Form} from "react-bootstrap";
import Api from "../utils/Api";
import {useNavigate} from "react-router-dom";

export const InstallPage = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (password !== confirmPassword) {
            alert("Passwords don't match");
        } else {
            Api.install(username, password)
                .then(_ => navigate("/login"));
        }
    }

    return(
        <Container>
            <h1>Installation</h1>
            <p>
                There is no admin user set up. Please create one before continuing.
            </p>
            <Form>
                <Form.Group controlId="admin_user">
                    <Form.Label>Admin username</Form.Label>
                    <Form.Control type="input" placeholder="Admin username" value={username} onChange={(e) => setUsername(e.target.value)} />
                </Form.Group>
                <Form.Group controlId="admin_password">
                    <Form.Label column="lg">Admin password</Form.Label>
                    <Form.Control type="password" placeholder="Admin password" value={password} onChange={(e) => setPassword(e.target.value)} />
                </Form.Group>
                <Form.Group controlId="admin_confirm_password">
                    <Form.Label column="lg">Confirm password</Form.Label>
                    <Form.Control type="password" placeholder="Repeat password" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} />
                </Form.Group>
                <Button variant="primary" type="submit" onClick={handleSubmit}>Submit</Button>
            </Form>
        </Container>
    );
}