import React from "react";
import {Outlet, useNavigate} from "react-router-dom";
import Api from "../utils/SystemApi";

export const CheckInstall = () => {
    const navigate = useNavigate();

    Api.getStatus()
        .then(status => {
            if (!status.admin) {
                navigate("/install");
            }
        })

    return (<Outlet/>);
};
