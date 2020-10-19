import React from "react";
import ConsultationSlotStudent from "./ConsultationSlotStudent/consultationSlotStudent";
import { useHistory } from "react-router-dom";

const ConsultationSlotStudents = (props) => {

    const history = useHistory();

    const goBack = () => {
        history.push("/professorProfile");
    }

    const getSubjectName = (subjectId) => {
        if (subjectId === "0") {
            return "Останато";
        } else {
            return props.professorSubjects ? props.professorSubjects.filter(s => s.id.id === subjectId)[0].name : null;
        }
    }

    const getTableRows = () => {
        return props.location.state.students
            .sort((s1, s2) => new Date(s1.createdOn) - new Date(s2.createdOn))
            .map(s => <ConsultationSlotStudent key={s.id.id} value={s} subjectName={getSubjectName(s.subjectId.id)} />);
    }

    const noStudents = () => {
        if (props.location.state.students.length === 0) {
            return (
                <div className="text-center text-danger font-weight-bold mt-5">
                    Нема пријавени студенти
                </div>
            );
        }
    }

    return (
        <>
            <div className="d-flex justify-content-between">
                <div className="pt-1">
                    <span className="font-weight-bold">Вкупно: </span>
                    <span>
                        {props.location.state.students.length}
                    </span>
                </div>
                <div>
                    {/*<button className="btn btn-info btn-sm mb-3 mr-2" onClick={() => console.log("Not implemented!")}>*/}
                    {/*    Принтај*/}
                    {/*</button>*/}
                    <button className="btn btn-outline-secondary btn-sm mb-3" title="Назад" onClick={goBack}>
                        <i className="fa fa-angle-left" />
                    </button>
                </div>
            </div>
            <table className="table table-striped table-responsive-lg table-hover w-100">
                <thead>
                <tr>
                    <th>Индекс</th>
                    <th>Име</th>
                    <th>Презиме</th>
                    <th>Предмет</th>
                    <th>Забелешка</th>
                </tr>
                </thead>
                <tbody>
                {getTableRows()}
                </tbody>
            </table>
            {noStudents()}
        </>
    );
}

export default ConsultationSlotStudents;
