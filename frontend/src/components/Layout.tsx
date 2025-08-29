import {Container} from "react-bootstrap";
import {Outlet} from "react-router-dom";
import {CommanderNavbar} from "./CommanderNavbar";

export const Layout = () => (
    <>
        <CommanderNavbar/>
        <Container fluid>
            <Outlet/>
        </Container>
    </>

)