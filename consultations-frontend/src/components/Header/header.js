import React from "react";
import { Link, NavLink } from "react-router-dom";
import LocalStorageService from "../../service/localStorageService";

const Header = (props) => {

    const logOut = () => {
        LocalStorageService.clearToken();
        LocalStorageService.clearIdentifier();
        LocalStorageService.clearRole();
        window.location.href = '/';
    }

    const logInOrLogOutButton = () => {
        if (LocalStorageService.getToken()) {
            return (
                <div className="order-lg-last">
                    <button onClick={logOut} className="btn btn-danger border-light">Одјава</button>
                </div>
            );
        } else {
            return (
                <div className="order-lg-last">
                    <NavLink to="/login" className="btn btn-outline-light">
                        Најава
                    </NavLink>
                </div>
            );
        }
    }
    
    const studentOrProfessor = () => {
        if (LocalStorageService.getRole() === "STUDENT") {
            return (
                <li className="nav-item ml-lg-1">
                    <NavLink to="/studentProfile" className="nav-link" onClick={() =>
                        props.loadStudent(LocalStorageService.getIdentifier())}>
                        Мои термини
                    </NavLink>
                </li>
            );
        } else if (LocalStorageService.getRole() === "PROFESSOR") {
            return (
                <li className="nav-item ml-lg-1">
                    <NavLink to="/professorProfile" className="nav-link" onClick={() =>
                        props.loadProfessorByUsername(LocalStorageService.getIdentifier())}>
                        Мои термини
                    </NavLink>
                </li>
            );
        } else {
            return null;
        }
    }

    return (
        <nav id="navbar" className="navbar navbar-expand-lg navbar-dark">
            <div className="container">
                <button className="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarToggler" aria-controls="navbarToggler" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <i className="navbar-toggler-icon" />
                </button>

                <Link to="/professors" className="navbar-brand m-auto"
                      title="Факултет за информатички науки и компјутерско инженерство">
                    ФИНКИ
                </Link>

                { logInOrLogOutButton() }

                <div className="collapse navbar-collapse ml-lg-4" id="navbarToggler">
                    <ul className="navbar-nav mr-auto mt-2 mt-lg-0 text-center">
                        <li className="nav-item ml-lg-1">
                            <NavLink to="/professors" className="nav-link" onClick={() => props.loadProfessors()}>
                                Наставен кадар
                            </NavLink>
                        </li>

                        { studentOrProfessor() }

                        <li className="nav-item ml-lg-1">
                            <NavLink to="/rooms" className="nav-link">
                                Простории
                            </NavLink>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
}

export default Header;
