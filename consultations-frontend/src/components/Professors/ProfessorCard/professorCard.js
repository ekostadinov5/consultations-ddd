import React, {useEffect, useState} from "react";
import { Link } from "react-router-dom";
import LocalStorageService from "../../../service/localStorageService";

const ProfessorCard = (props) => {

    const [professorFollowed, setProfessorFollowed] = useState(false);

    useEffect(() => {
        let professorFollowed = false;
        for (let p of props.studentProfessorsFollowed) {
            if (p.professorId.id === props.value.id.id) {
                professorFollowed = true;
            }
        }
        setProfessorFollowed(professorFollowed);
    }, [props.studentProfessorsFollowed, props.value]);

    const convertTitle = (title) => {
        if (title === "A") {
            return "Академик д-р"
        } else if (title === "D") {
            return "д-р"
        } else if (title === "M") {
            return "м-р"
        } else {
            return "спец."
        }
    }

    const follow = () => {
        props.onProfessorFollowed(props.value.id.id);
    }

    const unfollow = () => {
        props.onProfessorUnfollowed(props.value.id.id);
    }

    const followUnfollowButton = () => {
        if (LocalStorageService.getRole() === "STUDENT") {
            if (professorFollowed) {
                return (
                    <button onClick={unfollow} className="btn btn-danger ml-2 h-75" title="Откажи следење">
                        <i className="fas fa-xs fa-user-minus" />
                    </button>
                );
            } else {
                return (
                    <button onClick={follow} className="btn btn-success ml-2 h-75" title="Следи">
                        <i className="fas fa-xs fa-user-plus" />
                    </button>
                );
            }
        }
    }

    return (
        <div className="col-12 col-md-6 col-lg-4 mb-4">
            <div className="card">
                <div className="professorCardHeader card-header d-flex justify-content-between">
                    <p className="card-title mt-2">
                        {convertTitle(props.value.title)} {props.value.fullName.firstName} {props.value.fullName.lastName}
                    </p>
                    {followUnfollowButton()}
                </div>
                <Link to={`/professors/${props.value.id.id}`} className="btn btn-secondary">
                    Термини
                    <i className="fa fa-xs fa-angle-right ml-2" />
                </Link>
            </div>
        </div>
    );
}

export default ProfessorCard;
