import {useState} from "react";
import {useAuth} from "../hooks/useAuth.tsx";
import {Button, Container, Form} from "react-bootstrap";
import {useSearchParams} from "react-router-dom";

export const LoginPage = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const {login} = useAuth();
    const [searchParams, _] = useSearchParams();

    const handleLogin = async (e) => {
        e.preventDefault();
        if (username === "test" && password === "") {
            await login({username}, searchParams.get("ref"));
        } else {
            alert("Invalid username");
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