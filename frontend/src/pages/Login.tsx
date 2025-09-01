import React, {useState} from "react";
import {useAuth} from "../hooks/useAuth";
import {Button, Container, Form} from "react-bootstrap";
import {useSearchParams} from "react-router-dom";
import Api from "../utils/SystemApi";

export const LoginPage = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const {login} = useAuth();
    const [searchParams, _] = useSearchParams();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const token = await Api.login(username, password);
            login({username, token}, searchParams.get("ref"));
        } catch (error) {
            alert("Incorrect credentials");
        }
    };

    return (
        <Container>
            <Form onSubmit={handleLogin}>
                <Form.Group controlId="username">
                    <Form.Label column="lg">Username</Form.Label>
                    <Form.Control type="input" placeholder="Username" value={username}
                                  onChange={(e) => setUsername(e.target.value)}/>
                </Form.Group>
                <Form.Group controlId="password">
                    <Form.Label column="lg">Password</Form.Label>
                    <Form.Control type="password" placeholder="Password" value={password}
                                  onChange={(e) => setPassword(e.target.value)}/>
                </Form.Group>

                <Button variant="primary" type="submit" onClick={handleLogin}>
                    Login
                </Button>
            </Form>
        </Container>
    )
};