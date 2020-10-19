import React, { useEffect, useState } from "react";
import ProfessorService from "../../../repository/axiosProfessorRepository";
import { useParams, useHistory } from "react-router-dom";
import Subjects from "./Subjects/subjects";
import ConsultationSlots from "./ConsultationSlots/consultationSlots";

const Professor = (props) => {

    const [professor, setProfessor] = useState();
    const [subjectsShow, setSubjectsShow] = useState(window.innerWidth > 992);

    const {professorId} = useParams();

    const history = useHistory();

    useEffect(() => {
        ProfessorService.fetchById(professorId).then((promise) => {
            setProfessor(promise.data);
        });
    }, [professorId]);

    const goBack = () => {
        history.push("/professors");
    }

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

    const showHideSubjects = () => {
        if (subjectsShow) {
            const subjects = document.getElementById("professorSubjects");
            subjects.style.display = "none";
            const buttonIcon = document.getElementById("showHideButtonIcon");
            buttonIcon.classList.remove("fa-angle-up");
            buttonIcon.classList.add("fa-angle-down");
        } else {
            const subjects = document.getElementById("professorSubjects");
            subjects.style.display = "inline";
            const buttonIcon = document.getElementById("showHideButtonIcon");
            buttonIcon.classList.remove("fa-angle-down");
            buttonIcon.classList.add("fa-angle-up");
        }
        setSubjectsShow(!subjectsShow);
    }

    const getSortedSubjects = () => {
        return professor.subjects.sort((s1, s2) => s1.name.localeCompare(s2.name));
    }

    return professor ? (
        <div className="row">
            <div className="col-10 m-auto">
                <div className="d-flex justify-content-between">
                    <p className="lead mb-0">
                        {convertTitle(professor.title)} {professor.fullName.firstName} {professor.fullName.lastName}
                    </p>

                    <button className="btn btn-sm btn-outline-secondary" title="Назад" onClick={goBack}>
                        <i className="fa fa-angle-left" />
                    </button>
                </div>

                <hr />

                <div className="row">
                    <div className="col-12 col-lg-4">
                        <div className="mb-4">
                            <p className="font-weight-bold mb-0">Е-пошта</p>
                            <p className="text-break">{professor.email.email}</p>
                        </div>

                        <div className="mb-4">
                            <p className="font-weight-bold mb-0">
                                Предмети
                                <button className="btn btn-sm ml-1" onClick={showHideSubjects}>
                                    <i id="showHideButtonIcon"
                                       className={`fa fa-xs fa-angle-${subjectsShow ? "up" : "down"}`} />
                                </button>
                            </p>
                            <span id="professorSubjects" style={{ display: subjectsShow ? "inline" : "none" }}>
                                <Subjects subjects={getSortedSubjects()}/>
                            </span>
                        </div>
                    </div>

                    <ConsultationSlots professor={professor}
                                       sortedSubjects={getSortedSubjects()}
                                       studentConsultationSlots={props.studentConsultationSlots}
                                       onStudentAddedToConsultationSlotSlot=
                                           {props.onStudentAddedToConsultationSlotSlot}
                                       onStudentRemovedFromConsultationSlotSlot=
                                           {props.onStudentRemovedFromConsultationSlotSlot} />
                </div>
            </div>
        </div>
    ) : null;
}

export default Professor;
