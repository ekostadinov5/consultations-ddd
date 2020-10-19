import React from "react";
import StudentConsultationSlot from "./StudentConsultationSlot/studentConsultationSlot";

const StudentProfile = (props) => {

    const getTableRows = () => {
        return props.studentConsultationSlots
            .sort((cs1, cs2) => new Date(cs1.consultationSlotStart) - new Date(cs2.consultationSlotStart))
            .map(cs => <StudentConsultationSlot key={cs.id.id}
                                                value={cs}
                                                onStudentRemovedFromConsultationSlotSlot=
                                                    {props.onStudentRemovedFromConsultationSlotSlot}
            />);
    }

    const noConsultationSlots = () => {
        if (props.student.consultationSlots.length === 0) {
            return (
                <div className="text-center text-danger font-weight-bold mt-5">
                    Нема консултациски термини
                </div>
            );
        }
    }

    return props.student.fullName ?  (
        <div className="row">
            <div className="col-12 m-auto">
                <div className="row">
                    <div className="col-12 col-sm-8 text-center text-sm-left mb-3 mb-sm-0">
                        <span className="lead">
                            {props.student.fullName.firstName} {props.student.fullName.lastName} ({props.student.index.index})
                        </span>
                    </div>
                </div>

                <hr/>

                <div className="row">
                    <div className="col-12">
                        <table className="table table-striped table-responsive-lg table-hover w-100">
                            <thead>
                            <tr>
                                <th>Професор</th>
                                <th>Датум</th>
                                <th>Време</th>
                                <th>Просторија</th>
                                <th>Предмет</th>
                                <th>Забелешка</th>
                                <th />
                            </tr>
                            </thead>
                            <tbody>
                            {getTableRows()}
                            </tbody>
                        </table>
                        {noConsultationSlots()}
                    </div>
                </div>
            </div>
        </div>
    ) : null;
}

export default StudentProfile;