import { useEffect } from "react";
import { useParams } from "react-router-dom";
import LocalStorageService from "../../service/localStorageService";

const CasLogin = () => {

    const { token, identifier, role } = useParams();

    useEffect(() => {
        LocalStorageService.setToken(token);
        LocalStorageService.setIdentifier(identifier);
        LocalStorageService.setRole(role);
        window.location.href = "/";
    });

    return null;
}

export default CasLogin;
