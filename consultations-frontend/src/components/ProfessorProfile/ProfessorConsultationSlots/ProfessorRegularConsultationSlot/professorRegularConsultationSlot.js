import React, { useState } from "react";
import ProfessorRegularConsultationSlots from "./ProfessorRegularConsultationSlots/professorRegularConsultationSlots";

const ProfessorRegularConsultationSlot = (props) => {

    const [index, setIndex] = useState(0);

    const getSortedConsultationSlots = () => {
        return props.value.consultationSlots.sort((cs1, cs2) => new Date(cs1.date) - new Date(cs2.date));
    }

    return (
        <div className="col-12 col-md-6 col-lg-4 mb-3">
            <ProfessorRegularConsultationSlots value={props.value}
                                               sortedConsultationSlots={getSortedConsultationSlots()}
                                               selectedConsultationSlotIndex={index}
                                               setSelectedConsultationSlotIndex={setIndex}
                                               roomsByBuilding={props.roomsByBuilding}
                                               onRegularConsultationSlotUpdated=
                                            {props.onRegularConsultationSlotUpdated}
                                               onRegularConsultationSlotDeleted=
                                            {props.onRegularConsultationSlotDeleted}
                                               onConsultationSlotCanceled=
                                            {props.onConsultationSlotCanceled}
                                               onConsultationSlotUncanceled=
                                            {props.onConsultationSlotUncanceled}
            />
        </div>
    );
}

export default ProfessorRegularConsultationSlot;
