import {Button, Col, Form, Nav, Navbar, Row} from "react-bootstrap";
import {useAuth} from "../hooks/useAuth.jsx";

export const CommanderNavbar = () => {
    const {user, logout} = useAuth();

    const userSection = user === null ?
        <></> :
        <Form inline>
            <Row>
                <Col xs="6" />
                <Col xs="2">
                    <Form.Control readOnly plaintext value={user.username} />
                </Col>
                <Col xs="2">
                    <Button variant="outline-primary" onClick={() => logout()}>Logout</Button>
                </Col>
            </Row>
        </Form>;

    return (
        <Navbar expand="lg" className="bg-body-tertiary">
            <Navbar.Brand href="/">
                <img src="../../public/icon.svg" width="30" height="30" className="d-inline-block align-top"/>
            </Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav"/>
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="me-auto">
                    <Nav.Link href="/">Home</Nav.Link>
                    <Nav.Link href="/products">Products</Nav.Link>
                </Nav>
            </Navbar.Collapse>
            {userSection}
        </Navbar>
    )
};
