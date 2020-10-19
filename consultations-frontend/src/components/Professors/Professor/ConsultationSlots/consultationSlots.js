import React from "react";
import RegularConsultationSlot from "./RegularConsultationSlot/regularConsultationSlot";
import AdditionalConsultationSlot from "./AdditionalConsultationSlot/additionalConsultationSlot";

const ConsultationSlots = (props) => {

    const getDayOfWeekIntValue = (dayOfWeek) => {
        if(dayOfWeek === 'MONDAY') {
            return 1;
        } else if(dayOfWeek === 'TUESDAY') {
            return 2;
        } else if(dayOfWeek === 'WEDNESDAY') {
            return 3;
        } else if(dayOfWeek === 'THURSDAY') {
            return 4;
        } else if(dayOfWeek === 'FRIDAY') {
            return 5;
        } else if(dayOfWeek === 'SATURDAY') {
            return 6;
        } else if(dayOfWeek === 'SUNDAY') {
            return 7;
        } else {
            return null;
        }
    }

    const compareTimeVars = (time1, time2) => {
        let t1 = time1.split(':');
        let t2 = time2.split(':');
        let h1 = parseInt(t1[0]);
        let m1 = parseInt(t1[1]);
        let h2 = parseInt(t2[0]);
        let m2 = parseInt(t2[1]);
        if(h1 > h2) {
            return 1;
        } else if (h1 < h2) {
            return -1;
        } else {
            if(m1 > m2) {
                return 1;
            } else if(m1 < m2) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    const regularConsultationSlots = () => {
        return props.professor.regularConsultationSlots
            .sort((rcs1, rcs2) => getDayOfWeekIntValue(rcs1.dayOfWeek) - getDayOfWeekIntValue(rcs2.dayOfWeek)
                || compareTimeVars(rcs1.from, rcs2.from))
            .map(rcs => <RegularConsultationSlot key={rcs.id.id} value={rcs}
                                                 professorId={props.professor.id.id}
                                                 subjects={props.sortedSubjects}
                                                 studentConsultationSlots={props.studentConsultationSlots}
                                                 onStudentAddedToConsultationSlotSlot=
                                                     {props.onStudentAddedToConsultationSlotSlot}
                                                 onStudentRemovedFromConsultationSlotSlot=
                                                     {props.onStudentRemovedFromConsultationSlotSlot} />);
    }

    const additionalConsultationSlots = () => {
        return props.professor.additionalConsultationSlots
            .sort((acs1, acs2) => new Date(acs1.date) - new Date(acs2.date) || compareTimeVars(acs1.from, acs2.from))
            .map(acs => <AdditionalConsultationSlot key={acs.id.id} value={acs}
                                                    professorId={props.professor.id.id}
                                                    subjects={props.sortedSubjects}
                                                    studentConsultationSlots={props.studentConsultationSlots}
                                                    onStudentAddedToConsultationSlotSlot=
                                                        {props.onStudentAddedToConsultationSlotSlot}
                                                    onStudentRemovedFromConsultationSlotSlot=
                                                        {props.onStudentRemovedFromConsultationSlotSlot} />);
    }

    const noConsultationSlots = () => {
        return (
            <div className="col-12 col-lg-8 text-center text-danger font-weight-bold my-5">
                Нема активни термини
            </div>
        );
    }

    return props.professor.regularConsultationSlots.length > 0
    || props.professor.additionalConsultationSlots.length > 0 ? (
        <div className="col-12 col-lg-8">
            <p className="font-weight-bold">
                Редовни консултации
            </p>

            <hr />

            <div className="row mb-4">
                {regularConsultationSlots()}
            </div>

            <p className="font-weight-bold">
                Дополнителни консултации
            </p>

            <hr />

            <div className="row mb-4">
                {additionalConsultationSlots()}
            </div>
        </div>
    ) : noConsultationSlots();
}

export default ConsultationSlots;
